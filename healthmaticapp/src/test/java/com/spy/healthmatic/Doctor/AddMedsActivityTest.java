package com.spy.healthmatic.Doctor;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by prashantn.pol on 2016-12-15.
 */
public class AddMedsActivityTest {
    @Test
    public void isInvalidString() throws Exception {
        assertThat(AddMedsActivity.isInvalidString("testString"), is(false));
    }

}