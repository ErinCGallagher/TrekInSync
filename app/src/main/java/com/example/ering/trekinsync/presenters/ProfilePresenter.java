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
    private SharedPreferences sharedPref;
    private Context context;

    /**
     * Create Profile Presenter for Business logic
     */
    public ProfilePresenter (SharedPreferences sharedPref, Context context) {
        this.sharedPref = sharedPref;
        this.context = context;
        //TODO confirm user data was returned
        this.user = retrieveUserData();
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

    /** Private Functions */

    private User retrieveUserData() {
        //TODO: check if first time using app and create shared preferences or get them
        if (checkIfProfileExists()) {
            return convertSharedPrefsToUserModel();
        } else {
            return createSharedPrefTestData();
        }
    }

    private boolean checkIfProfileExists() {
        String profileCreatedKey = context.getString(R.string.app_name_key) + context.getString(R.string.created_profile_key);
        String profileCreated = sharedPref.getString(profileCreatedKey, "false");
        return Boolean.valueOf(profileCreated);
    }

    private User createSharedPrefTestData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getKey(R.string.created_profile_key), "true");
        Gson gson = new Gson();
        String json = gson.toJson(getTestUserObject());
        editor.putString(getKey(R.string.primary_profile_key), json);
        editor.apply();

        return convertSharedPrefsToUserModel();
    }

    private String getKey(@StringRes int keyId) {
        return context.getString(R.string.app_name_key) + context.getString(keyId);
    }

//    private String getStringValueGivenKey(@StringRes int keyId) {
//        String value = sharedPref.getString(getKey(keyId), context.getString(R.string.cannot_find_key));
//        return value;
//    }
//
//    private int getIntValueGivenKey(@StringRes int keyId) {
//        int value = sharedPref.getInt(getKey(keyId), 0);
//        return value;
//    }

    private User convertSharedPrefsToUserModel() {
        Gson gson = new Gson();
        String json = sharedPref.getString(getKey(R.string.primary_profile_key), "");
        //TODO: check if json is an empty string, display error pop-up and go to create profile view
        User userObj = gson.fromJson(json, User.class) ;
        return userObj;
    }

    //TODO remove once create profile flow completed
    private User getTestUserObject() {
        PolicyInfo policyInfo = new PolicyInfo("policy #", "12345");
        PolicyInfo policyInfo2 = new PolicyInfo("cert #", "098");
        PolicyInfo[] policyInfoArray = new PolicyInfo[2];
        policyInfoArray[0] = policyInfo;
        policyInfoArray[1] = policyInfo2;

        InsuranceCompany insuranceCompany = new InsuranceCompany("Manulife", "416-098-4663", true, policyInfoArray);
        InsuranceCompany[] insuranceCompanyArray = new InsuranceCompany[1];
        insuranceCompanyArray[0] = insuranceCompany;

        EmergencyContact emergencyContact = new EmergencyContact("Mother", "416-747-3625", "Cell");
        EmergencyContact emergencyContact2 = new EmergencyContact("Father", "416-888-9865", "Work");
        EmergencyContact[] emergencyContactArray = new EmergencyContact[2];
        emergencyContactArray[0] = emergencyContact;
        emergencyContactArray[1] = emergencyContact2;

        User user = new User("Erin Gallagher",
                "March 23, 1994",
                13,
                "US",
                "O neutral",
                "scented shit",
                "none",
                emergencyContactArray,
                insuranceCompanyArray);
        return user;
    }
}

