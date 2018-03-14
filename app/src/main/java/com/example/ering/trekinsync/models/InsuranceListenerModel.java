package com.example.ering.trekinsync.models;

public class InsuranceListenerModel {
    private InsuranceCompany company;
    private int position;
    private boolean shouldDelete;

    public InsuranceListenerModel(InsuranceCompany company, int position, boolean shouldDelete) {
        this.company = company;
        this.position = position;
        this.shouldDelete = shouldDelete;
    }

    public InsuranceCompany getCompany() {
        return company;
    }

    public void setContact(InsuranceCompany company) {
        this.company = company;
    }

    public boolean isShouldDelete() {
        return shouldDelete;
    }

    public int getPosition() {
        return position;
    }
}
