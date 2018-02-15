package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.SharedPreferences;

public class LandingPresenter {
    private SharedPreferences sharedPref;
    private Context context;

    /**
     * Create Profile Presenter for Business logic
     */
    public LandingPresenter (SharedPreferences sharedPref, Context context) {
        this.sharedPref = sharedPref;
        this.context = context;
    }

    public String getSectionTitle() {
        return "Travel Contacts";
    }

}
