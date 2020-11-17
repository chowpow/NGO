package model;

public class Donation {

    private int donationID;
    private int amount;

    public Donation(int donationID, int amount) {
        this.donationID = donationID;
        this.amount = amount;
    }

    public int getDonationID() {
        return donationID;
    }

    public int getAmount() {
        return amount;
    }
}
