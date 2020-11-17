package model;

public class MaterialFunding extends Donation{

    private final String type;

    public MaterialFunding(int donationID, int amount, String type) {
        super(donationID, amount);
        this.type = type;
    }
}
