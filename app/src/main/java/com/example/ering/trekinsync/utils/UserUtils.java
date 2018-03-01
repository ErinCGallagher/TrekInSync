package com.example.ering.trekinsync.utils;

import android.content.Context;

import com.example.ering.trekinsync.R;

import java.util.Arrays;
import java.util.List;

public class UserUtils {

    private UserUtils() { /* cannot be instantiated */ }

    /**
     * Given a country key, find the associated translated value.
     * @param countryKeys, list of country keys
     * @param countryValues, list of country values translated
     * @param selectedCountryKey, selected country key
     * @return selected translated country value
     */
    public static String getFormattedCountry(List<String> countryKeys, List<String> countryValues, String selectedCountryKey) {
        int position = countryKeys.indexOf(selectedCountryKey);
        return countryValues.get(position != -1 ? position : 0);
    }

    /**
     * Given a country value, find the position in the list.
     * @param countryValues, list of country values
     * @param selectedCountryValue, selected country value
     * @return position of given country value
     */
    public static int getCountryPosition(List<String> countryValues, String selectedCountryValue) {
        int countryPos = countryValues.indexOf(selectedCountryValue);
        return countryPos != -1 ? countryPos : 0;
    }

    /**
     * Given a country value, retrieve the associate country key.
     * @param countryKeys, list of country keys
     * @param countryValues, list of country values
     * @param selectedCountryValue, selected country value
     * @return the country key associated with the country value
     */
    public static String getCountryCode(List<String> countryKeys, List<String> countryValues, String selectedCountryValue) {
        int position = countryValues.indexOf(selectedCountryValue);
        return countryKeys.get(position != -1 ? position : 0);
    }

    /**
     * Given blood type key, retrieve translated blood type value.
     * @param bloodTypeKeys, list of blood type keys
     * @param bloodTypeValues, list of blood type values translated
     * @param selectedBloodTypeKey, selected blood type key
     * @return formatted blood type value
     */
    public static String getFormattedBloodType(List<String> bloodTypeKeys, List<String> bloodTypeValues, String selectedBloodTypeKey) {
        int position = bloodTypeKeys.indexOf(selectedBloodTypeKey);
        return bloodTypeValues.get(position != -1 ? position : 0);
    }

    /**
     * Given selected blood type key, find its position int he values list.
     * @param bloodTypeValues, list of blood type values
     * @param selectedBloodTypeValue, selected blood type key
     * @return position of country value
     */
    public static int getBloodTypePosition(List<String> bloodTypeValues, String selectedBloodTypeValue) {
        int bloodTypePos = bloodTypeValues.indexOf(selectedBloodTypeValue);
        return bloodTypePos != -1 ? bloodTypePos : 0;
    }

    /**
     * Given selected Blood type value, find the associated key
     * @param bloodTypeKeys, list of keys
     * @param bloodTypeValues, list of translated values
     * @param selectedBloodTypeValue, selected blood type value
     * @return blood type key associated with the blood type vaue
     */
    public static String getBloodTypeCode(List<String> bloodTypeKeys, List<String> bloodTypeValues, String selectedBloodTypeValue) {
        int position = bloodTypeValues.indexOf(selectedBloodTypeValue);
        return bloodTypeKeys.get(position != -1 ? position : 0);
    }

}
