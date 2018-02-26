package com.example.ering.trekinsync.presenters;

import android.content.Context;

import com.example.ering.trekinsync.interfaces.EditProfileView;

public class EditProfilePresenter {

    private EditProfileView view;
    private Context context;

    /**
     * Create Profile Presenter for Business logic
     */
    public EditProfilePresenter(Context context, EditProfileView view) {
        this.context = context;
        this.view = view;
    }

}
