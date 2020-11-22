package ui;

import controller.NGO;
import model.Beneficiary;
import model.Director;
import model.Project;
import model.Volunteer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// UserInput interface contains an NGO variable
public class UserInput {

    private BufferedReader bufferedReader = null;
    private NGO ngo = null;

    private static final int INVALID_INPUT = Integer.MIN_VALUE;

    // An instance of NGO is passed to the constructor
    public UserInput(NGO ngo) {
        this.ngo = ngo;
    }

    // Main menu for user interaction, user can choose what table they are working on
    public void showMenu() {

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println("Which table would you like to work on");
            System.out.println("1. Volunteer");
            System.out.println("2. Director");
            System.out.println("3. Project");
            System.out.println("4. Beneficiary");
            System.out.println("5. Quit");
            System.out.println("Choose one of the above options");

            choice = readInt();

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleVolunteerOperation();
                        break;
                    case 2:
                        handleDirectorOperation();
                        break;
                    case 3:
                        handleProjectOperation();
                        break;
                    case 4:
                        handleBeneficiaryOperation();
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
                        break;
                    default:
                        System.out.println("Not a valid option");
                        break;
                }
            }


        }

    }

    // volunteer query methods
    private void handleVolunteerInsert() {
        int vid = INVALID_INPUT;
        // || ((int) (Math.log10(vid) + 1)) < 9 for check
        while (vid == INVALID_INPUT || (vid <= 100000 && vid > 999999)) {
            System.out.println("Please enter the volunteer ID (6 digits)");
            vid = readInt();
        }

        String vPassword = null;
        while (vPassword == null || vPassword.length() <=0) {
            System.out.println("Please enter the volunteer password");
            vPassword = readString().trim();
        }

        String vName = null;
        while (vName == null || vName.length() <=0) {
            System.out.println("Please enter the volunteer's name");
            vName = readString().trim();
        }

        int vPhoneNumber = INVALID_INPUT;
        while (vPhoneNumber == INVALID_INPUT || (vPhoneNumber <= 1000000 && vPhoneNumber > 9999999)) {
            System.out.println("Please enter the volunteer's phone number (7 digits)");
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

        // Creates a new instance of Volunteer Object with what the user inputted
        Volunteer volunteer = new Volunteer(vid, vPassword, vName, vPhoneNumber, vAddress, vCity);

        // Calls the insertVolunteer method in NGO
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



    private void handleDirectorOperation() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println("1. Insert Director");
            System.out.println("2. Delete Director");
            System.out.println("3. Update Director password");
            System.out.println("4. Get Director Info (SELECT)");
            System.out.println("5. Get Director Info (PROJECTION)");


            System.out.println("5. Quit");
            System.out.println("Choose one of the above options");

            choice = readInt();

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleDirectorInsert();
                        break;
                    case 2:
                        handleDirectorDelete();
                        break;
                    case 3:
                        handleDirectorUpdate();
                        break;
                    case 4:
                        handleDirectorSelection();
                        break;

                    case 5:
                        ngo.projectionDirector();
                        break;
                    default:
                        System.out.println("Not a valid option");
                        break;
                }
            }

        }
    }

    //SELECTION means which rows are returned
    private void handleDirectorSelection() {
        String dCity = null;
        while (dCity == null) {
            System.out.println("Please enter the city for the directors :");
            dCity = readString().trim();
            dCity = ("'"+dCity+"'");
        }
        ngo.selectionDirector(dCity);
    }


    private void handleDirectorInsert() {
        int did = INVALID_INPUT;
        // || ((int) (Math.log10(vid) + 1)) < 9 for check
        //COME BACK TO THIS
        while (did == INVALID_INPUT || (did <= 100000 && did > 999999)) {
            System.out.println("Please enter the director ID (6 digits)");
            did = readInt();
        }
        String dPassword = null;
        while (dPassword == null || dPassword.length() <=0) {
            System.out.println("Please enter the director password ");
            dPassword = readString().trim();
        }

        String dName = null;
        while (dName == null || dName.length() <=0) {
            System.out.println("Please enter the director's name");
            dName = readString().trim();
        }

        int dPhoneNumber = INVALID_INPUT;
        while (dPhoneNumber == INVALID_INPUT || (dPhoneNumber <= 1000000 && dPhoneNumber > 9999999)) {
            System.out.println("Please enter the director's phone number ( 7 digits)");
            dPhoneNumber = readInt();
        }

        String dAddress = null;
        while (dAddress == null) {
            System.out.println("Please enter the director's address");
            dAddress = readString().trim();
        }

        String dCity = null;
        while (dCity == null) {
            System.out.println("Please enter the director's city");
            dCity = readString().trim();
        }

        // Creates a new instance of Volunteer Object with what the user inputted
        Director director = new Director(did, dPassword, dName, dPhoneNumber, dAddress, dCity);

        // Calls the insertVolunteer method in NGO
        ngo.insertDirector(director);

    }

    private void handleDirectorDelete() {
        int did = INVALID_INPUT;
        while (did == INVALID_INPUT) {
            System.out.println("Enter the director ID you would like to delete");
            did = readInt();
            if (did != INVALID_INPUT) {
                ngo.deleteDirector(did);
            }
        }
    }

    private void handleDirectorUpdate() {
        int id = INVALID_INPUT;
        while (id == INVALID_INPUT) {
            System.out.print("Please enter the director ID you wish to update: ");
            id = readInt();
        }

        String password = null;
        while (password == null || password.length() <= 0) {
            System.out.print("Please enter the new director password ");
            password = readString().trim();
        }

        ngo.updateDirector(id, password);
    }
    private void handleProjectOperation() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println("1. Insert Project");
            System.out.println("2. Delete Project");

            System.out.println("5. Quit");
            System.out.println("Choose one of the above options");

            choice = readInt();

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleProjectInsert();
                        break;
                    case 2:
                        handleProjectDelete();
                        break;

                    case 5:
                        break;
                    default:
                        System.out.println("Not a valid option");
                        break;
                }
            }

        }
    }
    private void handleProjectInsert() {
        int pid = INVALID_INPUT;
        // || ((int) (Math.log10(vid) + 1)) < 9 for check
        while (pid == INVALID_INPUT || (pid <= 100000 && pid > 999999)) {
            System.out.println("Please enter the project ID (6 digits)");
            pid = readInt();
        }

        String description = null;
        while (description == null || description.length() <=0) {
            System.out.println("Please enter the project description");
            description = readString().trim();
        }

        int budget = INVALID_INPUT;
        while (budget == INVALID_INPUT || (budget<= 1000000 && budget > 9999999)) {
            System.out.println("Please enter the director's budget ( 7 digits)");
            budget = readInt();
        }

        String duration = null;
        while (duration== null) {
            System.out.println("Please enter the project duration(months and days)");
            duration = readString().trim();
        }
        // Creates a new instance of Volunteer Object with what the user inputted
        Project project = new Project(pid, description, budget, duration);

        // Calls the insertProject  method in NGO
        ngo.insertProject(project);
    }

    private void handleProjectDelete() {
        int pid = INVALID_INPUT;
        while (pid == INVALID_INPUT) {
            System.out.println("Enter the project ID you would like to delete");
            pid = readInt();
            if (pid != INVALID_INPUT) {
                ngo.deleteProject(pid);
            }
        }
    }
    private void handleBeneficiaryOperation() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println("1. Insert Beneficiary");
            System.out.println("2. Delete Beneficiary");

            System.out.println("5. Quit");
            System.out.println("Choose one of the above options");

            choice = readInt();

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleBeneficiaryInsert();
                        break;
                    case 2:
                        handleBeneficiaryDelete();
                        break;

                    case 5:
                        break;
                    default:
                        System.out.println("Not a valid option");
                        break;
                }
            }

        }
    }
    private void handleBeneficiaryInsert() {
        int bid = INVALID_INPUT;
        // || ((int) (Math.log10(vid) + 1)) < 9 for check
        while (bid == INVALID_INPUT || (bid <= 100000 && bid > 999999)) {
            System.out.println("Please enter the beneficiary ID (6 digits)");
            bid = readInt();
        }

        String name = null;
        while (name == null || name.length() <=0) {
            System.out.println("Please enter  beneficiary name");
            name = readString().trim();
        }
        int age = INVALID_INPUT;
        while (age == INVALID_INPUT || (age<= 1 && age > 999)) {
            System.out.println("Please enter the beneficiary age");
            age = readInt();
        }

        int phoneNumber = INVALID_INPUT;
        while (phoneNumber  == INVALID_INPUT || (phoneNumber <= 1000000 && phoneNumber > 9999999)) {
            System.out.println("Please enter the beneficiary's phone number( 7 digits)");
            phoneNumber = readInt();
        }

        String city = null;
        while (city== null) {
            System.out.println("Please enter city");
            city = readString().trim();
        }
        String postalCode = null;
        while (postalCode== null) {
            System.out.println("Please enter postal code");
            postalCode = readString().trim();
        }
        // Creates a new instance of Volunteer Object with what the user inputted
        Beneficiary beneficiary = new Beneficiary(bid, name,age,phoneNumber,city,postalCode);

        // Calls the insertProject  method in NGO
        ngo.insertBeneficiary(beneficiary);
    }

    private void handleBeneficiaryDelete() {
        int bid = INVALID_INPUT;
        while (bid == INVALID_INPUT) {
            System.out.println("Enter the volunteer ID you would like to delete");
            bid = readInt();
            if (bid != INVALID_INPUT) {
                ngo.deleteBeneficiary(bid);
            }
        }
    }

    private void quit() {
        System.out.println("System quitting");

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ngo.endUserInput();
    }








    // read userInput methods
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
