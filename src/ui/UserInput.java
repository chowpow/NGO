package ui;

import controller.NGO;
import model.Volunteer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {

    private BufferedReader bufferedReader = null;
    private NGO ngo = null;

    private static final int INVALID_INPUT = Integer.MIN_VALUE;

    public UserInput() {
    }

    public void showMenu(NGO ngo) {
        this.ngo = ngo;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println("Which table would you like to work on");
            System.out.println("1. Volunteer");
            System.out.println("2. Director");

            System.out.println("5. Quit");
            System.out.println("Choose one of the above options");

            choice = readInt();

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleVolunteerOperation();
                        break;
                    case 2:
                        handleDirectorOperation(ngo);
                        break;
                    case 5:
                        quit();
                        break;
                    default:
                        System.out.println("Not a valid option");
                        break;
                }
            }
        }
    }



    private void handleVolunteerOperation() {

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println("1. Insert Volunteer");
            System.out.println("2. Delete Volunteer");

            System.out.println("5. Quit");
            System.out.println("Choose one of the above options");

            choice = readInt();

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleVolunteerInsert();
                        break;
                    case 2:
                        handleVolunteerDelete();
                        break;
                    case 5:
                        quit();
                        break;
                    default:
                        System.out.println("Not a valid option");
                        break;
                }
            }


        }

    }

    private void handleVolunteerInsert() {
        int vid = INVALID_INPUT;
        // || ((int) (Math.log10(vid) + 1)) < 9 for check
        while (vid == INVALID_INPUT || ((int) (Math.log10(vid) + 1)) < 9) {
            System.out.println("Please enter the volunteer ID");
            vid = readInt();
        }

        String vPassword = null;
        while (vPassword == null) {
            System.out.println("Please enter the volunteer password");
            vPassword = readString().trim();
        }

        String vName = null;
        while (vName == null) {
            System.out.println("Please enter the volunteer's name");
            vName = readString().trim();
        }

        int vPhoneNumber = INVALID_INPUT;
        while (vPhoneNumber == INVALID_INPUT) {
            System.out.println("Please enter the volunteer's phone number");
            vPhoneNumber = readInt();
        }

        String vAddress = null;
        while (vAddress == null) {
            System.out.println("Please enter the volunteer's address");
            vAddress = readString().trim();
        }

        String vCity = null;
        while (vCity == null) {
            System.out.println("Please enter the volunteer's city");
            vCity = readString().trim();
        }

        Volunteer volunteer = new Volunteer(vid, vPassword, vName, vPhoneNumber, vAddress, vCity);

        ngo.insertVolunteer(volunteer);
    }

    private void handleVolunteerDelete() {
        int vid = INVALID_INPUT;
        while (vid == INVALID_INPUT) {
            System.out.println("Enter the volunteer ID you would like to delete");
            vid = readInt();
            if (vid != INVALID_INPUT) {
                ngo.deleteVolunteer(vid);
            }
        }
    }










    private void handleDirectorOperation(NGO ngo) {
    }

    private void quit() {
    }



    private int readInt() {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private String readString() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
