package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LandingPresenter {

    private User user;
    private List<User> contacts;
    private SharedPreferences sharedPref;
    private Context context;

    /**
     * Create Profile Presenter for Business logic
     */
    public LandingPresenter (SharedPreferences sharedPref, Context context) {
        this.sharedPref = sharedPref;
        this.context = context;

        this.user = retrievePersonalProfileData();
        if (this.user == null ) {
            //TODO start Error flow and recreation of user profile
        }
        this.contacts = retrieveTravelContacts();
    }

    public String getSectionTitle() {
        return "Travel Contacts";
    }

    public User getUser() {
        return user;
    }

    public List<User> getTravelContacts() {
        return contacts;
    }


    /* Private Functions */

    /**
     * Retrieves the User's profile data from shared preferences if exists, otherwise start user
     * profile creation flow.
     * @return User object if exists, otherwise null.
     */
    private User retrievePersonalProfileData() {
        if (checkIfProfileExists()) {
            return convertSharedPrefsToUserModel();
        } else {
            //TODO: User creation flow initiated
            return createSharedPrefTestData();
        }
    }

    /**
     * Retrieve created profile key from shared preferences to indicate whether this user
     * has a profile stored on device.
     * @return true if profile exists, otherwise false
     */
    private boolean checkIfProfileExists() {
        String profileCreatedKey = context.getString(R.string.app_name_key) + context.getString(R.string.created_profile_key);
        String profileCreated = sharedPref.getString(profileCreatedKey, "false");
        return Boolean.valueOf(profileCreated);
    }

    /**
     * Retrieves the User's travel contact data from shared preferences if exists, otherwise
     * initiates empty contacts UI.
     * @return List of User object if they exist, otherwise null.
     */
    private List<User> retrieveTravelContacts() {
        if (checkIfContactKeysExists()) {
            return convertSharedPrefsToTravelContacts();
        }
        //TODO: display no contacts UI below header
        return createTravelContactTestData();
    }

    /**
     * Retrieve travel contact key name list from shared preferences to indicate whether contacts exist.
     * @return true if travel contacts exists, otherwise false
     */
    private boolean checkIfContactKeysExists() {
        String contactKeys = SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names);
        String contactKeysExist = sharedPref.getString(contactKeys, "false");
        //TODO: catch empty list case
        if (contactKeysExist == "false") {
            return false;
        }
        return true;
    }

    /**
     * Retrieve primary user's profile from shared preferences and convert to User object.
     * @return a User object otherwise null if error parsing
     */
    private User convertSharedPrefsToUserModel() {
        Gson gson = new Gson();
        String json = sharedPref.getString(SharedPrefsUtils.getKey(context, R.string.primary_profile_key), "");
        User userObj = null;
        try {
            userObj = gson.fromJson(json, User.class);
        } catch(Exception e) {
            //TODO create error flow if unable to parse
            Toast.makeText(context, "Error parsing user profile from shared preferences", Toast.LENGTH_LONG).show();
        }
        return userObj;
    }

    /**
     * Retrieve the list of travel contact key names from shared preferences in order to retrieve contact details.
     * @return List of User object retrieved from shared preferences
     */
    private List<User> convertSharedPrefsToTravelContacts() {
        Type listType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        //retrieve list of travel contact key names in shared preferences
        String json = sharedPref.getString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), "");
        List<String> contactKeyNames = gson.fromJson(json, listType);

        //retrieve travel contact details from shared preferences given the key names
        List<User> contactList = new ArrayList<>();
        for(String keyName: contactKeyNames) {
            String contactJson = sharedPref.getString(SharedPrefsUtils.getKey(context, keyName), "");
            if(json != null ){
                User userObj = gson.fromJson(contactJson, User.class);
                contactList.add(userObj);
            }
        }
        return contactList;
    }


    /* Test Data creation to be removed */

    //TODO remove once create profile flow completed
    private User createSharedPrefTestData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SharedPrefsUtils.getKey(context, R.string.created_profile_key), "true");
        Gson gson = new Gson();
        String json = gson.toJson(getTestUserObject("Erin Gallagher", "American"));
        editor.putString(SharedPrefsUtils.getKey(context, R.string.primary_profile_key), json);
        editor.apply();

        return convertSharedPrefsToUserModel();
    }

    //TODO remove once add travel contact flow complete
    private List<User> createTravelContactTestData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        User testUser1 = getTestUserObject("Christina Chan", "Canadian");
        User testUser2 = getTestUserObject("Laura Brooks", "British");
        User testUser3 = getTestUserObject("Lexi Flynn", "Mexican");

        List<String> contactKeys = new ArrayList<>();
        contactKeys.add(SharedPrefsUtils.createTravelContactKey(testUser1));
        contactKeys.add(SharedPrefsUtils.createTravelContactKey(testUser2));
        contactKeys.add(SharedPrefsUtils.createTravelContactKey(testUser3));
        String json = gson.toJson(contactKeys);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), json);

        String contactJson = gson.toJson(testUser1);
        editor.putString(SharedPrefsUtils.getKey(context, testUser1), contactJson);

        String contactJson2 = gson.toJson(testUser2);
        editor.putString(SharedPrefsUtils.getKey(context, testUser2), contactJson2);

        String contactJson3 = gson.toJson(testUser3);
        editor.putString(SharedPrefsUtils.getKey(context, testUser3), contactJson3);
        editor.apply();

        return convertSharedPrefsToTravelContacts();
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
