package controller;

import database.DatabaseHandler;
import delegates.LoginDelegate;
import model.Director;
import model.Volunteer;
import ui.LoginOra;
import ui.UserInput;

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

    public void insertDirector(Director director) {databaseHandler.insertDirector(director);}

    public void deleteDirector(int did) {databaseHandler.deleteDirector(did);}
}
