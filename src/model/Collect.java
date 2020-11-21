package model;

public class Collect {
    private final int departmentID;
    private final int donationID;



    public Collect(int departmentID, int donationID) {
        this.departmentID = departmentID;
        this.donationID = donationID;

    }
    public int getDepartmentID() {
        return departmentID;
    }
    public int getDonationID() {
        return donationID;
    }
}
