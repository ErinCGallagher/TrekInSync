package com.trekinsync.ering.trekinsync.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefsUtils {

    private SharedPrefsUtils() { /* cannot be instantiated */ }

    /**
     * Create a unique (relatively) key for the given user object.
     * @param user, User object to generate key for
     * @return String representing the key
     */
    public static String getTravelContactKey(User user) {
        String userKey = user.getName() + user.getCitizenship() + user.getAge();
        return userKey.replaceAll("\\s","");
    }

    /**
     * Retrieve Shared Preferences key given a user object by appending app name for uniqueness.
     * @param context, activity context
     * @param user, User object which key will be created to represent
     * @return return String key
     */
    public static String getKey(Context context, User user) {
        return getKey(context, getTravelContactKey(user));
    }

    /**
     * Retrieve Shared Preferences key given a key String by appending app name for uniqueness.
     * @param context, activity context
     * @param key, String representation of key
     * @return return String key
     */
    public static String getKey(Context context, String key) {
        return context.getString(R.string.app_name_key) + key;
    }

    /**
     * Retrieve Shared Preferences key given a key StringRes by appending app name for uniqueness.
     * @param context, activity context
     * @param keyId, StringRes representation of key
     * @return return String key
     */
    public static String getKey(Context context,@StringRes int keyId) {
        return context.getString(R.string.app_name_key) + context.getString(keyId);
    }


    /**
     * Check if travel contact key exists in shared preferences.
     * @param context
     * @param userObj, object representing user data
     * @return true if user data exists on device, otherwise false
     */
    public static boolean checkIfContactExists(Context context, User userObj) {
        //TODO: don't instantiate shared preferences twice
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key), Context.MODE_PRIVATE);
        String contactJson = sharedPref.getString(getKey(context, userObj), "false");

        //false response means contact key was not found on device
        if (contactJson.equals("false")) {
            return false;
        } else {
            return true; //contact exists
        }
    }

    /**
     * Remove contact key from travel contact key list in shared preferences and then remove contact
     * data from shared preferences.
     * @param context
     * @param user, object representing user data
     * @return true if contact is removed successfully, otherwise false
     */
    public static boolean removeTravelContact(Context context, User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Type listType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        //retrieve list of travel contact key names in shared preferences
        String json = sharedPref.getString(getKey(context, R.string.travel_contact_key_names), "");
        List<String> contactKeyNames = gson.fromJson(json, listType);

        //Remove key from list and put back in shared preferences
        contactKeyNames.remove(getTravelContactKey(user));
        String jsonKeyNames = gson.toJson(contactKeyNames);
        editor.putString(getKey(context, R.string.travel_contact_key_names), jsonKeyNames);

        //remove user data from shared preferences
        editor.remove(getKey(context, user));
        editor.apply();

        boolean contactExists = checkIfContactExists(context, user);
        //if contact still exists on device return false (failure to remove), otherwise true
        return !contactExists;
    }

    /**
     * Retrieve primary user's profile from shared preferences and convert to User object.
     * @return a User object otherwise null if error parsing
     */
    public static User convertSharedPrefsToUserModel(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);
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
    public static List<User> convertSharedPrefsToTravelContacts(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);

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


}
