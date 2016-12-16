package com.spy.healthmatic.Admin.Fragments;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by prashantn.pol on 2016-12-15.
 */
public class PatientPersonalFragmentTest {
    @Test
    public void isInvalidName() throws Exception {
        assertThat(PatientPersonalFragment.isInvalidName("testName"), is(false));
    }

    @Test
    public void isInvalidContact() throws Exception {
        assertThat(PatientPersonalFragment.isInvalidContact("123123"), is(false));
    }

    @Test
    public void isInvalidDate() throws Exception {
        assertThat(PatientPersonalFragment.isInvalidDate("2016-12-13"), is(false));
    }

}