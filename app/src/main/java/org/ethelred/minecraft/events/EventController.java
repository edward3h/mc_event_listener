package org.ethelred.minecraft.events;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Post;
import jakarta.inject.Inject;

@Controller("/api/event")
public class EventController {
    private final EventDAO eventDAO;

    @Inject
    public EventController(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Post
    public void eventUpdate(EventUpdate update) {

    }

    @Get
    public String test() {
        return "Just testing";
    }
}
