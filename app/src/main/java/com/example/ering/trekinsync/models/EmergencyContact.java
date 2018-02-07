package com.example.ering.trekinsync.models;

public class EmergencyContact {
    private String name;
    private String phoneNumber;
    private String phoneNumberType;

    public EmergencyContact(String name, String phoneNumber, String phoneNumberType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(String phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }
}
