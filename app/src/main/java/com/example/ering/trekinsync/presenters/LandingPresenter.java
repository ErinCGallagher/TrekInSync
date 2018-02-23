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

import java.util.ArrayList;

public class LandingPresenter {

    private User user;
    private ArrayList<User> contacts;
    private SharedPreferences sharedPref;
    private Context context;

    /**
     * Create Profile Presenter for Business logic
     */
    public LandingPresenter (SharedPreferences sharedPref, Context context) {
        this.sharedPref = sharedPref;
        this.context = context;
        //TODO confirm user data was returned
        this.user = retrievePersonalProfileData();
        this.contacts = retrieveTravelContacts();
    }

    public String getSectionTitle() {
        return "Travel Contacts";
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getTravelContacts() {
        return contacts;
    }

    /**
     *  Private Functions
     */

    private User retrievePersonalProfileData() {
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

    private ArrayList<User> retrieveTravelContacts() {
        if (checkIfContactKeysExists()) {
            return convertSharedPrefsToTravelContacts();
        }
        return createTravelContactTestData();
    }

    private boolean checkIfContactKeysExists() {
        String contactKeys = getKey(R.string.travel_contact_key_names);
        String contactKeysExist = sharedPref.getString(contactKeys, "false");
        if (contactKeysExist == "false") {
            return false;
        }
        return true;
    }

    //TODO remove once create profile flow completed
    private User createSharedPrefTestData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getKey(R.string.created_profile_key), "true");
        Gson gson = new Gson();
        String json = gson.toJson(getTestUserObject("Erin Gallagher", "American"));
        editor.putString(getKey(R.string.primary_profile_key), json);
        editor.apply();

        return convertSharedPrefsToUserModel();
    }

    //TODO remove once add travel contact flow complete
    private ArrayList<User> createTravelContactTestData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String[] contactKeys = new String[3];
        contactKeys[0] = "contact1";
        contactKeys[1] = "contact2";
        contactKeys[2] = "contact3";
        String json = gson.toJson(contactKeys);
        editor.putString(getKey(R.string.travel_contact_key_names), json);

        String contactJson = gson.toJson(getTestUserObject("Christina Chan", "Canadian"));
        editor.putString(getKey(contactKeys[0]), contactJson);

        String contactJson2 = gson.toJson(getTestUserObject("Laura Brooks", "British"));
        editor.putString(getKey(contactKeys[1]), contactJson2);

        String contactJson3 = gson.toJson(getTestUserObject("Lexi Flynn", "Mexican"));
        editor.putString(getKey(contactKeys[2]), contactJson3);
        editor.apply();

        return convertSharedPrefsToTravelContacts();
    }

    private String getKey(String key) {
        return context.getString(R.string.app_name_key) + key;
    }

    private String getKey(@StringRes int keyId) {
        return context.getString(R.string.app_name_key) + context.getString(keyId);
    }

    private User convertSharedPrefsToUserModel() {
        Gson gson = new Gson();
        String json = sharedPref.getString(getKey(R.string.primary_profile_key), "");
        //TODO: check if json is an empty string, display error pop-up and go to create profile view
        User userObj = gson.fromJson(json, User.class);
        return userObj;
    }

    private ArrayList<User> convertSharedPrefsToTravelContacts() {
        ArrayList<User> contactList = new ArrayList<>();
        Gson gson = new Gson();
        String json = sharedPref.getString(getKey(R.string.travel_contact_key_names), "");
        String[] contactKeyNames = gson.fromJson(json, String[].class);
        for(String keyName: contactKeyNames) {
            String contactJson = sharedPref.getString(getKey(keyName), "");
            if(json != null ){
                User userObj = gson.fromJson(contactJson, User.class);
                contactList.add(userObj);
            }
        }
        return contactList;
    }

    //TODO remove once create profile flow completed
    private User getTestUserObject(String name, String citizenship) {
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

        User user = new User(true,
                name,
                "March 23, 1994",
                13,
                citizenship,
                "O neutral",
                "scented shit",
                "none",
                emergencyContactArray,
                insuranceCompanyArray);
        return user;
    }

}
