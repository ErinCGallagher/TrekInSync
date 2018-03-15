package com.trekinsync.ering.trekinsync.models;

import android.os.Parcel;
import android.os.Parcelable;

public class EmergencyContact implements Parcelable {
    private String name;
    private String phoneNumber;
    private String phoneNumberType;

    public EmergencyContact(String name, String phoneNumber, String phoneNumberType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber != null ? phoneNumber : "";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberType() {
        return phoneNumberType != null ? phoneNumberType : "";
    }

    public void setPhoneNumberType(String phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

    protected EmergencyContact(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
        phoneNumberType = in.readString();
    }

    public static final Creator<EmergencyContact> CREATOR = new Creator<EmergencyContact>() {
        @Override
        public EmergencyContact createFromParcel(Parcel in) {
            return new EmergencyContact(in);
        }

        @Override
        public EmergencyContact[] newArray(int size) {
            return new EmergencyContact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeString(phoneNumberType);
    }

    public EmergencyContact clone() {
        return new EmergencyContact(name, phoneNumber, phoneNumberType);
    }
}
