package model;

public class Donor {

    private final int donorId;
    private final String donorName;
    private final int phoneNumber;
    private final String address;
    private final String city;
    private final String postalCode;


    public Donor(int donorId, String donorName, int phoneNumber, String address, String city, String postalCode) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public int getDonorId() {
        return donorId;
    }
    
    public String getDonorName() {
        return donorName;
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
