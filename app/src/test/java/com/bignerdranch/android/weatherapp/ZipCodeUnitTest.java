package com.bignerdranch.android.weatherapp;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by berekethaile on 4/25/18.
 */

public class ZipCodeUnitTest {

    @Test
    public void valuedZipCode() throws Exception {
        assertTrue(ValidateZip.isAValidZipCode("95051"));
        assertTrue(ValidateZip.isAValidZipCode("30030"));
    }

    @Test
    public void inValuedZipCode() throws Exception {
        assertFalse(ValidateZip.isAValidZipCode("101"));
        assertFalse(ValidateZip.isAValidZipCode("zipcode"));
        assertFalse(ValidateZip.isAValidZipCode("129299"));
    }
}
