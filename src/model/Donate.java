package model;

public class Donate {
    private final int donorID;
    private final int donationID;


    public Donate(int donorID, int donationID) {
        this.donorID = donorID;
        this.donationID = donationID;
    }
    public int getDonorID() {
        return donorID;
    }
    public int getDonationID() {
        return donationID;
    }

}
