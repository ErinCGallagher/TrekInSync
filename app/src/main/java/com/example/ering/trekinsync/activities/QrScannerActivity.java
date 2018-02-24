package com.example.ering.trekinsync.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.Result;

import java.lang.reflect.Type;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final int PERMISSION_REQUEST_CAMERA = 1;

    private ZXingScannerView mScannerView;
    private Context context;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_qr_code_scanner);
        context = getApplicationContext();

        ViewGroup scannerContentFrame = (ViewGroup) findViewById(R.id.scanner_frame);
        mScannerView = new ZXingScannerView(this);
        scannerContentFrame.addView(mScannerView);

        if (!checkCameraPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        User userObj = convertToUserObject(rawResult.toString());

        if (userObj != null && checkIfContactExists(userObj)) {
            addContactToSharedPrefs(userObj);
        } else {
            Toast.makeText(this, "This travel contact already exists on your device.", Toast.LENGTH_LONG).show();
        }
        returnToLandingActivity();
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO: swap if statement
                } else {
                    Toast.makeText(this, "Camera Permission is required to scan travel contact QR codes", Toast.LENGTH_LONG).show();
                    returnToLandingActivity();
                }
                return;
            }
        }
    }

    private void returnToLandingActivity() {
        Intent intent = new Intent(context, LandingActivity.class);
        startActivity(intent);
    }

    private User convertToUserObject(String data) {
        Gson gson = new Gson();
        User userObj= null;
        try {
            userObj = gson.fromJson(data, User.class);
        } catch(Exception e) {
            Toast.makeText(this, "Unable to retrieve Travel Contact Details From QR code ", Toast.LENGTH_LONG).show();
        }
        return userObj;
    }

    /**
     * Check if travel contact key exists in shared preferences.
     * @param userObj, object representing user data
     * @return true if user data exists on device, otherwise false
     */
    private boolean checkIfContactExists(User userObj) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        String contactJson = sharedPref.getString(SharedPrefsUtils.getKey(context, userObj), "false");

        if (contactJson == "false") {
            return true;
        } else {
            return false;
        }
    }

    private void addContactToSharedPrefs(User userObj) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Type listType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        //retrieve list of travel contact key names in shared preferences
        String json = sharedPref.getString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), "");
        List<String> contactKeyNames = gson.fromJson(json, listType);

        //Add new key name to list and put back in shared preferences
        contactKeyNames.add(SharedPrefsUtils.createTravelContactKey(userObj));
        String jsonKeyNames = gson.toJson(contactKeyNames);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), jsonKeyNames);

        //Add new user details to shared preferences
        String contactJson = gson.toJson(userObj);
        editor.putString(SharedPrefsUtils.getKey(context, userObj), contactJson);
        editor.apply();
    }
}
