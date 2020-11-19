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

    public void databaseSetUp() {
        volunteerTableSetup();
    }

    // volunteer table operations
    public void volunteerTableSetup() {
        dropTableIfExists("volunteer");
        
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE volunteer (volunteer_id integer PRIMARY KEY, v_password varchar2(20) not null, v_name varchar2(50), v_phone integer not null, v_address varchar2(50), v_city varchar2(50))");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        Volunteer volunteer1 = new Volunteer(123467, "23423dsdg", "Paul Pogba", 3215567, "567 Main Mall", "Vancouver");
        insertVolunteer(volunteer1);
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


    public void deleteVolunteer(int vid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM volunteer WHERE volunteer_id = ?");
            preparedStatement.setInt(1, vid);

            int rowCount = preparedStatement.executeUpdate();
            if (rowCount == 0) {
                System.out.println(vid + " does not exist!");
            }

            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method to drop tables if they already exist
    private void dropTableIfExists(String tableName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select table_name from user_tables");
            while(resultSet.next()) {
                if (resultSet.getString(1).toLowerCase().equals(tableName)) {
                    statement.execute("DROP TABLE " + tableName);
                    break;
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
