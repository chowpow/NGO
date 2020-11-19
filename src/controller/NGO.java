package controller;

import database.DatabaseHandler;
import delegates.LoginDelegate;
import model.Volunteer;
import ui.LoginOra;
import ui.UserInput;

public class NGO implements LoginDelegate {
    private DatabaseHandler databaseHandler;
    private LoginOra loginWindow;

    public NGO() {
        databaseHandler = new DatabaseHandler();
    }

    private void start() {
        loginWindow = new LoginOra();
        loginWindow.show(this);
    }

    @Override
    public void oraLogin(String username, String password) {
        boolean connected = databaseHandler.login(username, password);

        if (connected) {

            loginWindow.dispose();
            UserInput userInput = new UserInput();
            setUpDataBase();
            userInput.showMenu(this);


        } else {
            loginWindow.loginFail();

            if (loginWindow.hasReachedAttemptLimit()) {
                loginWindow.dispose();
                System.out.println("Max number of attempts exceeded");
                System.exit(-1);
            }
        }
    }

    private void setUpDataBase() {
        databaseHandler.databaseSetUp();
    }

    public void insertVolunteer(Volunteer volunteer) {
        databaseHandler.insertVolunteer(volunteer);
    }


    public static void main(String[] args) {
        NGO ngo = new NGO();
        ngo.start();
    }


    public void deleteVolunteer(int vid) {
        databaseHandler.deleteVolunteer(vid);
    }
}
