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
        projectTableSetup();
        beneficiaryTableSetup();
        leadsTableSetup();
        helpTableSetup();
//        fundTableSetup();
        workOnTableSetup();
//        manageTableSetup();
//        organizeTableSetup();
//        donateTableSetup();
//        acquireTableSetup();
//        collectTableSetup();
        beneficiaryTableSetup();
        donorTableSetup();

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
        Volunteer volunteer2 = new Volunteer(123457, "23424dsdg", "Abigail Ayala", 3215568, "567 Main Mall", "Toronto");
        insertVolunteer(volunteer2);
        Volunteer volunteer3 = new Volunteer(123458, "23425dsdg", "David Smith", 3215569, "567 Main Mall", "Montreal");
        insertVolunteer(volunteer3);
        Volunteer volunteer4 = new Volunteer(123459, "23426dsdg", "Paulina Payne", 3215561, "567 Main Mall", "Vancouver");
        insertVolunteer(volunteer4);
        Volunteer volunteer5 = new Volunteer(123451, "23426dsdg", "Mathias Smith", 3215563, "567 Main Mall", "Vancouver");
        insertVolunteer(volunteer5);
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

    //Volunteer Division 1
    public Project[] getVolunteerInfoDivision1() {

        ArrayList<Project> result = new ArrayList<Project>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT P.project_id FROM project P WHERE NOT EXISTS ((SELECT V.volunteer_id FROM volunteer V) MINUS (SELECT W.volunteer_id FROM workon W WHERE W.project_id = P.project_id))");


            while(rs.next()) {
                Project model = new Project(rs.getInt("project_id"),
                        " ",
                        0,
                        " ");
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Project[result.size()]);

    }


    //Volunteer Division 2
    public Volunteer[] getVolunteerInfoDivision2() {

        ArrayList<Volunteer> result = new ArrayList<Volunteer>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT V.volunteer_id, V.v_name FROM volunteer V WHERE NOT EXISTS ((SELECT P.project_id FROM project P) MINUS (select W.project_id FROM workon W WHERE W.volunteer_id = V.volunteer_id))");


            while(rs.next()) {
                Volunteer model = new Volunteer(rs.getInt("volunteer_id"),
                        " ",
                        rs.getString("v_name"),
                        0,
                        " ",
                        " ");
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Volunteer[result.size()]);

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


//added comment

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
        Director director2 = new Director(222222, "23423dsdggg", "Geoff", 7771311, "669 Main Mall", "Vancouver");
        Director director3 = new Director(333333, "23423dsdggg", "Doc Rivers", 6661311, "669 East Mall", "Saskatoon");
        insertDirector(director1);
        insertDirector(director2);
        insertDirector(director3);
    }

    public void getVolunteersInfo(String dCity,Integer project_id) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(v.volunteer_id), v.v_name,v.v_phone,v.v_city" +
                    " FROM project p, volunteer v, workon w " +
                    " WHERE v.volunteer_id= w.volunteer_id and p.project_id = w.project_id " +"and w.project_id ="+project_id+
                    " and v.v_city=" +dCity+
                    " GROUP BY v.v_name,v.v_phone,v.v_city");

            System.out.println("Count Name" + "            " + "Phone Number City");
            while(rs.next()) {
                int count = rs.getInt(1);
                String name =rs.getString(2);
                int phoneNumber = rs.getInt(3);
                String city =rs.getString(4);
                System.out.println(count + "     " + name + " " + phoneNumber+ " " + city);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

    }

    public Volunteer[] getVolunteerInfoJoin1() {

        ArrayList<Volunteer> result = new ArrayList<Volunteer>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT v_name, v_phone FROM volunteer V, workon W WHERE V.volunteer_id = W.volunteer_id");


            while(rs.next()) {
                Volunteer model = new Volunteer( 0,
                        " ",
                        rs.getString("v_name"),
                        rs.getInt("v_phone"),
                        " ",
                        " ");
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Volunteer[result.size()]);

    }



    public Volunteer[] getVolunteerInfoJoin2() {

        ArrayList<Volunteer> result = new ArrayList<Volunteer>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT v_name, v_phone FROM volunteer V, leads L WHERE V.volunteer_id = L.volunteer_id");


            while(rs.next()) {
                Volunteer model = new Volunteer( 0,
                        " ",
                        rs.getString("v_name"),
                        rs.getInt("v_phone"),
                        " ",
                        " ");
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Volunteer[result.size()]);

    }



// lol

    public Director[] getDirectorInfo(String dCity) {
        ArrayList<Director> result = new ArrayList<Director>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM director WHERE d_city =" +dCity );


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

    public Director[] getDirectorInfoProj() {
        ArrayList<Director> result = new ArrayList<Director>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT director_id, d_name, d_phone FROM director");


            while(rs.next()) {
                Director model = new Director(rs.getInt("director_id"),
                        " ",
                        rs.getString("d_name"),
                        rs.getInt("d_phone"),
                        " ",
                        " ");
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Director[result.size()]);

    }


    //done
    public Director[] getDirectorInfoJoin() {

        ArrayList<Director> result = new ArrayList<Director>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT d_name, d_phone FROM director D, leads L WHERE D.director_id = L.director_id");


            while(rs.next()) {
                Director model = new Director( 0,
                        " ",
                        rs.getString("d_name"),
                        rs.getInt("d_phone"),
                        " ",
                        " ");
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
    // project table operations
    public void projectTableSetup() {
        // If a volunteer table already exists, must get rid of it first
        dropTableIfExists("project");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE project (project_id integer PRIMARY KEY, description varchar2(500) not null, " +
                    "budget integer not null, duration varchar2(100))");
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table
        Project project1 = new Project(123456, "To tackle malnutrition", 1000, "2 months");
        insertProject(project1);

    }

    // A project instance is passed to the method, uses getters to grab all the attributes and sets them to a new tuple in the table
    public void insertProject(Project project) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex volunteer id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO project VALUES (?,?,?,?)");
            ps.setInt(1, project.getProjectID());
            ps.setString(2, project.getDescription());
            ps.setInt(3, project.getBudget());
            ps.setString(4, project.getDuration());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Given a volunteer id, delete the volunteer corresponding with that vid
    public void deleteProject(int pid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM project WHERE project_id = ?");
            preparedStatement.setInt(1, pid);

            int rowCount = preparedStatement.executeUpdate();
            if (rowCount == 0) {
                System.out.println(pid + " does not exist!");
            }

            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void projectInfo(int amount) {
        try {
            Statement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT COUNT(project_id)," + option + " FROM project GROUP BY " + option);
            ResultSet rs = stmt.executeQuery("SELECT budget, COUNT(*) FROM project GROUP BY budget HAVING budget > " + amount);
            System.out.println("Budget  Count");

            while(rs.next()) {
                int budget = rs.getInt(1);
                //String description = rs.getString(2);
                int count = rs.getInt(2);

                System.out.println(budget + "     " + count);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void beneficiaryTableSetup() {
        this.dropTableIfExists("beneficiary");

        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("CREATE TABLE beneficiary( beneficiary_id integer PRIMARY KEY, " +
                    "name varchar2(50), age integer, phoneNumber integer , city varchar2(50) , postalCode varchar2(50))");
            statement.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        Beneficiary beneficiary1 = new Beneficiary(123456, "Lauren Lynch", 50, 7065678, "Vancouver", "V6L1X1");
        this.insertBeneficiary(beneficiary1);
        Beneficiary beneficiary2 = new Beneficiary(123457, "Cristian Lynch", 15, 7565678, "Vancouver", "V6L1X1");
        this.insertBeneficiary(beneficiary2);
        Beneficiary beneficiary3 = new Beneficiary(123458, "Abel Smith", 5, 7365678, "Vancouver", "V6L1X1");
        this.insertBeneficiary(beneficiary3);
        Beneficiary beneficiary4 = new Beneficiary(123459, "Allisson Thoms", 18, 7865678, "Vancouver", "V6L1X1");
        this.insertBeneficiary(beneficiary4);
        Beneficiary beneficiary5 = new Beneficiary(123451, "Fione Gallad", 60, 7765678, "Montreal", "V6L1X1");
        this.insertBeneficiary(beneficiary5);
        Beneficiary beneficiary6 = new Beneficiary(123452, "Peter Lynch", 20, 7965678, "Montreal", "V6L1X1");
        this.insertBeneficiary(beneficiary6);
        Beneficiary beneficiary7 = new Beneficiary(123453, "Jyn Lee", 12, 6065678, "Toronto", "V6L1X1");
        this.insertBeneficiary(beneficiary7);
        Beneficiary beneficiary8 = new Beneficiary(123454, "Lauren Levi", 7, 6045678, "Toronto", "V6L1X1");
        this.insertBeneficiary(beneficiary8);
        Beneficiary beneficiary9 = new Beneficiary(123455, "Mirkka Puente", 35, 7765671, "Toronto", "V6L1X1");
        this.insertBeneficiary(beneficiary9);
        Beneficiary beneficiary10 = new Beneficiary(123416, "John Pratt", 45, 6045674, "Montreal", "V6L1X1");
        this.insertBeneficiary(beneficiary10);

    }

    public void insertBeneficiary(Beneficiary beneficiary) {
        try {
            PreparedStatement ps = this.connection.prepareStatement("INSERT INTO beneficiary VALUES (?,?,?,?,?,?)");
            ps.setInt(1, beneficiary.getBeneficiaryID());
            ps.setString(2, beneficiary.getName());
            ps.setInt(3, beneficiary.getAge());
            ps.setInt(4, beneficiary.getPhoneNumber());
            ps.setString(5, beneficiary.getCity());
            ps.setString(6, beneficiary.getPostalCode());
            ps.executeUpdate();
            this.connection.commit();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void deleteBeneficiary(int bid) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM beneficiary WHERE beneficiary_id = ?");
            preparedStatement.setInt(1, bid);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount == 0) {
                System.out.println(bid + " does not exist!");
            }

            this.connection.commit();
            preparedStatement.close();
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public void getBeneficiaryCityAndMinAge(int project_id, int age) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT temp.avage, temp.city FROM(SELECT avg (b.age) avage,b.city " +
                    "FROM beneficiary b, help h WHERE b.beneficiary_id = h.beneficiary_id and h.project_id="+project_id+
                    " GROUP BY b.city) temp WHERE temp.avage >="+age);
            //" +
            //                    "
            System.out.println("City " +"      " +"Average AGE");

            while(rs.next()) {
                String city = rs.getString(1);
                String minAge = rs.getString(2);

                System.out.println( city+ "      " + minAge);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void donorTableSetup() {
        // If a director table already exists, must get rid of it first
        dropTableIfExists("donor");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE donor ( donor_id integer  PRIMARY KEY ," +
                    "donorName varchar2(50), phoneNumber integer)");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table
        Donor donor1 = new Donor(111111, "David Thoms", 8901311);
        insertDonor(donor1);
    }
    public void insertDonor(Donor donor) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex volunteer id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO donor VALUES (?,?,?)");
            ps.setInt(1, donor.getDonorID());
            ps.setString(2, donor.getDonorName());
            ps.setInt(3, donor.getPhoneNumber());
            ps.executeUpdate();
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDonor(int did) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM donor WHERE donor_id = ?");
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


    // leads table operations
    public void leadsTableSetup() {
        // If a leads table already exists, must get rid of it first
        dropTableIfExists("leads");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE leads (director_id integer, volunteer_id integer, FOREIGN KEY (director_id) " +
                    "REFERENCES director(director_id) ON DELETE CASCADE, FOREIGN KEY (volunteer_id) REFERENCES volunteer(volunteer_id) ON DELETE CASCADE)");



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
            statement.executeUpdate("CREATE TABLE help (project_id integer, beneficiary_id integer, FOREIGN KEY (project_id) " +
                    "REFERENCES project(project_id) ON DELETE CASCADE, FOREIGN KEY (beneficiary_id) REFERENCES beneficiary(beneficiary_id) ON DELETE CASCADE)");



        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        Help help1 = new Help(123456,123456);
        insertHelp(help1);
        Help help2 = new Help(123456,123457);
        insertHelp(help2);
        Help help3 = new Help(123456,123458);
        insertHelp(help3);
        Help help4 = new Help(123456,123459);
        insertHelp(help4);
        Help help5 = new Help(123456,123451);
        insertHelp(help5);
        Help help6 = new Help(123456,123452);
        insertHelp(help6);
        Help help7 = new Help(123456,123453);
        insertHelp(help7);
        Help help8 = new Help(123456,123454);
        insertHelp(help8);
        Help help9 = new Help(123456,123455);
        insertHelp(help9);
        Help help10 = new Help(123456,123416);
        insertHelp(help10);
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
            statement.executeUpdate("CREATE TABLE fund (project_id integer , donation_id integer," +
                    " FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (donation_id) REFERENCES donation (donation_id) ON DELETE CASCADE)");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

//        Fund fund1 = new Fund(234567,345678);
//        insertFund(fund1);
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
        dropTableIfExists("workon");

        try {
            // The SQL script to create the table
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE workon (project_id integer, volunteer_id integer, FOREIGN KEY (project_id) " +
                    "REFERENCES project (project_id) ON DELETE CASCADE, FOREIGN KEY (volunteer_id) REFERENCES volunteer (volunteer_id) ON DELETE CASCADE)");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1 Sample entry is created and inserted to the table

        WorkOn workOn1 = new WorkOn(123456,123456);
        insertWorkOn(workOn1);
        WorkOn workOn2 = new WorkOn(123456,123457);
        insertWorkOn(workOn2);
        WorkOn workOn3 = new WorkOn(123456,123458);
        insertWorkOn(workOn3);
        WorkOn workOn4 = new WorkOn(123456,123459);
        insertWorkOn(workOn4);
        WorkOn workOn5 = new WorkOn(123456,123451);
        insertWorkOn(workOn5);
    }
    public void insertWorkOn(WorkOn workOn) {
        try {
            // parameterIndices correspond to the positions of the attributes (ex workOn id is the first attribute)
            PreparedStatement ps = connection.prepareStatement("INSERT INTO workon VALUES (?,?)");
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
