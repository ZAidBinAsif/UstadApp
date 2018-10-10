package com.andrinotech.ustadapp.ui.login;


import com.andrinotech.ustadapp.ui.base.BaseCallBack;

/**
 * Created by ZaidAs on 2/28/2018.
 */

public interface LoginCallback extends BaseCallBack {
    public void openMainActivity();


    public void showLoginError(String msg, String error);

    public void inValidEmail(String msg);
    public void passwordLength(String msg);

    public void inValidPassword(String msg);

}
