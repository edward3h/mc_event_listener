package org.ethelred.minecraft.events;

import org.jdbi.v3.sqlobject.GenerateSqlObject;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import java.util.List;

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
    List<LocationDTO> findLatestLocations();

}
