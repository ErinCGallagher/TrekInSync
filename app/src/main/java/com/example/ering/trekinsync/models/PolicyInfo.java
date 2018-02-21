package com.example.ering.trekinsync.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PolicyInfo implements Parcelable{
    private String name;
    private String number;

    public PolicyInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    protected PolicyInfo(Parcel in) {
        name = in.readString();
        number = in.readString();
    }

    public static final Creator<PolicyInfo> CREATOR = new Creator<PolicyInfo>() {
        @Override
        public PolicyInfo createFromParcel(Parcel in) {
            return new PolicyInfo(in);
        }

        @Override
        public PolicyInfo[] newArray(int size) {
            return new PolicyInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(number);
    }
}
