package model;

public class Leads {
    private final int directorId;
    private final int volunteerId;


    public Leads(int directorId, int volunteerId) {
        this.directorId = directorId;
        this.volunteerId = volunteerId;
}
    public int getDirectorId() {
    return directorId;
    }
    public int getVolunteerId() {
        return volunteerId;
    }
}