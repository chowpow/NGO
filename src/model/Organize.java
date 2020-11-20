package model;

public class Organize {
    private final int projectID;
    private final int departmentID;


    public Organize(int projectID, int departmentID) {
        this.projectID = projectID;
        this.departmentID = departmentID;
    }
    public int getProjectID() {
        return projectID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
}
