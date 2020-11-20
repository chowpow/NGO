package database;

import model.Leads;
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

    // Close the connection with Oracle
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Uses the passed username and password to connect to Oracle servers, if connection works it returns true
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
            System.out.println("Connection has failed");
            return false;
        }
    }

    // Create all tables
    public void databaseSetUp() {
        volunteerTableSetup();
        leadsTableSetup();
    }

    // volunteer table operations
    public void volunteerTableSetup() {
        // If a volunteer table already exists, must get rid of it first
        dropTableIfExists("volunteer");
        
        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE volunteer (volunteer_id integer PRIMARY KEY, v_password varchar2(20) not null, " +
                    "v_name varchar2(50), v_phone integer not null check (v_phone between 1000000 and 9999999), v_address varchar2(50), v_city varchar2(50))");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table
        Volunteer volunteer1 = new Volunteer(123456, "23423dsdg", "Paul Pogba", 3215567, "567 Main Mall", "Vancouver");
        insertVolunteer(volunteer1);
    }

    // A volunteer instance is passed to the method, uses getters to grab all the attributes and sets them to a new tuple in the table
    public void insertVolunteer(Volunteer volunteer) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex volunteer id is the first attribute)
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

    // Given a volunteer id, delete the volunteer corresponding with that vid
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

    // method to drop tables if they already exist, name of the table needing to be dropped is the parameter
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

    // volunteer table operations
    public void leadsTableSetup() {
        // If a volunteer table already exists, must get rid of it first
        dropTableIfExists("leads");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE leads (director_id integer PRIMARY KEY, volunteer_id integer PRIMARY KEY, " +
                    "FOREIGN KEY (director_id) REFERENCES director (director_id), " +
                    "FOREIGN KEY (volunteer_id) REFERENCES volunteer (volunteer_id));");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Leads leads1 = new Leads(234567,345678);
        insertLeads(leads1);
    }
    public void insertLeads(Leads leads) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex volunteer id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO leads VALUES (?,?)");
            ps.setInt(1, leads.getDirectorID());
            ps.setInt(2, leads.getVolunteerID());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
