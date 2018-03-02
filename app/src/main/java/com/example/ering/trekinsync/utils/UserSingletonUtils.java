package com.example.ering.trekinsync.utils;

import android.content.Context;

import com.example.ering.trekinsync.R;

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
    }



    /**
     * Given a country key, find the associated translated value.
     * @param selectedCountryKey, selected country key
     * @return selected translated country value
     */
    public static String getFormattedCountry(String selectedCountryKey) {
        int position = countryKeyList.indexOf(selectedCountryKey);
        return countryValuesList.get(position != -1 ? position : 0);
    }

    /**
     * Given a country value, find the position in the list.
     * @param selectedCountryValue, selected country value
     * @return position of given country value
     */
    public static int getCountryPosition(String selectedCountryValue) {
        int countryPos = countryValuesList.indexOf(selectedCountryValue);
        return countryPos != -1 ? countryPos : 0;
    }

    /**
     * Given a country value position, retrieve the associate country key.
     * @param countryValuePosition, selected country value position
     * @return the country key associated with the country value
     */
    public static String getCountryCode(int countryValuePosition) {
        if (countryValuePosition < countryKeyList.size()) {
            return countryKeyList.get(countryValuePosition);
        }
        //if country code is not found, return a default value
        return countryKeyList.get(0);
    }

    /**
     * Given blood type key, retrieve translated blood type value.
     * @param selectedBloodTypeKey, selected blood type key
     * @return formatted blood type value
     */
    public static String getFormattedBloodType(String selectedBloodTypeKey) {
        int position = bloodTypeKeyList.indexOf(selectedBloodTypeKey);
        return bloodTypeValuesList.get(position != -1 ? position : 0);
    }

    /**
     * Given selected blood type key, find its position int he values list.
     * @param selectedBloodTypeValue, selected blood type key
     * @return position of country value
     */
    public static int getBloodTypePosition(String selectedBloodTypeValue) {
        int bloodTypePos = bloodTypeValuesList.indexOf(selectedBloodTypeValue);
        return bloodTypePos != -1 ? bloodTypePos : 0;
    }

    /**
     * Given selected Blood type position, find the associated key
     * @param bloodTypeValuePosition, selected blood type value position
     * @return blood type key associated with the blood type value
     */
    public static String getBloodTypeCode(int bloodTypeValuePosition) {
        if (bloodTypeValuePosition < bloodTypeKeyList.size()) {
            return bloodTypeKeyList.get(bloodTypeValuePosition);
        }
        //if country code is not found, return a default value
        return bloodTypeKeyList.get(0);
    }

}
