package controller;

import database.DatabaseHandler;
import delegates.LoginDelegate;
import model.*;
import ui.LoginOra;
import ui.UserInput;

import java.sql.Statement;

// Main class that runs everything
public class NGO implements LoginDelegate {
    // contains a LoginWindow and a Database Handler
    private DatabaseHandler databaseHandler;
    private LoginOra loginWindow;

    public NGO() {
        databaseHandler = new DatabaseHandler();
    }

    // Creates a new instance of LoginWindow
    private void start() {
        loginWindow = new LoginOra();
        // Calls the show function, passing the current instance of NGO
        loginWindow.show(this);
    }

    public void endUserInput() {
        databaseHandler.close();
        databaseHandler = null;

        System.exit(0);
    }

    @Override
    // Takes in the username and password passed from the login window and passes it to Database handler
    public void oraLogin(String username, String password) {
        boolean connected = databaseHandler.login(username, password);

        // If the connection is successful, close the login window, set up all databases and creates a new instance of UserInput
        if (connected) {
            loginWindow.dispose();
            UserInput userInput = new UserInput(this);
            setUpDataBase();
            // Generates the user interaction menu
            userInput.showMenu();


        } else {
            loginWindow.loginFail();

            if (loginWindow.hasReachedAttemptLimit()) {
                loginWindow.dispose();
                System.out.println("Max number of attempts exceeded");
                System.exit(-1);
            }
        }
    }

    // Lets Database handler set up the databases
    private void setUpDataBase() {
        databaseHandler.databaseSetUp();
    }



    // Main method that runs everything, it creates a new instance of NGO and calls start
    public static void main(String[] args) {
        NGO ngo = new NGO();
        ngo.start();
    }

    // Passes in the volunteer instance it got from the UserInput class and passes in to the Database Handler
    public void insertVolunteer(Volunteer volunteer) {
        databaseHandler.insertVolunteer(volunteer);
    }

    public void deleteVolunteer(int vid) {
        databaseHandler.deleteVolunteer(vid);
    }

    public void volunteerJoin1() {
        Volunteer[] models = databaseHandler.getVolunteerInfoJoin1();

        for (int i = 0; i < models.length; i++) {
            Volunteer model = models[i];

            // simplified output formatting; truncation may occur
            //    System.out.printf("%-10.10s", model.getDirectorID());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-15.15s", model.getPhoneNumber());


            System.out.println();

        }

    }

    public void volunteerJoin2() {
        Volunteer[] models = databaseHandler.getVolunteerInfoJoin2();

        for (int i = 0; i < models.length; i++) {
            Volunteer model = models[i];

            // simplified output formatting; truncation may occur
            //    System.out.printf("%-10.10s", model.getDirectorID());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-15.15s", model.getPhoneNumber());


            System.out.println();

        }

    }

    public void volunteerDivision1() {
        Project[] models = databaseHandler.getVolunteerInfoDivision1();

        for (int i = 0; i < models.length; i++) {
            Project model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getProjectID());



            System.out.println();

        }

    }

    public void volunteerDivision2() {
        Volunteer[] models = databaseHandler.getVolunteerInfoDivision2();

        for (int i = 0; i < models.length; i++) {
            Volunteer model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getVolunteerID());
            System.out.printf("%-20.20s", model.getName());



            System.out.println();

        }

    }


    public void insertDirector(Director director) {databaseHandler.insertDirector(director);}

    public void deleteDirector(int did) {databaseHandler.deleteDirector(did);}
    public void updateDirector(int did, String pass) {databaseHandler.updateDirector(did, pass);}
    public void selectionDirector(String dCity) {
        Director[] models = databaseHandler.getDirectorInfo(dCity);

        for (int i = 0; i < models.length; i++) {
            Director model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getDirectorID());
            System.out.printf("%-20.20s", model.getPassword());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-15.15s", model.getPhoneNumber());
            System.out.printf("%-20.20s", model.getAddress());
            System.out.printf("%-15.15s", model.getCity());



            System.out.println();

        }


    }

    public void projectionDirector() {
        Director[] models = databaseHandler.getDirectorInfoProj();

        for (int i = 0; i < models.length; i++) {
            Director model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getDirectorID());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-15.15s", model.getPhoneNumber());


            System.out.println();

        }

    }

    public void joinDirector() {
        Director[] models = databaseHandler.getDirectorInfoJoin();

        for (int i = 0; i < models.length; i++) {
            Director model = models[i];

            // simplified output formatting; truncation may occur
        //    System.out.printf("%-10.10s", model.getDirectorID());
            System.out.printf("%-20.20s", model.getName());
            System.out.printf("%-15.15s", model.getPhoneNumber());


            System.out.println();

        }

    }

    public void projectInfo(int amount) {
        databaseHandler.projectInfo(amount);
    }
    public void getBeneficiaryCityAndMinAge(int project_id, int age) {
        databaseHandler.getBeneficiaryCityAndMinAge(project_id, age);
    }


    public void getVolunteersInfo(String dcity, int project_id) { databaseHandler.getVolunteersInfo(dcity, project_id); }
    public void insertProject(Project project) { databaseHandler.insertProject(project); }
    public void deleteProject(int pid) {
        databaseHandler.deleteProject(pid);
    }
    public void insertBeneficiary(Beneficiary beneficiary) { databaseHandler.insertBeneficiary(beneficiary); }
    public void deleteBeneficiary(int bid) {
        databaseHandler.deleteBeneficiary(bid);
    }
    public void insertDonor(Donor donor) { databaseHandler.insertDonor(donor); }
    public void deleteDonor(int did) {
        databaseHandler.deleteDonor(did);
    }


}
