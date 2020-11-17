package model;

public class Volunteer {
    private final int volunteerID;
    private final String password;
    private final String name;
    private final int phoneNumber;
    private final String address;
    private final String city;



    public Volunteer(int volunteerID, String password, String name, int phoneNumber, String address, String city) {
        this.volunteerID = volunteerID;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
    }

    public int getVolunteerID() {
        return volunteerID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
}
