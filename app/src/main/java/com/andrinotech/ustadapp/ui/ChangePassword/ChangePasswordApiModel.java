package com.andrinotech.ustadapp.ui.ChangePassword;

/**
 * Created by AliAh on 08/03/2018.
 */

public class ChangePasswordApiModel {
    String password;
    String email;
    String code;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password, String email) {
        this.password = password;
        this.email=email;
    }

    public ChangePasswordApiModel(String password, String email) {
        this.email=email;

        this.password = password;
    }
}
