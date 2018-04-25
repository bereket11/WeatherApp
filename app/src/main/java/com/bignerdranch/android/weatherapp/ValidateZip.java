package com.bignerdranch.android.weatherapp;

import java.util.regex.Pattern;

/**
 * Created by berekethaile on 4/23/18.
 */

public class ValidateZip {
    /**
     * Regular Expression to match the US Zip-Code
     */
    static final String regex = "^[0-9]{5}(?:-[0-9]{4})?$";


    //This method returns true if the parameter string is a valid zip code
    public static boolean isAValidZipCode(String zip) {
        return Pattern.matches(regex, zip);
    }

}
