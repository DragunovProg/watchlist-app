package ua.dragunov.watchlist.persistence;

import ua.dragunov.watchlist.exceptions.DatabaseConnetionException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider {

    public static DataSource getDataSource(){
        Context dataSourceContext = null;
        try {
            dataSourceContext = new InitialContext();
            Context envContext = (Context) dataSourceContext.lookup("java:/comp/env");

            return (DataSource) envContext.lookup("jdbc/MysqlDataSource");
        } catch (NamingException e) {
            System.out.println("DataSource connection failed\nInfo: " + e.getMessage());
            throw new DatabaseConnetionException("database invalid connection exception, something wrong with you config in context.xml ", e);
        }
    }
}
