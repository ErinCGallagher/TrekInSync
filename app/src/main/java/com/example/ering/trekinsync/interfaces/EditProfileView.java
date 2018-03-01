package com.example.ering.trekinsync.interfaces;

import android.content.DialogInterface;
import android.support.annotation.ArrayRes;

import com.example.ering.trekinsync.models.User;

/**
 * Create an interface for view related activities
 */
public interface EditProfileView {
    void launchPopUp(String title, @ArrayRes int itemsId, int checkedItem, DialogInterface.OnClickListener listener);
    void reloadData();
    void launchProfileView(User user);
    void launchLandingView();
    void launchConfirmationAlert();
}
