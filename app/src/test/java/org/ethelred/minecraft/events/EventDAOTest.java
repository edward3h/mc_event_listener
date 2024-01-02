package org.ethelred.minecraft.events;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@InjectTest
public class EventDAOTest {
    @Inject
    Jdbi jdbi;

    @Inject
    EventDAO eventDAO;

    @BeforeEach
    public void cleanUp() {
        jdbi.useHandle(handle -> handle.createUpdate("delete from location").execute());
    }

    @Test
    public void testDAO() {
        var success = eventDAO.insertLocation("My World", new PlayerUpdate("Steve", "ignore", Dimension.overworld, new Vector3(1, 2,3)));
        assertThat(success).isTrue();

        var locations = eventDAO.findLatestLocations();
        assertThat(locations).hasSize(1)
                .first()
                .extracting(EventDAO.LocationDTO::player).isEqualTo("Steve");

        success = eventDAO.insertLocation("My World", new PlayerUpdate("Alex", "ignore", Dimension.overworld, new Vector3(7, -1.3,56)));
        assertThat(success).isTrue();
        locations = eventDAO.findLatestLocations();
        assertThat(locations).hasSize(2)
                .extracting(EventDAO.LocationDTO::player).contains("Alex", "Steve");
        assertThat(locations.get(0).dimension()).isEqualTo(Dimension.overworld);
    }
}
