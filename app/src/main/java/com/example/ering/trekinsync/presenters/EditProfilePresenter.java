package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.EditProfileView;
import com.example.ering.trekinsync.models.User;

public class EditProfilePresenter {

    private EditProfileView view;
    private Context context;
    private User user;

    /**
     * Create Profile Presenter for Business logic
     */
    public EditProfilePresenter(Context context, EditProfileView view, User user) {
        this.context = context;
        this.view = view;
        this.user = user;
    }

    public String getActionBarTitle() {
        if (user != null) {
            return context.getString(R.string.edit_profile_action_bar_title);
        } else {
            return context.getString(R.string.create_profile_action_bar_title);
        }
    }

    public void handleRowItemSelection(View v, int position) {
        view.launchPopUp(v);
    }

}
