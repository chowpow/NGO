package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatebaseHandler {

    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private Connection connection;

    public DatebaseHandler() {
        //DriverManager.registerDriver();
    }
}
