package org.ethelred.minecraft.events;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Post;
import jakarta.inject.Inject;
import org.ethelred.minecraft.events.di.MethodLogging;
import org.ethelred.minecraft.events.model.EventUpdate;
import org.slf4j.event.Level;

@Controller("/api/event")
@MethodLogging(Level.INFO)
public class EventController {
    private final EventDAO eventDAO;

    @Inject
    public EventController(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Post
    public void eventUpdate(EventUpdate update) {
        eventDAO.useTransaction(transactional ->
        update.players()
                .forEach(p -> transactional.insertLocation(update.world(), p))
        );
    }

    @Get
    public String test() {
        return "Just testing";
    }
}
