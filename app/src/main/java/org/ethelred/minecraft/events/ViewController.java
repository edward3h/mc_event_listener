package org.ethelred.minecraft.events;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Produces;
import io.jstach.jstachio.JStachio;
import jakarta.inject.Inject;
import org.ethelred.minecraft.events.template.IndexTemplate;

@Controller
public class ViewController {
    private final EventDAO eventDAO;
    private final JStachio jStachio;

    @Inject
    public ViewController(EventDAO eventDAO, JStachio jStachio) {
        this.eventDAO = eventDAO;
        this.jStachio = jStachio;
    }

    @Get
    @Produces("text/html")
    public String index() {
        return jStachio.execute(new IndexTemplate("Title Goes Here",
                eventDAO.findLatestLocations()
                )
        );
    }
}
