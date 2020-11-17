package model;

public abstract class Department {
    protected final int departmentID;
    protected final String departmentType;

    public Department(int departmentID, String departmentType) {
        this.departmentID = departmentID;
        this.departmentType = departmentType;

    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentType() {
        return departmentType;
    }
}
