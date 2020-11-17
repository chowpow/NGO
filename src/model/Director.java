package model;


public class Director {

    private final int directorId;
    private final String password;
    private final String name;
    private final int phoneNumber;
    private final String address;
    private final String city;
    private final String postalCode;

    public Director(int directorId, String password, String name, int phoneNumber, String address, String city, String postalCode) {
        this.directorId = directorId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
    public int getDirectorId() {
        return directorId;
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
