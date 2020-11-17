package model;

public class Leads {
    private final int directorID;
    private final int volunteerID;


    public Leads(int directorID, int volunteerID) {
        this.directorID = directorID;
        this.volunteerID = volunteerID;
}
    public int getDirectorID() {
    return directorID;
    }
    public int getVolunteerID() {
        return volunteerID;
    }
}