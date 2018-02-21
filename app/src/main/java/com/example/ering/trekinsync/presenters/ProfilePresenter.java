package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.models.User;
import com.google.gson.Gson;

public class ProfilePresenter {
    private User user;
    private Context context;

    /**
     * Create Profile Presenter for Business logic
     */
    public ProfilePresenter (User user, Context context) {
        this.context = context;
        this.user = user;
        //take in view interface
    }

    //Section Title Data
    public String getGeneralSectionTitle() {
        return context.getString(R.string.general_section_title);
    }

    public String getHealthSectionTitle() {
        return context.getString(R.string.health_section_title);
    }

    public String getEmergencyContactSectionTitle() {
        return context.getString(R.string.emergency_contact_section_title);
    }

    public String getInsuranceSectionTitle() {
        return context.getString(R.string.insurance_section_title);
    }

    //General Info Section
    public String getActionBarTitle() {
        return context.getString(R.string.profile_action_bar_title);
    }

    public String getFullNameLabel() {
        return context.getString(R.string.full_name_label);
    }

    public String getFullName() {
        return user.getName();
    }

    public String getBirthDateLabel() {
        return context.getString(R.string.birth_date_label);
    }

    public String getBirthDate() {
        return user.getBirthDate();
    }

    public String getAgeLabel() {
        return context.getString(R.string.age_label);
    }

    public String getAge() {
        return Integer.toString(user.getAge());
    }

    public String getCitizenshipLabel() {
        return context.getString(R.string.citizenship_label);
    }

    public String getCitizenship() {
        return user.getCitizenship();
    }

    //Health Section
    public String getBloodTypeLabel() {
        return context.getString(R.string.blood_type_label);
    }

    public String getBloodType() {
        return user.getBloodType();
    }

    public String getAllergiesLabel() {
        return context.getString(R.string.allergies_label);
    }

    public String getAllergies() {
        //TODO: use model to retrieve list, or could potentially be large string
        return user.getAllergies();
    }

    public String getMedicineLabel() {
        return context.getString(R.string.medication_label);
    }

    public String getMedicine() {
        return user.getMedicine();
    }

    //Emergency Contact section
    public EmergencyContact[] getEmergencyContacts() {
        //TODO null check
        return user.getEmergencyContacts();
    }

    //Insurance Company Section
    public String getCallFirstLabel() {
        return context.getString(R.string.call_first_label);
    }

    public InsuranceCompany[] getInsuranceCompanies() {
        //TODO null check
        return user.getInsuranceInfo();
    }
}

