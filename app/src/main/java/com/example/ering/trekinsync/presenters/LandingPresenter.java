package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.LandingView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LandingPresenter {

    private User user;
    private List<User> contacts;
    private SharedPreferences sharedPref;
    private Context context;
    private LandingView view;

    /**
     * Create Profile Presenter for Business logic
     */
    public LandingPresenter (SharedPreferences sharedPref, Context context, LandingView view) {
        this.sharedPref = sharedPref;
        this.context = context;
        this.view = view;

        this.user = retrievePersonalProfileData();
        if (this.user == null ) {
            //launch create profile flow since none exists
            view.launchCreateProfilePage();
            return;
        }
        this.contacts = retrieveTravelContacts();
        if (this.contacts != null && !contacts.isEmpty()) {
            removeExpiredContacts();
            sortTravelContacts();
        } else {
            //TODO: display no travel contacts UI
        }
    }

    public void updateProfileAndContacts() {
        this.user = retrievePersonalProfileData();
        this.contacts = retrieveTravelContacts();
        view.reloadData();
    }

    public String getSectionTitle() {
        return "Travel Contacts";
    }

    public User getUser() {
        return user;
    }

    public String getUserName() {
        if (user != null ){
            return user.getName();
        }
        return "";
    }

    public List<User> getTravelContacts() {
        return contacts;
    }

    public void handleTravelContactClick(int position) {
        User selectedContact = contacts.get(position-1);
        view.launchProfilePage(selectedContact);
    }

    /* Handle Drawer Menu */

    public void handleNavMenuViewProfile() {
        if (user != null) {
            view.launchProfilePage(user);
        }
    }

    public void handleNavMenuEditProfile() {
        if (user != null) {
            view.launchEditProfilePage(user);
        }
    }


    /* Private Functions */

    /**
     * Iterate over travel contacts and remove any that have expired.
     * If the contact expiry date is today or after today the contact is considered expired.
     */
    private void removeExpiredContacts() {
        int numContactsExpired = 0;
        Date dateToday = new Date();
        for (Iterator<User> it = contacts.iterator(); it.hasNext();) {
            User contact = it.next();
            Date contactExpiryDate = contact.getContactExpiryDate();
            if (dateToday.after(contactExpiryDate) || DateUtils.isToday(contactExpiryDate.getTime())) {
                boolean result = SharedPrefsUtils.removeTravelContact(context, contact);
                if (result) {
                    it.remove();
                    numContactsExpired++;
                }
            }
        }
        if (numContactsExpired > 0) {
            Toast.makeText(context, "Removed " + numContactsExpired + " expired contacts", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * sort the contacts by citizenship and then by name.
     */
    private void sortTravelContacts() {
        Collections.sort(this.contacts, new Comparator<User>(){
            public int compare(User obj1, User obj2) {
                // ## Ascending order
                return obj1.getSortingString().compareToIgnoreCase(obj2.getSortingString());
            }
        });
    }

    /**
     * Retrieves the User's profile data from shared preferences if exists, otherwise start user
     * profile creation flow.
     * @return User object if exists, otherwise null.
     */
    private User retrievePersonalProfileData() {
        if (checkIfProfileExists()) {
            return SharedPrefsUtils.convertSharedPrefsToUserModel(context);
        } else {
            return null;
        }
    }

    /**
     * Retrieve created profile key from shared preferences to indicate whether this user
     * has a profile stored on device.
     * @return true if profile exists, otherwise false
     */
    private boolean checkIfProfileExists() {
        String profileCreatedKey = context.getString(R.string.app_name_key) + context.getString(R.string.created_profile_key);
        String profileCreated = sharedPref.getString(profileCreatedKey, "false");
        return Boolean.valueOf(profileCreated);
    }

    /**
     * Retrieves the User's travel contact data from shared preferences if exists, otherwise
     * initiates empty contacts UI.
     * @return List of User object if they exist, otherwise null.
     */
    private List<User> retrieveTravelContacts() {
        if (checkIfContactKeysExists()) {
            return SharedPrefsUtils.convertSharedPrefsToTravelContacts(context);
        }
        //TODO: display no contacts UI below header
        return new ArrayList<>();
    }

    /**
     * Retrieve travel contact key name list from shared preferences to indicate whether contacts exist.
     * @return true if travel contacts exists, otherwise false
     */
    private boolean checkIfContactKeysExists() {
        String contactKeys = SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names);
        String contactKeysExist = sharedPref.getString(contactKeys, "false");
        //TODO: catch empty list case
        if (contactKeysExist == "false") {
            return false;
        }
        return true;
    }

}
