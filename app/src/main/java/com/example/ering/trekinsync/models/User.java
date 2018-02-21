package com.example.ering.trekinsync.models;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private boolean isPersonalProfile;
    private String name;
    private String birthDate; //TODO: convert to date object
    private int age;
    private String citizenship;
    private String bloodType;
    private String allergies;
    private String medicine;
    private EmergencyContact[] emergencyContacts;
    private InsuranceCompany[] insuranceInfo;

    public User(boolean isPersonalProfile, String name, String birthDate, int age, String citizenship, String bloodType, String allergies, String medicine, EmergencyContact[] emergencyContact, InsuranceCompany[] insuranceInfo) {
        this.isPersonalProfile = isPersonalProfile;
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

    public boolean getIsPersonalProfile() {
        return isPersonalProfile;
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

    protected User(Parcel in) {
        isPersonalProfile = in.readInt() != 0;
        name = in.readString();
        birthDate = in.readString();
        age = in.readInt();
        citizenship = in.readString();
        bloodType = in.readString();
        allergies = in.readString();
        medicine = in.readString();
        emergencyContacts = in.createTypedArray(EmergencyContact.CREATOR);
        insuranceInfo = in.createTypedArray(InsuranceCompany.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt((isPersonalProfile ? 1 : 0));
        parcel.writeString(name);
        parcel.writeString(birthDate);
        parcel.writeInt(age);
        parcel.writeString(citizenship);
        parcel.writeString(bloodType);
        parcel.writeString(allergies);
        parcel.writeString(medicine);
        parcel.writeTypedArray(emergencyContacts, flags);
        parcel.writeTypedArray(insuranceInfo, flags);
    }
}
