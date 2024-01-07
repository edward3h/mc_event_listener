package org.ethelred.minecraft.events;

import org.ethelred.minecraft.events.model.Dimension;
import org.ethelred.minecraft.events.model.Location;
import org.ethelred.minecraft.events.model.Player;
import org.ethelred.minecraft.events.model.PlayerUpdate;
import org.ethelred.minecraft.events.model.Vector3;
import org.ethelred.minecraft.events.model.World;
import org.jdbi.v3.sqlobject.GenerateSqlObject;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@GenerateSqlObject
public interface EventDAO extends Transactional<EventDAO> {
    @SqlUpdate(
            // language=sql
            """
            insert into location(world, player, dimension, x, y, z) values (:world, :name, :dimension, :location.x, :location.y, :location.z)
            """)
    boolean insertLocation(@Bind String world, @BindMethods PlayerUpdate playerUpdate);

    record LocationDTO(String world, String player, Dimension dimension, double x, double y, double z){}

    @SqlQuery(
            // language=sql
            """
            with recent_location as (
                select max(id) as recent_id, world, player as recent_id from location group by world, player
            )
            select l.world, l.player, l.dimension, l.x, l.y, l.z from location l join recent_location rl on l.id = rl.recent_id
            """
    )
    @RegisterConstructorMapper(LocationDTO.class)
    List<LocationDTO> findLatestLocationRows();

    default SortedSet<World> findLatestLocations() {
        return findLatestLocationRows()
                .stream()

                .collect(Collectors.groupingBy(LocationDTO::world,
                        Collectors.mapping(l -> new Player(l.player, new Location(l.dimension, new Vector3(l.x,l.y,l.z))),
                                Collectors.toCollection(TreeSet::new))))
                .entrySet()
                .stream()
                .map(e -> new World(e.getKey(), e.getValue()))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
