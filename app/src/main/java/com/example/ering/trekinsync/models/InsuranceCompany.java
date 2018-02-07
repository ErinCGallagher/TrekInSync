package com.example.ering.trekinsync.models;

public class InsuranceCompany {
    private String name;
    private String phoneNumber;
    private boolean callFirst;
    private PolicyInfo[] policyInfo;

    public InsuranceCompany(String name, String phoneNumber, boolean callFirst, PolicyInfo[] policyInfo) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callFirst = callFirst;
        this.policyInfo = policyInfo;
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

    public boolean isCallFirst() {
        return callFirst;
    }

    public void setCallFirst(boolean callFirst) {
        this.callFirst = callFirst;
    }

    public PolicyInfo[] getPolicyInfo() {
        return policyInfo;
    }

    public void setPolicyInfo(PolicyInfo[] policyInfo) {
        this.policyInfo = policyInfo;
    }
}
