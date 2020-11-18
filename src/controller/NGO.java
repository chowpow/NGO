package controller;

import database.DatabaseHandler;
import delegates.LoginDelegate;
import ui.LoginOra;

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

        } else {
            loginWindow.loginFail();

            if (loginWindow.hasReachedAttemptLimit()) {
                loginWindow.dispose();
                System.out.println("Max number of attempts exceeded");
                System.exit(-1);
            }
        }
    }


    public static void main(String[] args) {
        NGO ngo = new NGO();
        ngo.start();
    }
}
