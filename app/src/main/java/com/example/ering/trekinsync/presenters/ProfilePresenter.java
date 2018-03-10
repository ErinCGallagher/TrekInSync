package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.ProfileView;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.example.ering.trekinsync.utils.UserSingletonUtils;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        view.reloadData();
    }

    //Section Title Data
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

    public @ColorInt int getHeaderColor() {
        if (user.getIsPersonalProfile()) {
            return ContextCompat.getColor(context, R.color.colorPrimary);
        } else {
            return ContextCompat.getColor(context, R.color.colorSecondary);
        }
    }

    public Drawable getProfileIcon() {
        return context.getResources().getDrawable(R.drawable.placeholder_profile_icon_personal);
    }

    public String getFullName() {
        return user.getName() != null ? user.getName() : "";
    }

    public String getBirthDateLabel() {
        return context.getString(R.string.birth_date_label);
    }

    public String getBirthDate() {
        return user.getFormattedBirthDate() != null ? user.getFormattedBirthDate() : "";
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
        return user.getCitizenship() != null ?
                UserSingletonUtils.getInstance().getFormattedCountry(user.getCitizenship()) : "";
    }

    //Health Section
    public String getBloodTypeLabel() {
        return context.getString(R.string.blood_type_label);
    }

    public String getBloodType() {
        return user.getBloodType() != null ?
                UserSingletonUtils.getInstance().getFormattedBloodType(user.getBloodType()) : "";
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
        view.launchEditProfile();
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
        boolean result = SharedPrefsUtils.removeTravelContact(context, user);
        if (result) {
            Toast.makeText(context, "Contact " + user.getName() + " was deleted successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "An Error occurred while trying to delete travel contact " + user.getName(), Toast.LENGTH_LONG).show();
        }
        view.launchLandingPage();
    }
}

