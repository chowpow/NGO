package controller;

import delegates.LoginDelegate;
import ui.LoginOra;

public class NGO implements LoginDelegate {
    private LoginOra loginWindow;

    public NGO() {

    }

    private void start() {
        loginWindow = new LoginOra();
        loginWindow.show(this);
    }

    @Override
    public void oraLogin(String username, String password) {

    }


    public static void main(String[] args) {
        NGO ngo = new NGO();
        ngo.start();
    }
}
