package org.ethelred.minecraft.events;

import io.avaje.http.client.HttpClient;
import io.avaje.http.client.JsonbBodyAdapter;
import io.avaje.inject.test.InjectTest;
import io.javalin.Javalin;
import jakarta.inject.Inject;
import org.ethelred.minecraft.events.model.Dimension;
import org.ethelred.minecraft.events.model.EventUpdate;
import org.ethelred.minecraft.events.model.PlayerUpdate;
import org.ethelred.minecraft.events.model.Vector3;
import org.jdbi.v3.core.Jdbi;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@InjectTest
public class ServiceTest {
    @Inject static Javalin javalin;
    static HttpClient client;
    @Inject
    static Jdbi jdbi;

    @BeforeAll
    static void start() {
        javalin.start();
        client = HttpClient.builder()
                .baseUrl(STR."http://localhost:\{javalin.port()}")
                .bodyAdapter(new JsonbBodyAdapter())
                .build();
    }

    @AfterAll
    static void stop() {
        javalin.stop();
    }

    @BeforeEach
    public void cleanUp() {
        jdbi.useHandle(handle -> handle.createUpdate("delete from location").execute());
    }

    @Test
    void testEmpty() {
        var res = client.request().GET().asPlainString();
        var doc = Jsoup.parse(res.body());
        var paragraphs = doc.select("p");
        assertThat(paragraphs).hasSize(1);
        var paragraph = paragraphs.first();
        assertThat(paragraph).isNotNull();
        assertThat(paragraph.ownText()).isEqualTo("No location data.");
    }

    @Test
    void testStuff() {
        var res = client.request()
                .path("api/event")
                .body(new EventUpdate("My World", List.of(
                        new PlayerUpdate("Steve", "x", Dimension.overworld, new Vector3(1,2,3)),
                        new PlayerUpdate("Alex", "y", Dimension.overworld, new Vector3(200.5, -16, 3456728))
                )))
                .POST()
                .asPlainString();
        assertThat(res.statusCode()).isEqualTo(201);

        var index = client.request().GET().asPlainString();
        var doc = Jsoup.parse(index.body());
        var listItems = doc.select("li");
        assertThat(listItems).hasSize(2);
        var item = listItems.first();
        assertThat(item).isNotNull();
        assertThat(item.text()).contains("Alex"); // players within a list are sorted alphabetically by name
    }
}
