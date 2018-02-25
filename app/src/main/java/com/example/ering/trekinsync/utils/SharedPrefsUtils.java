package com.example.ering.trekinsync.utils;

import android.content.Context;
import android.support.annotation.StringRes;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.User;

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


}
