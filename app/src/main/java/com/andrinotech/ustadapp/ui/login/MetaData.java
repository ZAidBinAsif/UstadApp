
package com.andrinotech.ustadapp.ui.login;

import com.andrinotech.ustadapp.helper.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaData {
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @SerializedName("error")
    @Expose
    private Error error;


    @SerializedName("user")
    @Expose
    private Ustad user;

    public Ustad getUser() {
        return user;
    }

    public void setUser(Ustad user) {
        this.user = user;
    }
}
