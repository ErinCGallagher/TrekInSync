package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.models.User;

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
        this.user = retrieveUserData();
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
        return user.getName();
    }

    public String getBirthDateLabel() {
        return "Birth Date";
    }

    public String getBirthDate() {
        return user.getBirthDate();
    }

    public String getAgeLabel() {
        return "Age";
    }

    public String getAge() {
        return Integer.toString(user.getAge());
    }

    public String getCitizenshipLabel() {
        return "Citizenship";
    }

    public String getCitizenship() {
        return user.getCitizenship();
    }

    //Health Section
    public String getBloodTypeLabel() {
        return "Blood Type";
    }

    public String getBloodType() {
        return user.getBloodType();
    }

    public String getAllergiesLabel() {
        return "Allergies";
    }

    public String getAllergies() {
        //TODO: use model to retrieve list, or could potentially be large string
        return user.getAllergies();
    }

    public String getMedicineLabel() {
        return "Medicine";
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
        return "Call First";
    }

    public InsuranceCompany[] getInsuranceCompanies() {
        //TODO null check
        return user.getInsuranceInfo();
    }

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
        editor.putString(getKey(R.string.full_name_key), "Lisa Thomas");
        editor.putString(getKey(R.string.birth_day_key), "March 28, 1996");
        editor.putInt(getKey(R.string.age_key), 21);
        editor.putString(getKey(R.string.citizenship_key), "Canadian");
        editor.putString(getKey(R.string.blood_type_key), "O Positive");
        editor.putString(getKey(R.string.allergies_key), "Scented Creme, Heat, Maple Trees");
        editor.putString(getKey(R.string.medication_key), "Some but not important");
        editor.apply();

        return convertSharedPrefsToUserModel();
    }

    private String getKey(@StringRes int keyId) {
        return context.getString(R.string.app_name_key) + context.getString(keyId);
    }

    private String getStringValueGivenKey(@StringRes int keyId) {
        String value = sharedPref.getString(getKey(keyId), context.getString(R.string.cannot_find_key));
        return value;
    }

    private int getIntValueGivenKey(@StringRes int keyId) {
        int value = sharedPref.getInt(getKey(keyId), 0);
        return value;
    }

    private User convertSharedPrefsToUserModel() {
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

        User user = new User(getStringValueGivenKey(R.string.full_name_key),
                getStringValueGivenKey(R.string.birth_day_key),
                getIntValueGivenKey(R.string.age_key),
                getStringValueGivenKey(R.string.citizenship_key),
                getStringValueGivenKey(R.string.blood_type_key),
                getStringValueGivenKey(R.string.allergies_key),
                getStringValueGivenKey(R.string.medication_key),
                emergencyContactArray,
                insuranceCompanyArray);
        return user;
    }
}

