package com.spy.healthmatic.Model;

/**
 * Created by yatin on 23/11/16.
 */

public class LoginModel {
    private String usrname;
    private String password;
    private String fingerKey;

    public LoginModel(String usrname, String password, String fingerKey) {
        this.usrname = usrname;
        this.password = password;
        this.fingerKey = fingerKey;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFingerKey() {
        return fingerKey;
    }

    public void setFingerKey(String fingerKey) {
        this.fingerKey = fingerKey;
    }
}
