package com.spy.healthmatic.Welcome;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by prashantn.pol on 2016-12-15.
 */
public class LoginTest {
    @Test
    public void isInvalidValidUserName() throws Exception {
        assertThat(Login.isInvalidValidUserName("testUsername"), is(false));
    }

    @Test
    public void isInValidPassword() throws Exception {
        assertThat(Login.isInValidPassword("testPassword"), is(false));
    }

}