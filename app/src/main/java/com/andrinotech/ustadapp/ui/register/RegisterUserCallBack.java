package com.andrinotech.ustadapp.ui.register;


import com.andrinotech.ustadapp.ui.base.BaseCallBack;

public interface RegisterUserCallBack extends BaseCallBack {
    public  void ValidationError(RegisterViewModel.validationEnum validationEnum);
    public void openLoginActivity();
    public void EmailAlreadyExist(String str);
    public void InternetError(String str);
}
