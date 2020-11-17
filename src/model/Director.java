package model;

public class Director {

    private final int directorID;
    private final String password;
    private final String name;
    private final int phoneNumber;
    private final String address;
    private final String city;
    private final String postalCode;

    public Director(int directorID, String password, String name, int phoneNumber, String address, String city, String postalCode) {
        this.directorID = directorID;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
    public int getDirectorID() {
        return directorID;
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

    public String getPostalCode() {
        return postalCode;
    }


}
