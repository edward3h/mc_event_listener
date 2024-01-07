package org.ethelred.minecraft.events.di;

import io.avaje.config.Configuration;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.ethelred.minecraft.events.EventDAO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Factory
public class DAOFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOFactory.class);
    @Bean
    public DataSource getDataSource(Configuration configuration) {
        var dataSource = new SQLiteDataSource();
        var url = configuration.get("datasource.url");
        dataSource.setUrl(url);
        runLiquibaseUpdate(dataSource);
        return dataSource;
    }

    private void runLiquibaseUpdate(DataSource dataSource) {
        try (var connection = dataSource.getConnection()) {
            var lc = new JdbcConnection(connection);
            var ldb = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(lc);
            var liquibase = new Liquibase("db/liquibase-changelog.xml", new ClassLoaderResourceAccessor(), ldb);
            //noinspection deprecation
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            LOGGER.error("Failed to run Liquibase update", e);
        }
    }

    @Bean
    public Jdbi getJdbi(DataSource dataSource) {
        var jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    public EventDAO getEventDAO(Jdbi jdbi) {
        return jdbi.onDemand(EventDAO.class);
    }
}
