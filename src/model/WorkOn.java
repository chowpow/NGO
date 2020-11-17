package model;

public class WorkOn {

    private int projectID;
    private int volunteerID;

    public WorkOn(int projectID, int volunteerID) {
        this.projectID = projectID;
        this.volunteerID = volunteerID;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getVolunteerID() {
        return volunteerID;
    }
}
