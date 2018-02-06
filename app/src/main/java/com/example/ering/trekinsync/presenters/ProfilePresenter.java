package com.example.ering.trekinsync.presenters;

public class ProfilePresenter {
    /**
     * Create Profile Presenter for Business logic
     */
    public ProfilePresenter () {
        //take in view interface
    }

    //Section Title Data
    public String getGeneralSectionTitle() {
        return "GENERAL INFO";
    }

    public String getHealthSectionTitle() {
        return "HEALTH INFO";
    }

    public String getEmergencyContactSectionTitle() {
        return "EMERGENCY CONTACT INFO";
    }

    public String getInsuranceSectionTitle() {
        return "INSURANCE INFO";
    }

    //General Info Section
    public String getActionBarTitle() {
        return "Personal profile";
    }

    public String getFullNameLabel() {
        return "Full Name";
    }

    public String getFullName() {
        return "Erin Gallagher";
    }

    public String getBirthDateLabel() {
        return "Birth Date";
    }

    public String getBirthDate() {
        return "March 24, 1994";
    }

    public String getAgeLabel() {
        return "Age";
    }

    public String getAge() {
        return "23";
    }

    public String getCitizenshipLabel() {
        return "Citizenship";
    }

    public String getCitizenship() {
        return "Canadian";
    }

    //Health Section
    public String getBloodTypeLabel() {
        return "Blood Type";
    }

    public String getBloodType() {
        return "O Negative";
    }

    public String getAllergiesLabel() {
        return "Allergies";
    }

    public String getAllergies() {
        //TODO: use model to retrieve list
        return "Scented Creme, Heat, Maple Trees";
    }

    public String getMedicineLabel() {
        return "Medicine";
    }

    public String getMedicine() {
        return "None";
    }
}

