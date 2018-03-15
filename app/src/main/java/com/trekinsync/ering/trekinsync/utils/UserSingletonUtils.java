package com.trekinsync.ering.trekinsync.utils;

import android.content.Context;

import com.trekinsync.ering.trekinsync.R;

import java.util.Arrays;
import java.util.List;

public class UserSingletonUtils {

    private static UserSingletonUtils instance;
    private final Context context;

    //key value string lists
    private static List<String> countryKeyList;
    private static List<String> countryValuesList;
    private static List<String> bloodTypeKeyList;
    private static List<String> bloodTypeValuesList;
    private static List<String> phoneRelationKeyList;
    private static List<String> phoneRelationValuesList;
    private static List<String> phoneTypeKeysList;
    private static List<String> phoneTypeValuesList;
    private static List<String> insuranceKeyList;
    private static List<String> insuranceValuesList;

    public static void init(Context context) {
        if (instance == null) {
            //never use activity context which will be lost when activity is destroyed
            context = context.getApplicationContext();
            instance = new UserSingletonUtils(context);
        }
    }

    public static UserSingletonUtils getInstance() {
        return instance;
    }

    private UserSingletonUtils(Context context) {
        this.context = context;
        initStringLists();
    }

    private void initStringLists() {
        countryKeyList = Arrays.asList(context.getResources().getStringArray(R.array.citizenship_keys));
        countryValuesList = Arrays.asList(context.getResources().getStringArray(R.array.citizenship_values));
        bloodTypeKeyList = Arrays.asList(context.getResources().getStringArray(R.array.blood_type_keys));
        bloodTypeValuesList = Arrays.asList(context.getResources().getStringArray(R.array.blood_type_values));
        phoneRelationKeyList = Arrays.asList(context.getResources().getStringArray(R.array.contact_relation_keys));
        phoneRelationValuesList = Arrays.asList(context.getResources().getStringArray(R.array.contact_relation_values));
        phoneTypeKeysList = Arrays.asList(context.getResources().getStringArray(R.array.number_type_keys));
        phoneTypeValuesList = Arrays.asList(context.getResources().getStringArray(R.array.number_type_values));
        insuranceKeyList = Arrays.asList(context.getResources().getStringArray(R.array.insurance_keys));
        insuranceValuesList = Arrays.asList(context.getResources().getStringArray(R.array.insurance_values));
    }

    /**
     * Given a country key, find the associated translated value.
     * @param selectedCountryKey, selected country key
     * @return selected translated country value
     */
    public String getFormattedCountry(String selectedCountryKey) {
        int position = countryKeyList.indexOf(selectedCountryKey);
        return countryValuesList.get(position != -1 ? position : 0);
    }

    /**
     * Given a country value, find the position in the list.
     * @param selectedCountryValue, selected country value
     * @return position of given country value
     */
    public int getCountryPosition(String selectedCountryValue) {
        int countryPos = countryValuesList.indexOf(selectedCountryValue);
        return countryPos != -1 ? countryPos : 0;
    }

    /**
     * Given a country value position, retrieve the associate country key.
     * @param countryValuePosition, selected country value position
     * @return the country key associated with the country value
     */
    public String getCountryCode(int countryValuePosition) {
        if (countryValuePosition < countryKeyList.size()) {
            return countryKeyList.get(countryValuePosition);
        }
        //if country code is not found, return a default value
        return countryKeyList.get(0);
    }

    public String getDefaultCountryCode() {
        return countryKeyList.get(0);
    }

    /**
     * Given blood type key, retrieve translated blood type value.
     * @param selectedBloodTypeKey, selected blood type key
     * @return formatted blood type value
     */
    public String getFormattedBloodType(String selectedBloodTypeKey) {
        int position = bloodTypeKeyList.indexOf(selectedBloodTypeKey);
        return bloodTypeValuesList.get(position != -1 ? position : 0);
    }

    /**
     * Given selected blood type key, find its position int he values list.
     * @param selectedBloodTypeValue, selected blood type key
     * @return position of country value
     */
    public int getBloodTypePosition(String selectedBloodTypeValue) {
        int bloodTypePos = bloodTypeValuesList.indexOf(selectedBloodTypeValue);
        return bloodTypePos != -1 ? bloodTypePos : 0;
    }

    /**
     * Given selected Blood type position, find the associated key
     * @param bloodTypeValuePosition, selected blood type value position
     * @return blood type key associated with the blood type value
     */
    public String getBloodTypeCode(int bloodTypeValuePosition) {
        if (bloodTypeValuePosition < bloodTypeKeyList.size()) {
            return bloodTypeKeyList.get(bloodTypeValuePosition);
        }
        //if country code is not found, return a default value
        return bloodTypeKeyList.get(0);
    }

    public String getFormattedPhoneRelation(String selectedPhoneRelationKey) {
        int position = phoneRelationKeyList.indexOf(selectedPhoneRelationKey);
        return phoneRelationValuesList.get(position != -1 ? position : 0);
    }

    public String getDefaultBloodType() {
        return bloodTypeKeyList.get(0);
    }

    /**
     * Given selected phone number relation value, find its position in the values list.
     * @param selectedRelationValue, selected relation value
     * @return position of country value
     */
    public int getPhoneRelationPosition(String selectedRelationValue) {
        int relationPos = phoneRelationValuesList.indexOf(selectedRelationValue);
        return relationPos != -1 ? relationPos : 0;
    }

    public int getPhoneRelationKeyPosition(String selectedRelationKey) {
        int relationPos = phoneRelationKeyList.indexOf(selectedRelationKey);
        return relationPos != -1 ? relationPos : 0;
    }

    public String getFormattedPhoneType(String selectedPhoneTypeKey) {
        int position = phoneTypeKeysList.indexOf(selectedPhoneTypeKey);
        return phoneTypeValuesList.get(position != -1 ? position : 0);
    }

    public String getPhoneRelationCode(int PhoneRelationValuePosition) {
        if (PhoneRelationValuePosition < phoneRelationKeyList.size()) {
            return phoneRelationKeyList.get(PhoneRelationValuePosition);
        }
        //if country code is not found, return a default value
        return phoneRelationKeyList.get(0);
    }

    /**
     * Given selected phone number type value, find its position in the values list.
     * @param selectedPhoneTypeValue, selected relation value
     * @return position of country value
     */
    public int getPhoneTypePosition(String selectedPhoneTypeValue) {
        int typePos = phoneTypeValuesList.indexOf(selectedPhoneTypeValue);
        return typePos != -1 ? typePos : 0;
    }

    public int getPhoneTypeKeyPosition(String selectedPhoneTypeKey) {
        int typePos = phoneTypeKeysList.indexOf(selectedPhoneTypeKey);
        return typePos != -1 ? typePos : 0;
    }

    public String getPhoneTypeCode(int PhoneTypeValuePosition) {
        if (PhoneTypeValuePosition < phoneTypeKeysList.size()) {
            return phoneTypeKeysList.get(PhoneTypeValuePosition);
        }
        //if country code is not found, return a default value
        return phoneTypeKeysList.get(0);
    }


    /* Insurance Company Policy Type */

    public String getInsurancePolicyCode(int InsurancePolicyValuePosition) {
        if (InsurancePolicyValuePosition < insuranceKeyList.size()) {
            return insuranceKeyList.get(InsurancePolicyValuePosition);
        }
        //if country code is not found, return a default value
        return insuranceKeyList.get(0);
    }

    public int getInsurancePolicyKeyPosition(String selectedInsuranceKey) {
        int insurancePos = insuranceKeyList.indexOf(selectedInsuranceKey);
        return insurancePos != -1 ? insurancePos : 0;
    }

    public String getFormattedInsurancePolicy(String selectedInsurancePolicyKey) {
        int position = insuranceKeyList.indexOf(selectedInsurancePolicyKey);
        return insuranceValuesList.get(position != -1 ? position : 0);
    }

    public String getDefaultPolicy() {
        return insuranceKeyList.get(0);
    }
}
