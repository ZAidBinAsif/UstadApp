package com.andrinotech.ustadapp.ui.forgotPassword;


import com.andrinotech.ustadapp.ui.base.BaseCallBack;

public interface ForgotPasswordCallback extends BaseCallBack {
    public void inValidEmail(String msg);
    public void inValidCode(String msg);

    public void openLoginActivity();

    public void UserNotFound(String msg);

    public void internetError(String msg);
}
