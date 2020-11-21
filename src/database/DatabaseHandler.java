package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]" ;
    private static final String WARNING_TAG = "[WARNING]";
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
        directorTableSetup();
        leadsTableSetup();
//        helpTableSetup();
//        fundTableSetup();
//        workOnTableSetup();
//        manageTableSetup();
//        organizeTableSetup();
//        donateTableSetup();
//        acquireTableSetup();
//        collectTableSetup();
    }


    //rollback connection for catching exceptions

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
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
            statement.close();


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




    public void directorTableSetup() {
        // If a director table already exists, must get rid of it first
        dropTableIfExists("director");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE director (director_id integer PRIMARY KEY, d_password varchar2(20) not null, " +
                    "d_name varchar2(50), d_phone integer not null check (d_phone between 1000000 and 9999999), d_address varchar2(50), d_city varchar2(50))");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table
        Director director1 = new Director(111111, "23423dsdggg", "jeff", 8901311, "568 Main Mall", "Vancouver");
        insertDirector(director1);
    }

    public Director[] getDirectorInfo() {
        ArrayList<Director> result = new ArrayList<Director>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM director");


            while(rs.next()) {
                Director model = new Director(rs.getInt("director_id"),
                        rs.getString("d_password"),
                        rs.getString("d_name"),
                        rs.getInt("d_phone"),
                        rs.getString("d_address"),
                        rs.getString("d_city"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Director[result.size()]);

    }




    public void insertDirector(Director director) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex volunteer id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO director VALUES (?,?,?,?,?,?)");
            ps.setInt(1, director.getDirectorID());
            ps.setString(2, director.getPassword());
            ps.setString(3, director.getName());
            ps.setInt(4, director.getPhoneNumber());
            ps.setString(5, director.getAddress());
            ps.setString(6, director.getCity());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDirector(int did) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM director WHERE director_id = ?");
            preparedStatement.setInt(1, did);

            int rowCount = preparedStatement.executeUpdate();
            if (rowCount == 0) {
                System.out.println(did + " does not exist!");
            }

            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update function for director

    public void updateDirector(int id, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE director SET d_password = ? WHERE director_id = ?");
            ps.setString(1, password);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG +" Director" + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // method to drop tables if they already exist, name of the table needing to be dropped is the parameter
    private void dropTableIfExists(String tableName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select table_name from user_tables");
            while(resultSet.next()) {
                if (resultSet.getString(1).toLowerCase().equals(tableName)) {
                    statement.execute("DROP TABLE " + tableName + " cascade constraints");
                    break;
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // leads table operations
    public void leadsTableSetup() {
        // If a leads table already exists, must get rid of it first
        dropTableIfExists("leads");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE leads (director_id integer, volunteer_id integer, FOREIGN KEY (director_id) REFERENCES director ON DELETE CASCADE, FOREIGN KEY (volunteer_id) REFERENCES volunteer ON DELETE CASCADE)");



        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Leads leads1 = new Leads(111111,123456);
        insertLeads(leads1);
    }
    public void insertLeads(Leads leads) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex: director id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO leads VALUES (?,?)");
            ps.setInt(1, leads.getDirectorID());
            ps.setInt(2, leads.getVolunteerID());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // help table operations
    public void helpTableSetup() {
        // If a help table already exists, must get rid of it first
        dropTableIfExists("help");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE help (project_id integer PRIMARY KEY," +
                    "beneficiary_id integer PRIMARY KEY, " +
                    "FOREIGN KEY (project_id) REFERENCES project (project_id), " +
                    "FOREIGN KEY (beneficiary_id) REFERENCES beneficiary (beneficiary_id));");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Help help1 = new Help(234567,345678);
        insertHelp(help1);
    }
    public void insertHelp(Help help) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex project id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO help VALUES (?,?)");
            ps.setInt(1, help.getProjectID());
            ps.setInt(2, help.getBeneficiaryID());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // fund table operations
    public void fundTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("fund");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE fund (project_id integer PRIMARY KEY," +
                    " donation_id integer PRIMARY KEY," +
                    " FOREIGN KEY (project_id) REFERENCES project (project_id), " +
                    "FOREIGN KEY (donation_id) REFERENCES donation (donation_id));");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Fund fund1 = new Fund(234567,345678);
        insertFund(fund1);
    }
    public void insertFund(Fund fund) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex fund id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO fund VALUES (?,?)");
            ps.setInt(1, fund.getProjectID());
            ps.setInt(2, fund.getDonationID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // workOn table operations
    public void workOnTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("workOn");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE workOn (project_id integer PRIMARY KEY, " +
                    "volunteer_id integer PRIMARY KEY, " +
                    "FOREIGN KEY (project_id) REFERENCES project (project_id), " +
                    "FOREIGN KEY (volunteer_id) REFERENCES volunteer (volunteer_id));");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        WorkOn workOn1 = new WorkOn(234567,345678);
        insertWorkOn(workOn1);
    }
    public void insertWorkOn(WorkOn workOn) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex workOn id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO workOn VALUES (?,?)");
            ps.setInt(1, workOn.getProjectID());
            ps.setInt(2, workOn.getVolunteerID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // manage table operations
    public void manageTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("manage");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE manages (director_id integer PRIMARY KEY," +
                    "department_id integer PRIMARY KEY, " +
                    "FOREIGN KEY (director_id) REFERENCES director (director_id), " +
                    "FOREIGN KEY (department_id) REFERENCES department (department_id));"

);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Manage manage1 = new Manage(234567,345678);
        insertManage(manage1);
    }
    public void insertManage(Manage manage) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex manage id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO manage VALUES (?,?)");
            ps.setInt(1, manage.getDirectorID());
            ps.setInt(2, manage.getDepartmentID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // organize table operations
    public void organizeTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("organize");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE organizes (project_id integer PRIMARY KEY,\n" +
                    "\t\t       department_id integer PRIMARY KEY,\n" +
                    "\t\t       FOREIGN KEY (project_id) REFERENCES project (project_id),\n" +
                    "\t\t       FOREIGN KEY (department_id) REFERENCES department (department_id));"

            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Organize organize1 = new Organize(234567,345678);
        insertOrganize(organize1);
    }
    public void insertOrganize(Organize organize) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex organize id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO organize VALUES (?,?)");
            ps.setInt(1, organize.getProjectID());
            ps.setInt(2, organize.getDepartmentID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // donate table operations
    public void donateTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("donate");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE donates (donor_id integer PRIMARY KEY,\n" +
                    "\t\t    donation_id integer PRIMARY KEY,\n" +
                    "\t\t    FOREIGN KEY (donor_id) REFERENCES donor (donor_id),\n" +
                    "\t\t    FOREIGN KEY (donation_id) REFERENCES donation (donation_id));"

            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Donate donate1 = new Donate(234567,345678);
        insertDonate(donate1);
    }
    public void insertDonate(Donate donate) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex donor id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO donate VALUES (?,?)");
            ps.setInt(1, donate.getDonorID());
            ps.setInt(2, donate.getDonationID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //  acquire table operations
    public void acquireTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("acquire");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE acquires ( department_id integer PRIMARY KEY,\n" +
                    "\t\t\tdonor_id integer PRIMARY KEY,\n" +
                    "\t\t\tFOREIGN KEY (department_id) REFERENCES department (department_id),\t\t   \n" +
                    "\t\t\tFOREIGN KEY (donor_id) REFERENCES donor (donor_id));"

            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Acquire acquire1 = new Acquire(234567,345678);
        insertAcquire(acquire1);
    }
    public void insertAcquire(Acquire acquire) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex department id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO acquire VALUES (?,?)");
            ps.setInt(1, acquire.getDepartmentID());
            ps.setInt(2, acquire.getDonorID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //  collect table operations
    public void collectTableSetup() {
        // If a fund table already exists, must get rid of it first
        dropTableIfExists("collect");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE collects (donation_id  integer PRIMARY KEY,\n" +
                    "\t\t    department_id integer PRIMARY KEY,\n" +
                    "\t\t    FOREIGN KEY (donation_id) REFERENCES donation (donation_id),\n" +
                    "\t\t    FOREIGN KEY (department_id) REFERENCES department (department_id));"

            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Collect collect1 = new Collect(234567,345678);
        insertCollect(collect1);
    }
    public void insertCollect(Collect collect) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex department id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO collect VALUES (?,?)");
            ps.setInt(1, collect.getDepartmentID());
            ps.setInt(2, collect.getDonationID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
