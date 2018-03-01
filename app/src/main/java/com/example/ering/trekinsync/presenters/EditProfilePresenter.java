package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.activities.ProfileActivity;
import com.example.ering.trekinsync.interfaces.EditProfileView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.example.ering.trekinsync.utils.UserUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class EditProfilePresenter {

    private EditProfileView view;
    private Context context;
    private User user;

    private List<String> countryKeyList;
    private List<String> countryValuesList;
    private List<String> bloodTypeKeyList;
    private List<String> bloodTypeValuesList;


    /**
     * Create Profile Presenter for Business logic
     */
    public EditProfilePresenter(Context context, EditProfileView view, User user) {
        this.context = context;
        this.view = view;
        this.user = user;

        countryKeyList = Arrays.asList(context.getResources().getStringArray(R.array.citizenship_keys));
        countryValuesList = Arrays.asList(context.getResources().getStringArray(R.array.citizenship_values));
        bloodTypeKeyList = Arrays.asList(context.getResources().getStringArray(R.array.blood_type_keys));
        bloodTypeValuesList = Arrays.asList(context.getResources().getStringArray(R.array.blood_type_values));
    }

    public String getActionBarTitle() {
        if (user != null) {
            return context.getString(R.string.edit_profile_action_bar_title);
        } else {
            return context.getString(R.string.create_profile_action_bar_title);
        }
    }

    public void handleSaveProfileButtonClick() {
        //update primary user shared preferences
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SharedPrefsUtils.getKey(context, R.string.created_profile_key), "true");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.primary_profile_key), json);
        editor.apply();

        //Send updated user object back in intent
        view.launchProfileView(user);
    }

    /**
     * Launch back button confirmation dialog to confirm user wants to not save changes
     */
    public void handleBackButtonClick() {
        view.launchConfirmationAlert();
    }

    /* Section Title Data*/

    public String getGeneralSectionTitle() {
        return context.getString(R.string.general_section_title);
    }

    public String getHealthSectionTitle() {
        return context.getString(R.string.health_section_title);
    }

    public String getEmergencyContactSectionTitle() {
        return context.getString(R.string.emergency_contact_section_title);
    }

    public String getInsuranceSectionTitle() {
        return context.getString(R.string.insurance_section_title);
    }

    /* User Details */

    /**
     * Get formatted/translated country.
     * @return String country
     */
    public String getUserCitizenship() {
        return UserUtils.getFormattedCountry(countryKeyList, countryValuesList, user.getCitizenship());
    }

    /**
     * Get formatted/translated country.
     * @return String blood type
     */
    public String getUserBloodType() {
        return UserUtils.getFormattedBloodType(bloodTypeKeyList, bloodTypeValuesList, user.getBloodType());
    }

    /* On Click Listeners */

    /**
     * Create listener for Citizenship row click. Launch Alert spinner on click.
     * @return OnClickListener
     */
    public View.OnClickListener createCitizenshipDropDownRowListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.launchPopUp("Select Country with Citizenship",
                        R.array.citizenship_values,
                        UserUtils.getCountryPosition(countryValuesList, getUserCitizenship()),
                        createCountrySelectionListener());
            }
        };
    }

    /**
     * Create listener for Blood Type row click. Launch Alert spinner on click.
     * @return OnClickListener
     */
    public View.OnClickListener createBloodTypeDropDownListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.launchPopUp("Select Blood Type",
                        R.array.blood_type_values,
                        UserUtils.getBloodTypePosition(bloodTypeValuesList, getUserBloodType()),
                        createBloodTypeSelectionListener());
            }
        };
    }

    /* Dialog alert views */

    /**
     * Create Alert spinner for country with citizenship selection.
     * @return DialogInterface.OnClickListener
     */
    private DialogInterface.OnClickListener createCountrySelectionListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setCitizenship(UserUtils.getCountryCode(countryKeyList, countryValuesList, countryValuesList.get(which)));
                view.reloadData();
                dialog.dismiss();
            }
        };
    }

    /**
     * Create Alert spinner for blood type selection.
     * @return DialogInterface.OnClickListener
     */
    private DialogInterface.OnClickListener createBloodTypeSelectionListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setBloodType(UserUtils.getBloodTypeCode(bloodTypeKeyList, bloodTypeValuesList, bloodTypeValuesList.get(which)));
                view.reloadData();
                dialog.dismiss();
            }
        };
    }

}
