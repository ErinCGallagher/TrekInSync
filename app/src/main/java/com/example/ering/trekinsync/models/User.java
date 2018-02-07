package com.example.ering.trekinsync.models;


public class User {
    private String name;
    //TODO: convert to date object
    private String birthDate;
    private int age;
    private String citizenship;
    private String bloodType;
    private String allergies;
    private String medicine;
    private EmergencyContact[] emergencyContacts;
    private InsuranceCompany[] insuranceInfo;

    public User(String name, String birthDate, int age, String citizenship, String bloodType, String allergies, String medicine, EmergencyContact[] emergencyContact, InsuranceCompany[] insuranceInfo) {
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.citizenship = citizenship;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.medicine = medicine;
        this.emergencyContacts = emergencyContact;
        this.insuranceInfo = insuranceInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public EmergencyContact[] getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(EmergencyContact[] emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public InsuranceCompany[] getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(InsuranceCompany[] insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }
}
