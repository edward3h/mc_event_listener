package org.ethelred.minecraft.events;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Post;
import jakarta.inject.Inject;
import org.ethelred.minecraft.events.model.EventUpdate;

@Controller("/api/event")
public class EventController {
    private final EventDAO eventDAO;

    @Inject
    public EventController(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Post
    public void eventUpdate(EventUpdate update) {
        eventDAO.begin();
        update.players()
                .forEach(p -> eventDAO.insertLocation(update.world(), p));
        eventDAO.commit();
    }

    @Get
    public String test() {
        return "Just testing";
    }
}
