package database;

import model.Volunteer;

import java.sql.*;

public class DatabaseHandler {

    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private Connection connection = null;

    public DatabaseHandler() {
        try {
            // Load JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("Connected to Oracle!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertVolunteer(Volunteer volunteer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO volunteer VALUES (?,?,?,?,?,?)");
            ps.setInt(1, volunteer.getVolunteerID());
            ps.setString(2, volunteer.getPassword());
            ps.setString(3, volunteer.getName());
            ps.setInt(4, volunteer.getPhoneNumber());
            ps.setString(5, volunteer.getAddress());
            ps.setString(6, volunteer.getCity());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void databaseSetUp() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE volunteer (volunteer_id integer PRIMARY KEY, v_password varchar2(20) not null, v_name varchar2(50), v_phone integer not null, v_address varchar2(50), v_city varchar2(50))");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        Volunteer volunteer1 = new Volunteer(123456, "soccer", "Jack Richards", 3214567, "234 Main Mall", "Vancouver");
        insertVolunteer(volunteer1);
    }
}
