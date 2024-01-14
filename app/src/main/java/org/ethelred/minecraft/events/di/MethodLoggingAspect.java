package org.ethelred.minecraft.events.di;

import io.avaje.inject.aop.AspectProvider;
import io.avaje.inject.aop.Invocation;
import io.avaje.inject.aop.MethodInterceptor;
import jakarta.inject.Singleton;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.lang.reflect.Method;
import java.util.Arrays;

@Singleton
public class MethodLoggingAspect implements AspectProvider<MethodLogging> {
    @Override
    public MethodInterceptor interceptor(Method method, MethodLogging methodLogging) {
        Level level;
        if (methodLogging == null) {
            var klass = method.getDeclaringClass();
            var annotation = klass.getAnnotation(MethodLogging.class);
            if (annotation != null) {
                level = annotation.value();
            } else {
                level = Level.DEBUG;
            }
        } else {
            level = methodLogging.value();
        }
        return new MethodLoggingInterceptor(level);
    }

    private record MethodLoggingInterceptor(Level level) implements MethodInterceptor {

        @Override
            public void invoke(Invocation invocation) throws Throwable {
                var logger = LoggerFactory.getLogger(invocation.instance().getClass());
                try {
                    var result = invocation.invoke();
                    logger.atLevel(level).log("{}({}) => {}", invocation.method().getName(), Arrays.toString(invocation.arguments()), result);
                    invocation.result(result);
                } catch (Exception e) {
                    logger.atLevel(level).setCause(e).log("{}({}) exception", invocation.method().getName(), Arrays.toString(invocation.arguments()));
                    throw e;
                }
            }
        }
}
