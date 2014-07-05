package rusk.rest.system.db;

public final class DatabaseConfig {

    public static final String DRIVER = "org.hsqldb.jdbcDriver";
    public static final String URL = "jdbc:hsqldb:file:db/rusk;shutdown=true";
    public static final String USER = "SA";
    public static final String PASSWORD = "";
    
    private DatabaseConfig() {}
}
