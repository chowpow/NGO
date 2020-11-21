package model;

public class Acquire {
    private final int departmentID;
    private final int donorID;



    public Acquire(int departmentID, int donorID) {
        this.departmentID = departmentID;
        this.donorID = donorID;

    }
    public int getDepartmentID() {
        return departmentID;
    }
    public int getDonorID() {
        return donorID;
    }


}
