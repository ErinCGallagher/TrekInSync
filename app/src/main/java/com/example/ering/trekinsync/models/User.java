package com.example.ering.trekinsync.models;


import java.sql.Date;

public class User {
    private String name;
    private Date birthDate;
    private int age;
    private String citizenship;
    private String bloodType;
    private String allergies;
    private String medicine;
    private EmergencyContact[] emergencyContact;
    private InsuranceCompany[] insuranceInfo;

    public User(String name, Date birthDate, int age, String citizenship, String bloodType, String allergies, String medicine, EmergencyContact[] emergencyContact, InsuranceCompany[] insuranceInfo) {
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.citizenship = citizenship;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.medicine = medicine;
        this.emergencyContact = emergencyContact;
        this.insuranceInfo = insuranceInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public EmergencyContact[] getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact[] emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public InsuranceCompany[] getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(InsuranceCompany[] insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }
}
