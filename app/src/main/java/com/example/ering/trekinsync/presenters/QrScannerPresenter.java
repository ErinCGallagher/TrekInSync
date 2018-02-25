package com.example.ering.trekinsync.presenters;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.QrScannerView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class QrScannerPresenter {
    private Context context;
    private QrScannerView view;

    public QrScannerPresenter(Context context, QrScannerView view) {
        this.context = context;
        this.view = view;
    }

    /**
     * Handles QR Code scan results as a string.
     * Converts results to a User object, if possible, and adds the travel contact to shared prefs.
     * If the data cannot be added to the device, the user will be returned to the landing activity.
     * @param results
     */
    public void handleQrCodeScanResults(String results) {
        User userObj = convertToUserObject(results);

        if (userObj != null && checkIfContactExists(userObj)) {
            addContactToSharedPrefs(userObj);
        } else {
            Toast.makeText(context, "This travel contact already exists on your device.", Toast.LENGTH_LONG).show();
        }
        view.returnToLandingActivity();
    }

    /**
     * Determines if the user has granted camera permission for this app.
     * @return true is permissions have been granted, otherwise false.
     */
    public boolean isCameraAccessGranted() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Convert string json data to User object model if possible.
     * @param data, json representation of User Model
     * @return User object model if parsing is successful, otherwise null
     */
    private User convertToUserObject(String data) {
        Gson gson = new Gson();
        User userObj= null;
        try {
            userObj = gson.fromJson(data, User.class);
            userObj.setIsPersonalProfile(false);
        } catch(Exception e) {
            Toast.makeText(context, "Unable to retrieve Travel Contact Details From QR code ", Toast.LENGTH_LONG).show();
        }
        return userObj;
    }

    /**
     * Check if travel contact key exists in shared preferences.
     * @param userObj, object representing user data
     * @return true if user data exists on device, otherwise false
     */
    private boolean checkIfContactExists(User userObj) {
        //TODO: don't instantiate shared preferences twice
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData", Context.MODE_PRIVATE);
        String contactJson = sharedPref.getString(SharedPrefsUtils.getKey(context, userObj), "false");

        if (contactJson == "false") {
            return true;
        } else {
            return false;
        }
    }

    private void addContactToSharedPrefs(User userObj) {
        //TODO: don't instantiate shared preferences twice
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Type listType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        //retrieve list of travel contact key names in shared preferences
        String json = sharedPref.getString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), "");
        List<String> contactKeyNames = gson.fromJson(json, listType);

        //Add new key name to list and put back in shared preferences
        contactKeyNames.add(SharedPrefsUtils.getTravelContactKey(userObj));
        String jsonKeyNames = gson.toJson(contactKeyNames);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), jsonKeyNames);

        //Add new user details to shared preferences
        String contactJson = gson.toJson(userObj);
        editor.putString(SharedPrefsUtils.getKey(context, userObj), contactJson);
        editor.apply();
    }
}
