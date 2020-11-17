package model;

public class Help {
    private final int projectID;
    private final int beneficiaryID;


    public Help(int projectID, int beneficiaryID) {
        this.projectID = projectID;
        this.beneficiaryID = beneficiaryID;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getBeneficiaryID() {
        return beneficiaryID;
    }


}
