package com.example.ering.trekinsync.presenters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.interfaces.EditProfileView;
import com.example.ering.trekinsync.models.EmergencyNumberDataListenerModel;
import com.example.ering.trekinsync.models.EmergencyContact;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.PolicyInfo;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.utils.ProfileFlow;
import com.example.ering.trekinsync.utils.SharedPrefsUtils;
import com.example.ering.trekinsync.utils.UserSingletonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditProfilePresenter {

    private EditProfileView view;
    private Context context;
    private SharedPreferences sharedPref;
    private User user;
    private ProfileFlow profileFlow;
    private boolean userDataModified;

    /**
     * Create Profile Presenter for Business logic
     */
    public EditProfilePresenter(Context context, EditProfileView view, User user) {
        this.context = context;
        this.view = view;
        this.user = user;
        this.sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);

        if (user == null) {
            startCreateProfileFlow();
        } else {
            profileFlow = ProfileFlow.EDIT;
        }
    }

    public String getActionBarTitle() {
        if (profileFlow == ProfileFlow.EDIT) {
            return context.getString(R.string.edit_profile_action_bar_title);
        } else {
            return context.getString(R.string.create_profile_action_bar_title);
        }
    }

    public String getFullName() {
        return user.getName() != null ? user.getName() : "";
    }

    public @ColorInt int getHeaderColor() {
        return ContextCompat.getColor(context, R.color.colorPrimary);
    }

    public void handleSaveProfileButtonClick() {
        //update primary user shared preferences
        saveProfileToSharedPrefs();

        if (userDataModified) {
            Toast.makeText(context, "Profile Successfully Updated", Toast.LENGTH_LONG).show();
        }

        if(profileFlow == ProfileFlow.EDIT) {
            //Send updated user object back in intent
            view.launchProfileView(user);
        } else {
            view.launchLandingView();
        }
    }

    /**
     * Launch back button confirmation dialog to confirm user wants to not save changes
     */
    public void handleBackButtonClick() {
        if (userDataModified && profileFlow != ProfileFlow.CREATE) {
            view.launchConfirmationAlert();
        } else {
            view.launchBackButtonAction();
        }
    }

    /* Section Title Data*/

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
     * Gte formatted user birthday.
     * @return String birthday
     */
    public String getFormattedUserBirthday() {
        return user.getFormattedBirthDate();
    }

    /**
     * Get formatted/translated country.
     * @return String country
     */
    public String getUserCitizenship() {
        return UserSingletonUtils.getInstance().getFormattedCountry(user.getCitizenship());
    }

    /**
     * Get formatted/translated country.
     * @return String blood type
     */
    public String getUserBloodType() {
        return UserSingletonUtils.getInstance().getFormattedBloodType(user.getBloodType());
    }

    //Emergency Contact section
    public EmergencyContact[] getEmergencyContacts() {
        if (user.getEmergencyContacts() != null) {
            return user.getEmergencyContacts();
        }
        return new EmergencyContact[0];
    }

    public int getMaxEmergencyContacts() {
        return 3;
    }

    /* On Click Listeners */

    public View.OnClickListener createBirthdayDropDownListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.launchDatePicker(user.getBirthdayDate(), createBirthDaySelectionListener());
            }
        };
    }

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
                        UserSingletonUtils.getInstance().getCountryPosition(getUserCitizenship()),
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
                        UserSingletonUtils.getInstance().getBloodTypePosition(getUserBloodType()),
                        createBloodTypeSelectionListener());
            }
        };
    }

    /**
     * Create data input listener for emergency contact rows.
     * @return DataInputListener of type EmergencyNumberDataListenerModel
     */
    public DataInputListener<EmergencyNumberDataListenerModel> getEmergencyContactListener() {
        return new DataInputListener<EmergencyNumberDataListenerModel>() {
            @Override
            public void onInputReceived(EmergencyNumberDataListenerModel value) {
                //update user model emergency contact details
                EmergencyContact[] userEmergContacts = user.getEmergencyContacts();

                if (value.isShouldDelete()) { //emergency contact deleted
                    //convert to ArrayList and remove requested contact
                    List<EmergencyContact> list = new ArrayList<>(Arrays.asList(userEmergContacts));
                    try {
                        list.remove(value.getPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    //create new emergency contact list for user
                    EmergencyContact[] updatedList = new EmergencyContact[(userEmergContacts.length - 1)];
                    user.setEmergencyContacts(list.toArray(updatedList));
                    view.reloadData();
                } else { //emergency contact details updated
                    if (value.getPosition() < userEmergContacts.length) {
                        userEmergContacts[value.getPosition()].setName(value.getContact().getName());
                        userEmergContacts[value.getPosition()].setPhoneNumberType(value.getContact().getPhoneNumberType());
                        userEmergContacts[value.getPosition()].setPhoneNumber(value.getContact().getPhoneNumber());
                    }
                }
                indicateUserDataModified();
            }
        };
    }

    /**
     * detect add emergency contact button click.
     * @return on click listener
     */
    public View.OnClickListener getAddEmergencyContactListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmergencyContact defaultNewContact = new EmergencyContact("BR", "111-111-1111", "M");
                EmergencyContact[] ec = user.getEmergencyContacts();
                List<EmergencyContact> list = new ArrayList<>(Arrays.asList(ec));
                try {
                    list.add(defaultNewContact);
                } catch(Exception e){
                    e.printStackTrace();
                    return;
                }
                user.setEmergencyContacts(list.toArray(ec));
                view.reloadData();
                indicateUserDataModified();
            }
        };
    }

    /* Dialog alert views */

    private DatePickerDialog.OnDateSetListener createBirthDaySelectionListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePickerView, int year, int month, int dayOfMonth) {
                //Set new Birthday
                Calendar birthdayCal = Calendar.getInstance();
                birthdayCal.set(year, month, dayOfMonth);
                user.setBirthDate(birthdayCal.getTime());

                //calculate & set age from birthday
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - birthdayCal.get(Calendar.YEAR);
                if (today.get(Calendar.DAY_OF_YEAR) < birthdayCal.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                user.setAge(age);

                //reload data
                indicateUserDataModified();
                view.reloadData();
            }
        };
    }

    /**
     * Create Alert spinner for country with citizenship selection.
     * @return DialogInterface.OnClickListener
     */
    private DialogInterface.OnClickListener createCountrySelectionListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setCitizenship(UserSingletonUtils.getInstance().getCountryCode(which));
                indicateUserDataModified();
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
                user.setBloodType(UserSingletonUtils.getInstance().getBloodTypeCode(which));
                indicateUserDataModified();
                view.reloadData();
                dialog.dismiss();
            }
        };
    }

    private void startCreateProfileFlow() {
        this.profileFlow = ProfileFlow.CREATE;
        this.user = createSharedPrefTestData();
        createTravelContactTestData();
    }

    private void indicateUserDataModified() {
        this.userDataModified = true;
    }

    private void saveProfileToSharedPrefs() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SharedPrefsUtils.getKey(context, R.string.created_profile_key), "true");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.primary_profile_key), json);
        editor.apply();
    }

    /* Test Data creation to be removed */

    //TODO remove once create profile flow completed
    private User createSharedPrefTestData() {
        return getTestUserObject("Erin Gallagher", "FIN", true);
    }

    //TODO remove once add travel contact flow complete
    private void createTravelContactTestData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        User testUser1 = getTestUserObject("Christina Chan", "CAD", false);
        User testUser2 = getTestUserObject("Laura Brooks", "KOR", false);
        User testUser3 = getTestUserObject("Lexi Flynn", "ITA", false);

        List<String> contactKeys = new ArrayList<>();
        contactKeys.add(SharedPrefsUtils.getTravelContactKey(testUser1));
        contactKeys.add(SharedPrefsUtils.getTravelContactKey(testUser2));
        contactKeys.add(SharedPrefsUtils.getTravelContactKey(testUser3));
        String json = gson.toJson(contactKeys);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), json);

        String contactJson = gson.toJson(testUser1);
        editor.putString(SharedPrefsUtils.getKey(context, testUser1), contactJson);

        String contactJson2 = gson.toJson(testUser2);
        editor.putString(SharedPrefsUtils.getKey(context, testUser2), contactJson2);

        String contactJson3 = gson.toJson(testUser3);
        editor.putString(SharedPrefsUtils.getKey(context, testUser3), contactJson3);
        editor.apply();
    }

    //TODO remove once create profile flow completed
    private User getTestUserObject(String name, String citizenship, boolean isPersonalProfile) {
        PolicyInfo policyInfo = new PolicyInfo("policy #", "12345");
        PolicyInfo policyInfo2 = new PolicyInfo("cert #", "098");
        PolicyInfo[] policyInfoArray = new PolicyInfo[2];
        policyInfoArray[0] = policyInfo;
        policyInfoArray[1] = policyInfo2;

        InsuranceCompany insuranceCompany = new InsuranceCompany("Manulife", "416-098-4663", true, policyInfoArray);
        InsuranceCompany[] insuranceCompanyArray = new InsuranceCompany[1];
        insuranceCompanyArray[0] = insuranceCompany;

        EmergencyContact emergencyContact = new EmergencyContact("PA", "416-747-3625", "M");
        EmergencyContact emergencyContact2 = new EmergencyContact("SI", "416-888-9865", "W");
        EmergencyContact[] emergencyContactArray = new EmergencyContact[2];
        emergencyContactArray[0] = emergencyContact;
        emergencyContactArray[1] = emergencyContact2;

        User user = new User(isPersonalProfile,
                "2018-03-18",
                name,
                "1994-03-24",
                23,
                citizenship,
                "O-",
                "scented shit",
                "none",
                emergencyContactArray,
                insuranceCompanyArray);
        return user;
    }
}
