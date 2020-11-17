package model;

public class Donor {

    private final int donorID;
    private final String donorName;
    private final int phoneNumber;


    public Donor(int donorID, String donorName, int phoneNumber) {
        this.donorID = donorID;
        this.donorName = donorName;
        this.phoneNumber = phoneNumber;
    }

    public int getDonorID() {
        return donorID;
    }

    public String getDonorName() {
        return donorName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }


}
