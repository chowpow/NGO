package model;

public class Beneficiary {

    private final int beneficiaryID;
    private final String name;
    private final int age;
    private final int phoneNumber;
    private final String city;
    private final String postalCode;

    public Beneficiary(int beneficiaryID, String name, int age, int phoneNumber, String city, String postalCode) {
    this.beneficiaryID = beneficiaryID;
    this.name = name;
    this.age = age;
    this.phoneNumber = phoneNumber;
    this.city = city;
    this.postalCode = postalCode;
    }

    public int getBeneficiaryID() {
        return beneficiaryID;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }
    public String getPostalCode() {
        return postalCode;
    }

}




