package com.example.ering.trekinsync.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsuranceCompany implements Parcelable{
    private String name;
    private String phoneNumber;
    private boolean callFirst = false;
    private PolicyInfo[] policyInfo;

    public InsuranceCompany(String name, String phoneNumber, boolean callFirst, PolicyInfo[] policyInfo) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callFirst = callFirst;
        this.policyInfo = policyInfo;
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

    public boolean isCallFirst() {
        return callFirst;
    }

    public void setCallFirst(boolean callFirst) {
        this.callFirst = callFirst;
    }

    public ArrayList<PolicyInfo> getPolicyInfo() {
        if (policyInfo != null) {
            return new ArrayList<>(Arrays.asList(policyInfo));
        } else {
            return new ArrayList<>();
        }
    }

    public void setPolicyInfo(ArrayList<PolicyInfo> newPolicyInfo) {
        PolicyInfo[] updatedList = new PolicyInfo[(newPolicyInfo.size())];
        this.policyInfo = newPolicyInfo.toArray(updatedList);
    }

    protected InsuranceCompany(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
        callFirst = in.readInt() != 0;
        policyInfo = in.createTypedArray(PolicyInfo.CREATOR);
    }

    public static final Creator<InsuranceCompany> CREATOR = new Creator<InsuranceCompany>() {
        @Override
        public InsuranceCompany createFromParcel(Parcel in) {
            return new InsuranceCompany(in);
        }

        @Override
        public InsuranceCompany[] newArray(int size) {
            return new InsuranceCompany[size];
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
        parcel.writeInt((callFirst ? 1 : 0));
        parcel.writeTypedArray(policyInfo, flags);
    }

    public InsuranceCompany clone() {
        return new InsuranceCompany(name, phoneNumber, callFirst, policyInfo.clone());
    }
}
