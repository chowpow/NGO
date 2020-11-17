package model;

public class Project {

    private final int projectID;
    private final String description;
    private final int budget;
    private final String duration;

    public Project(int projectID, String description, int budget, String duration) {
        this.projectID = projectID;
        this.description = description;
        this.budget = budget;
        this.duration = duration;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getDescription() {
        return description;
    }

    public int getBudget() {
        return budget;
    }

    public String getDuration() {
        return duration;
    }

}
