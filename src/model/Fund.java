package model;

public class Fund {

    private final int projectID;
    private final int donationID;

    public Fund(int projectID, int donationID) {
        this.projectID = projectID;
        this.donationID = donationID;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getDonationID() {
        return donationID;
    }


}
