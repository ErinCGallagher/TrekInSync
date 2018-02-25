package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.ProfileView;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProfilePresenter {
    private User user;
    private Context context;
    private ProfileView view;

    /**
     * Create Profile Presenter for Business logic
     */
    public ProfilePresenter (User user, Context context, ProfileView view) {
        this.context = context;
        this.user = user;
        this.view = view;
    }

    //Section Title Data
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

    //General Info Section
    public String getActionBarTitle() {
        if (user != null && user.getIsPersonalProfile()) {
            return context.getString(R.string.personal_profile_action_bar_title);
        } else {
            return context.getString(R.string.travel_contact_profile_action_bar_title);
        }
    }

    public String getFullNameLabel() {
        return context.getString(R.string.full_name_label);
    }

    public String getFullName() {
        return user.getName() != null ? user.getName() : "";
    }

    public String getBirthDateLabel() {
        return context.getString(R.string.birth_date_label);
    }

    public String getBirthDate() {
        return user.getBirthDate() != null ? user.getBirthDate() : "";
    }

    public String getAgeLabel() {
        return context.getString(R.string.age_label);
    }

    public String getAge() {
        return Integer.toString(user.getAge());
    }

    public String getCitizenshipLabel() {
        return context.getString(R.string.citizenship_label);
    }

    public String getCitizenship() {
        return user.getCitizenship() != null ? user.getCitizenship() : "";
    }

    //Health Section
    public String getBloodTypeLabel() {
        return context.getString(R.string.blood_type_label);
    }

    public String getBloodType() {
        return user.getBloodType() != null ? user.getBloodType() : "";
    }

    public String getAllergiesLabel() {
        return context.getString(R.string.allergies_label);
    }

    public String getAllergies() {
        //TODO: use model to retrieve list, or could potentially be large string
        return user.getAllergies() != null ? user.getAllergies() : "";
    }

    public String getMedicineLabel() {
        return context.getString(R.string.medication_label);
    }

    public String getMedicine() {
        return user.getMedicine() != null ? user.getMedicine() : "";
    }

    //Emergency Contact section
    public EmergencyContact[] getEmergencyContacts() {
        //TODO null check
        return user.getEmergencyContacts();
    }

    //Insurance Company Section
    public String getCallFirstLabel() {
        return context.getString(R.string.call_first_label);
    }

    public InsuranceCompany[] getInsuranceCompanies() {
        //TODO null check
        return user.getInsuranceInfo();
    }

    /**
     * Handle Menu setup with edit button for personal profile, otherwise delete button.
     * @param menu, return menu for display
     */
    public void handleMenuSetup(Menu menu) {
        if (user != null && user.getIsPersonalProfile()) {
            MenuItem item = menu.findItem(R.id.action_delete);
            item.setVisible(false);
        } else {
            MenuItem item = menu.findItem(R.id.action_edit);
            item.setVisible(false);
        }
        view.refreshMenu();
    }

    public void handleEditProfileButtonClick() {
        //TODO: start edit profile flow
    }

    /**
     * Launch delete confirmation dialog to confirm user wants to delete contact.
     */
    public void handleDeleteProfileButtonClick() {
        view.launchConfirmationAlert();
    }

    /**
     * Remove contact from shared preferences, display success toast and then launch landing page.
     */
    public void handleDeleteContactConfirmation() {
        deleteContact();
        Toast.makeText(context, "Contact " + user.getName() + " was deleted successfully", Toast.LENGTH_LONG).show();
        view.launchLandingPage();
    }

    /**
     * Remove contact key from travel contact key list in shared preferences and then remove contact
     * data from shared preferences.
     */
    private void deleteContact() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Type listType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        //retrieve list of travel contact key names in shared preferences
        String json = sharedPref.getString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), "");
        List<String> contactKeyNames = gson.fromJson(json, listType);

        //Remove key from list and put back in shared preferences
        contactKeyNames.remove(SharedPrefsUtils.getTravelContactKey(user));
        String jsonKeyNames = gson.toJson(contactKeyNames);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), jsonKeyNames);

        //remove user data from shared preferences
        editor.remove(SharedPrefsUtils.getKey(context, user));
        editor.apply();
    }
}

