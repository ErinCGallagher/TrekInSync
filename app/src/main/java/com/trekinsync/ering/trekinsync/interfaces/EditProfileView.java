package com.trekinsync.ering.trekinsync.interfaces;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.ArrayRes;

import com.trekinsync.ering.trekinsync.adapters.IconSelectionAdapter;
import com.trekinsync.ering.trekinsync.models.User;

import java.util.Date;

/**
 * Create an interface for view related activities
 */
public interface EditProfileView {
    void launchPopUp(String title, @ArrayRes int itemsId, int checkedItem, DialogInterface.OnClickListener listener);
    void launchPopUp(String title, IconSelectionAdapter adapter, DialogInterface.OnClickListener listener);
    void launchDatePicker(Date birthDate, DatePickerDialog.OnDateSetListener listener);
    void reloadData();
    void launchProfileView(User user);
    void launchLandingView();
    void launchBackButtonAction();
    void launchConfirmationAlert();
}
