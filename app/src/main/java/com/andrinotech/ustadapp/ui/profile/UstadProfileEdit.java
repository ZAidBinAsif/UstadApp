package com.andrinotech.ustadapp.ui.profile;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.ui.login.Ustad;
import com.andrinotech.ustadapp.ui.register.RegisterViewModel;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.GlideHelper;
import com.andrinotech.ustadapp.utils.multiselect.MultiSelectDialog;
import com.andrinotech.ustadapp.utils.multiselect.MultiSelectModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UstadProfileEdit extends BaseActivity<UstadProfileEditViewModel> implements ProfileEditCallBack, View.OnClickListener {
    private EditText et_fullname, et_username, info, skils, price, et_phone, email, birthday, address;
    TextView changephoto, category;
    ImageView imageView_logo, cancel, confirm;
    private AVProgressDialog mLoadingDialog;
    private Bitmap bitmap;
    private Uri inputImageUri1;
    private File imageFile;
    ArrayList<MultiSelectModel> strings = new ArrayList<>();
    String categorytext = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setCallBack(this);
        initViews();
        strings.add(new MultiSelectModel(1, "Ngaji"));
        strings.add(new MultiSelectModel(2, "Ibadah"));
        strings.add(new MultiSelectModel(3, "Hadist"));
        strings.add(new MultiSelectModel(4, "Halal"));
        strings.add(new MultiSelectModel(5, "keluarga"));
        strings.add(new MultiSelectModel(6, "Muamalah"));

    }

    @Override
    public int getLayout() {
        return R.layout.ustadedit_profile_screen;

    }


    @Override
    public UstadProfileEditViewModel initViewModel() {
        return new UstadProfileEditViewModel();
    }

    public void initViews() {

        et_fullname = findViewById(R.id.et_fullname);
        et_username = findViewById(R.id.et_username);
        et_phone = findViewById(R.id.et_phone);
        email = findViewById(R.id.email);
        category = findViewById(R.id.category);
        info = findViewById(R.id.personal);
        skils = findViewById(R.id.skills);
        price = findViewById(R.id.price);
        imageView_logo = findViewById(R.id.imageView_logo);
        changephoto = findViewById(R.id.changephoto);
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);

        imageView_logo.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        changephoto.setOnClickListener(this);
        setData();
        mLoadingDialog = new AVProgressDialog(this);

    }

    private void setData() {
        Ustad user = UserManager.getInstance().getMetaData().getUser();
        et_fullname.setText(user.getName());
        et_username.setText(user.getUsername());
        et_phone.setText(user.getPhone());
        email.setText(user.getEmail());
        category.setOnClickListener(this);
        category.setText(user.getCategory() == null ? "" : user.getCategory());
        price.setText(user.getPrice() == null ? "" : user.getPrice());
        info.setText(user.getInfo() == null ? "" : user.getInfo());
        skils.setText(user.getSkils() == null ? "" : user.getSkils());
        String path = user.getLogo() == null ? "" : user.getLogo();
        GlideHelper.loadImage(this, path, imageView_logo, R.drawable.ic_profile_plc);

    }

    @Override
    public void getImageFromIntent(Uri uri, File p) {
        inputImageUri1 = uri;
        imageFile = p;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), inputImageUri1);
            Glide.with(this)
                    .load(uri).dontAnimate()
                    .fitCenter()
                    .error(R.drawable.ic_profile_plc)
                    .placeholder(R.drawable.ic_profile_plc)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView_logo);
//            imageView_logo.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                register();
                break;
            case R.id.changephoto:
                getViewModel().askForDangerousPermissions(this);
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.category:
                showDialog();
                break;

            default:
                break;
        }
    }


    public void showDialog() {
        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Category") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .multiSelectList(strings) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        for (int i = 0; i < selectedIds.size(); i++) {
                            categorytext = categorytext + selectedNames.get(i);

                            if (i != selectedIds.size() - 1) {
                                categorytext = categorytext + ",";

                            }
                        }
                        category.setText(categorytext);


                    }

                    @Override
                    public void onCancel() {

                    }


                });

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

    }

    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }


    private void register() {
        mLoadingDialog.show();
        String name = et_fullname.getText().toString();
        String username = et_username.getText().toString();
        String et_phonetext = et_phone.getText().toString();
        String emailtext = email.getText().toString();
        String categorytext = category.getText().toString();
        String pricetext = price.getText().toString();
        String skiltext = skils.getText().toString();
        String infotext = info.getText().toString();

        getViewModel().editUser(imageFile, name, username, et_phonetext, emailtext, categorytext, pricetext, skiltext, infotext);

    }

    @Override
    public void ValidationError(RegisterViewModel.validationEnum validationEnum) {
        if (validationEnum.equals(RegisterViewModel.validationEnum.Name)) {
            CommonUtils.showToast(" Name Required.");
        }
        if (validationEnum.equals(RegisterViewModel.validationEnum.UserNAme)) {
            CommonUtils.showToast("UserName Required.");
        }
        if (validationEnum.equals(RegisterViewModel.validationEnum.EMAIL)) {
            CommonUtils.showToast("Email Required.");
        }
        if (validationEnum.equals(RegisterViewModel.validationEnum.WRONG_EMAIL)) {
            CommonUtils.showToast("Invalid email format.");
        }

        dismissDialog();
    }

    @Override
    public void SuccessFullyUpdated() {
        dismissDialog();
        CommonUtils.showToast("Profile Updated Successfully");

    }

    @Override
    public void ErrorOnEdit(String str) {
        dismissDialog();
        CommonUtils.showToast(str);

    }


    @Override
    public void InternetError(String str) {
        dismissDialog();
        CommonUtils.showToast(str);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getViewModel().onRequestPermissionsResult(UstadProfileEdit.this, requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(this, requestCode, resultCode, data);
    }

}
