package ua.dragunov.watchlist.persistence;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider {
    private static final Logger logger = LogManager.getLogger(DataSourceProvider.class);

    public static DataSource getDataSource(){
        Context dataSourceContext = null;
        try {
            dataSourceContext = new InitialContext();
            Context envContext = (Context) dataSourceContext.lookup("java:/comp/env");
            logger.info("DataSource context created + {}", envContext);

            return (DataSource) envContext.lookup("jdbc/MysqlDataSource");
        } catch (NamingException e) {
            logger.error("DataSource connection failed\nInfo: {}", e.getMessage());
            throw new DatabaseConnetionException("database invalid connection exception, something wrong with you config in context.xml ", e);
        }
    }
}
