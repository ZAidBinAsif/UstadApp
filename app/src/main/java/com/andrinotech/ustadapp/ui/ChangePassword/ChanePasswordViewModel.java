package com.andrinotech.ustadapp.ui.ChangePassword;


import android.util.Patterns;

import com.andrinotech.ustadapp.UstadApp;
import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.ui.base.BaseViewModel;
import com.andrinotech.ustadapp.ui.login.MetaData;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.NetworkUtils;
import com.andrinotech.ustadapp.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChanePasswordViewModel extends BaseViewModel<ChangePasswordCallback> {


    public boolean isEmailAndPasswordValid(String pasword, String confirmpassword, String email) {
        boolean isValid = true;
        if (StringUtils.isNullOrEmpty(pasword)) {
            getmCallback().inValidPassword("Password Required.");
            isValid = false;
        }
        if (!StringUtils.passwordLength(pasword)) {
            getmCallback().passwordLength("length must be between 3 to 15 character.");
            isValid = false;

        }
        if (StringUtils.isNullOrEmpty(confirmpassword)) {
            getmCallback().inValidPassword("confirm password Password Required.");
            isValid = false;
        }
        if (!StringUtils.passwordMatches(pasword, confirmpassword)) {
            getmCallback().inValidPassword("Password and Confirm Password does not match.");
            isValid = false;
        }
        if (StringUtils.isNullOrEmpty(email)) {
            getmCallback().inValidEmail("Email Required");
            isValid = false;
        }
        if (!StringUtils.isNullOrEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getmCallback().inValidEmail("invalid Email");
            isValid = false;
        }
        return isValid;
    }

    public void changecode(String password, final String confirmpassword, String s, String code) {
        if (StringUtils.isNullOrEmpty(code)) {
            getmCallback().inValidCode("Code Required.");
            return;
        }

        if (isEmailAndPasswordValid(password, confirmpassword, s)) {
            final ChangePasswordApiModel changePasswordApiModel = new ChangePasswordApiModel(password, s);
            changePasswordApiModel.setCode(code);
            getmCompositeDisposable().add(getmRequestHandler().changepassword(changePasswordApiModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Consumer<MetaData>() {
                        @Override
                        public void accept(MetaData metaData) throws Exception {
                            if (metaData == null) {
                                getmCallback().inValidEmail("Not Available");
                                return;
                            } else if (metaData.getError().getCode() == 302) {
                                getmCallback().inValidEmail(metaData.getError().getMessage());
                                return;
                            }
                            UserManager.getInstance().setMetaData(metaData);
                            getmCallback().openMainActivity();
                            CommonUtils.showToast("Password has been changed Successfully, please login");

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                                getmCallback().showError("Check your internet connection", "");
                            } else {
                                getmCallback().showError("No Internet Connection", "");

                            }

                        }
                    }));
        }
    }

}
