package model;

public class Project {

    private final int projectId;
    private final String description;
    private final int budget;
    private final String duration;

    public Project(int projectId, String description, int budget, String duration) {
        this.projectId = projectId;
        this.description = description;
        this.budget = budget;
        this.duration = duration;
    }

    public int getProjectId() {
        return projectId;
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
