package com.trekinsync.ering.trekinsync.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User implements Parcelable {
    private boolean isPersonalProfile;
    private String contactExpiryDate;
    private String name;
    private String birthDate;
    private int age;
    private String citizenship;
    private String bloodType;
    private String allergies;
    private String medicine;
    private int icon;
    private EmergencyContact[] emergencyContacts;
    private InsuranceCompany[] insuranceInfo;

    private static final String DATE_PATTERN_DISPLAY = "MMM d, yyyy";
    private static final String DATE_PATTERN_STORAGE = "yyyy-MM-dd";

    public User(boolean isPersonalProfile, String contactExpiryDate, String name, String birthDate, int age, String citizenship, String bloodType, String allergies, String medicine, int icon, EmergencyContact[] emergencyContact, InsuranceCompany[] insuranceInfo) {
        this.isPersonalProfile = isPersonalProfile;
        this.contactExpiryDate = contactExpiryDate;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.citizenship = citizenship;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.medicine = medicine;
        this.icon = icon;
        this.emergencyContacts = emergencyContact;
        this.insuranceInfo = insuranceInfo;
    }

    public boolean getIsPersonalProfile() {
        return isPersonalProfile;
    }

    public void setIsPersonalProfile(boolean isPersonalProfile) {
        this.isPersonalProfile = isPersonalProfile;
    }

    /**
     * Get the expiry date from object as DATE_PATTERN_STORAGE and return as Date object.
     * @return Date expiry date
     */
    public Date getContactExpiryDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_STORAGE, Locale.CANADA);
        Date date = null;
        if (contactExpiryDate != null) {
            try {
                date = formatter.parse(contactExpiryDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date != null ? date : new Date();
    }

    /**
     * Get formatted expiry date for display DATE_PATTERN_DISPLAY (MMM d, yyyy).
     * @return String expiry date formatted
     */
    public String getFormattedExpiryDate() {
        Date expiryDateToConvert = getContactExpiryDate();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_DISPLAY, Locale.CANADA);
        return formatter.format(expiryDateToConvert);
    }

    /**
     * Given expiryDate, sets the value in DATE_PATTERN_STORAGE (yyy-MM-dd)
     * @param expiryDate, Date expiryDate
     */
    public void setContactExpiryDate(Date expiryDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_STORAGE, Locale.CANADA);
        this.contactExpiryDate  = formatter.format(expiryDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_STORAGE, Locale.CANADA);
        Date date = null;
        if (birthDate != null) {
            try {
                date = formatter.parse(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date != null ? date : new Date();
    }

    public String getFormattedBirthDate() {
        Date birthDateToConvert = getBirthdayDate();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_DISPLAY, Locale.CANADA);
        return formatter.format(birthDateToConvert);
    }

    public void setBirthDate(Date birthDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_STORAGE, Locale.CANADA);
        this.birthDate = formatter.format(birthDate);
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public String getSortingStringCountry() {
        String key = citizenship + name;
        return key.replaceAll("\\s","");
    }

    public String getSortingStringName() {
        String key = name + citizenship;
        return key.replaceAll("\\s","");
    }

    protected User(Parcel in) {
        isPersonalProfile = in.readInt() != 0;
        contactExpiryDate = in.readString();
        name = in.readString();
        birthDate = in.readString();
        age = in.readInt();
        citizenship = in.readString();
        bloodType = in.readString();
        allergies = in.readString();
        medicine = in.readString();
        icon = in.readInt();
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
        parcel.writeString(contactExpiryDate);
        parcel.writeString(name);
        parcel.writeString(birthDate);
        parcel.writeInt(age);
        parcel.writeString(citizenship);
        parcel.writeString(bloodType);
        parcel.writeString(allergies);
        parcel.writeString(medicine);
        parcel.writeInt(icon);
        parcel.writeTypedArray(emergencyContacts, flags);
        parcel.writeTypedArray(insuranceInfo, flags);
    }
}
