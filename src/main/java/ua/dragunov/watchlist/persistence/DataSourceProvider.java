package ua.dragunov.watchlist.persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider {

    public static DataSource getDataSource() throws NamingException {
        Context dataSourceContext = new InitialContext();
        Context envContext = (Context) dataSourceContext.lookup("java:/comp/env");

        return (DataSource) envContext.lookup("jdbc/MysqlDataSource");
    }
}
