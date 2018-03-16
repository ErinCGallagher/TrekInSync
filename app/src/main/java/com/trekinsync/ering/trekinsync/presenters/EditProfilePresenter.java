package com.trekinsync.ering.trekinsync.presenters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.adapters.IconSelectionAdapter;
import com.trekinsync.ering.trekinsync.interfaces.DataInputListener;
import com.trekinsync.ering.trekinsync.interfaces.EditProfileView;
import com.trekinsync.ering.trekinsync.models.EmergencyContactListenerModel;
import com.trekinsync.ering.trekinsync.models.EmergencyContact;
import com.trekinsync.ering.trekinsync.models.IconModel;
import com.trekinsync.ering.trekinsync.models.InsuranceCompany;
import com.trekinsync.ering.trekinsync.models.InsuranceListenerModel;
import com.trekinsync.ering.trekinsync.models.PolicyInfo;
import com.trekinsync.ering.trekinsync.models.User;
import com.trekinsync.ering.trekinsync.utils.ProfileFlow;
import com.trekinsync.ering.trekinsync.utils.SharedPrefsUtils;
import com.trekinsync.ering.trekinsync.utils.UserSingletonUtils;
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
        this.sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);

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

    public LayerDrawable getProfileIcon() {
        Resources r = context.getResources();
        Drawable[] layers = new Drawable[2];
        layers[0] = r.getDrawable(UserSingletonUtils.getInstance().getSelectedDrawable(user.getIcon()));
        layers[1] = r.getDrawable(R.drawable.ic_edit_black_opacity_40);
        return new LayerDrawable(layers);
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

    public String getBirthDateLabel() {
        return context.getString(R.string.birth_date_label);
    }

    public String getCitizenshipLabel() {
        return context.getString(R.string.citizenship_label);
    }

    public String getBloodTypeLabel() {
        return context.getString(R.string.blood_type_label);
    }

    public String getAllergiesLabel() {
        return context.getString(R.string.allergies_label);
    }

    public String getMedicationLabel() {
        return context.getString(R.string.medication_label);
    }

    public String getAddNumberButtonLabel() {
        return context.getString(R.string.add_number_button_title);
    }

    public String getAddInsuranceCompanyButtonLabel() {
        return context.getString(R.string.add_insurance_button_title);
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

    /**
     * Get user allergies.
     * @return allergies as String
     */
    public String getAllergies() {
        return user.getAllergies();
    }

    /**
     * Get user medication.
     * @return medication as String
     */
    public String getMedications() {
        return user.getMedicine();
    }

    /**
     * Get list of emergency contacts
     * @return Emergency Contact Array
     */
    public EmergencyContact[] getEmergencyContacts() {
        if (user.getEmergencyContacts() != null) {
            return user.getEmergencyContacts();
        }
        return new EmergencyContact[0];
    }

    /**
     * Get list of insurance companies.
     * @return Insurance Company Array
     */
    public InsuranceCompany[] getInsuranceCompanies() {
        if (user.getInsuranceInfo() != null) {
            return user.getInsuranceInfo();
        }
        return new InsuranceCompany[0];
    }

    public int getMaxEmergencyContacts() {
        return 3;
    }

    public int getMaxInsuranceCompanies() {
        return 2;
    }

    /* On Click Listeners */

    /**
     * Create On Click Listener for profile icon selection that opens a dialog spinner.
     * @return View.OnClickListener
     */
    public View.OnClickListener createProfileIconClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<IconModel> iconList = UserSingletonUtils.getInstance().getProfileIconList();
                IconSelectionAdapter adapter = new IconSelectionAdapter(context, iconList, user.getIcon());
                view.launchPopUp("Select Profile Icon", adapter, createIconSelectionListener());
            }
        };
    }

    /**
     * Create Data Input Listener for  full name edit text.
     * @return DataInputListener<String> listener
     */
    public DataInputListener<String> getFullNameEditListener() {
        return new DataInputListener<String>() {
            @Override
            public void onInputReceived(String value) {
                user.setName(value.trim());
                indicateUserDataModified();
            }
        };
    }

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
     * Create listener for Allergies text field. Update user data on input.
     * @return DataInputListener<String> listener
     */
    public DataInputListener<String> createAllergiesDataListener() {
        return new DataInputListener<String>() {
            @Override
            public void onInputReceived(String value) {
                user.setAllergies(value);
                indicateUserDataModified();
            }
        };
    }

    /**
     * Create listener for Medications text field. Update user data on input.
     * @return DataInputListener<String> listener
     */
    public DataInputListener<String> createMedicationDataListener() {
        return new DataInputListener<String>() {
            @Override
            public void onInputReceived(String value) {
                user.setMedicine(value);
                indicateUserDataModified();
            }
        };
    }

    /**
     * Create data input listener for emergency contact rows.
     * @return DataInputListener of type EmergencyContactListenerModel
     */
    public DataInputListener<EmergencyContactListenerModel> getEmergencyContactListener() {
        return new DataInputListener<EmergencyContactListenerModel>() {
            @Override
            public void onInputReceived(EmergencyContactListenerModel value) {
                //update user model emergency contact details
                EmergencyContact[] userEmergContacts = getEmergencyContacts();

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

    public DataInputListener<InsuranceListenerModel> getInsuranceCompanyListener() {
        return new DataInputListener<InsuranceListenerModel>() {
            @Override
            public void onInputReceived(InsuranceListenerModel value) {
                //update user model emergency contact details
                InsuranceCompany[] userInsuranceCompanies = getInsuranceCompanies();

                if (value.isShouldDelete()) { //emergency contact deleted
                    //convert to ArrayList and remove requested contact
                    List<InsuranceCompany> list = new ArrayList<>(Arrays.asList(userInsuranceCompanies));
                    try {
                        list.remove(value.getPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    //create new emergency contact list for user
                    InsuranceCompany[] updatedList = new InsuranceCompany[(userInsuranceCompanies.length - 1)];
                    user.setInsuranceInfo(list.toArray(updatedList));
                    view.reloadData();
                } else {
                    if (value.getPosition() < userInsuranceCompanies.length) {
                        userInsuranceCompanies[value.getPosition()] = value.getCompany();
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

    /**
     * detect add emergency contact button click.
     * @return on click listener
     */
    public View.OnClickListener getAddInsuranceCompanyListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PolicyInfo[] policyInfoList = new PolicyInfo[2];
                policyInfoList[0] = new PolicyInfo("", "");
                policyInfoList[1] = new PolicyInfo("", "");
                InsuranceCompany defaultCompany = new InsuranceCompany("","", policyInfoList);
                InsuranceCompany[] ic = getInsuranceCompanies();
                List<InsuranceCompany> list = new ArrayList<>(Arrays.asList(ic));
                try {
                    list.add(defaultCompany);
                } catch(Exception e){
                    e.printStackTrace();
                    return;
                }
                user.setInsuranceInfo(list.toArray(ic));
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

    private DialogInterface.OnClickListener createIconSelectionListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: update user profile icon
                indicateUserDataModified();
                view.reloadData();
                dialog.dismiss();
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
        this.user = createEmptyInitProfile();
        setupContactsSharedPrefs();
    }

    private void indicateUserDataModified() {
        this.userDataModified = true;
    }

    private void saveProfileToSharedPrefs() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SharedPrefsUtils.getKey(context, R.string.created_profile_key), "true");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.primary_profile_key), json);
        editor.apply();
    }

    /* Test Data creation to be removed */
    private User createEmptyInitProfile() {
        String policyName = UserSingletonUtils.getInstance().getDefaultPolicy();
        PolicyInfo policyInfo = new PolicyInfo(policyName, "");
        PolicyInfo policyInfo2 = new PolicyInfo(policyName, "");
        PolicyInfo[] policyInfoArray = new PolicyInfo[2];
        policyInfoArray[0] = policyInfo;
        policyInfoArray[1] = policyInfo2;

        InsuranceCompany insuranceCompany = new InsuranceCompany("", "", policyInfoArray);
        InsuranceCompany[] insuranceCompanyArray = new InsuranceCompany[1];
        insuranceCompanyArray[0] = insuranceCompany;

        EmergencyContact emergencyContact = new EmergencyContact("PA", "416-747-3625", "M");
        EmergencyContact[] emergencyContactArray = new EmergencyContact[1];
        emergencyContactArray[0] = emergencyContact;

        String defaultBloodType = UserSingletonUtils.getInstance().getDefaultBloodType();
        String defaultName = context.getString(R.string.full_name_hint);
        String defaultCountryCode = UserSingletonUtils.getInstance().getDefaultCountryCode();

        User user = new User(true,
                "2018-03-18",
                defaultName,
                "1994-03-24",
                23,
                defaultCountryCode,
                defaultBloodType,
                "",
                "",
                0,
                emergencyContactArray,
                insuranceCompanyArray);
        return user;
    }

    private void setupContactsSharedPrefs() {
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        List<String> contactKeys = new ArrayList<>();
        String json = gson.toJson(contactKeys);
        editor.putString(SharedPrefsUtils.getKey(context, R.string.travel_contact_key_names), json);
        editor.apply();
    }
}
