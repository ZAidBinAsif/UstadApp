package com.andrinotech.ustadapp.ui.profile;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.andrinotech.ustadapp.UstadApp;
import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.helper.DynamicPermission;
import com.andrinotech.ustadapp.helper.FileHelper;
import com.andrinotech.ustadapp.helper.Utilforbitmap;
import com.andrinotech.ustadapp.ui.base.BaseViewModel;
import com.andrinotech.ustadapp.ui.login.MetaData;
import com.andrinotech.ustadapp.ui.login.Ustad;
import com.andrinotech.ustadapp.ui.register.RegisterViewModel;
import com.andrinotech.ustadapp.utils.Config;
import com.andrinotech.ustadapp.utils.NetworkUtils;
import com.andrinotech.ustadapp.utils.StringUtils;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class UstadProfileEditViewModel extends BaseViewModel<ProfileEditCallBack> {
    public enum validationEnum {
        Name, UserNAme, EMAIL
    }

    private int PICK_IMAGE_REQUEST = 2;

    public static File currentImagefile;
    public String mCurrentPhotoPath;
    public int CAMERA_PREVIEW_RESULT = 1;

    public Ustad areFieldsValid(String name, String username, String email) {
        boolean isValid = true;
        Ustad user = new Ustad();
        if (StringUtils.isNullOrEmpty(name)) {
            getmCallback().ValidationError(RegisterViewModel.validationEnum.Name);
            isValid = false;
        }
        if (StringUtils.isNullOrEmpty(username)) {
            getmCallback().ValidationError(RegisterViewModel.validationEnum.UserNAme);
            isValid = false;
        }
        if (StringUtils.isNullOrEmpty(email)) {
            getmCallback().ValidationError(RegisterViewModel.validationEnum.EMAIL);
            isValid = false;
        }
        if (!StringUtils.isNullOrEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getmCallback().ValidationError(RegisterViewModel.validationEnum.WRONG_EMAIL);
            isValid = false;
        }
        if (isValid) {
            user.setUsername(username);
            user.setName(name);
            user.setEmail(email);
        } else {
            user = null;
        }

        return user;
    }

    public void editUser(final File imageFile, final String name, final String username, final String et_phonetext, final String emailtext, final String categorytext, final String pricetext, final String skiltext, final String infotext) {
        if (areFieldsValid(name, username, emailtext) != null) {
            if (imageFile != null) {
                AndroidNetworking.upload(Config.Api.UPLOAD_IMAGE)
                        .addMultipartFile("image", imageFile)
                        .addMultipartParameter("email", emailtext)
                        .setTag("uploadTest")
                        .setPriority(Priority.HIGH)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @Override
                            public void onProgress(long bytesUploaded, long totalBytes) {
                            }
                        })
                        .getAsObject(MetaData.class, new ParsedRequestListener() {
                            @Override
                            public void onResponse(Object response) {
                                MetaData metaData = (MetaData) response;
                                if (metaData == null) {
                                    getmCallback().ErrorOnEdit("Not Available");
                                    return;
                                } else if (metaData.getError().getCode() == 302) {
                                    getmCallback().ErrorOnEdit(metaData.getError().getMessage());
                                    return;
                                } else {
                                    Ustad user = areFieldsValid(name, username, emailtext);
                                    editProfie(user, categorytext, et_phonetext, pricetext, skiltext, infotext);
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                                    getmCallback().ErrorOnEdit("Check your internet connection");
                                } else {
                                    getmCallback().ErrorOnEdit("No Internet Connection");

                                }
                            }
                        });
            } else {
                Ustad user = areFieldsValid(name, username, emailtext);
                editProfie(user, categorytext, et_phonetext, pricetext, skiltext, infotext);

            }

        } else {
            Ustad user = areFieldsValid(name, username, emailtext);
            editProfie(user, categorytext, et_phonetext, pricetext, skiltext, infotext);

        }
    }


    public void editProfie(Ustad user, String categorytext, String et_phonetext, String pricetext, String skiltext, String infotext) {
        user.setPrice(pricetext != null ? pricetext : "");
        user.setPhone(et_phonetext != null ? et_phonetext : "");
        user.setSkils(skiltext != null ? skiltext : "");
        user.setInfo(infotext != null ? infotext : "");
        user.setCategory(categorytext != null ? categorytext : "");
        EditprofileApidModel editprofileApidModel = new EditprofileApidModel();
        editprofileApidModel.setUser(user);
        getmCompositeDisposable().add(getmRequestHandler()
                .Editprfofile(editprofileApidModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MetaData>() {
                    @Override
                    public void accept(MetaData metaData) throws Exception {
                        if (metaData == null) {
                            getmCallback().ErrorOnEdit("Not Available");
                            return;
                        } else if (metaData.getError().getCode() == 302) {
                            getmCallback().ErrorOnEdit(metaData.getError().getMessage());
                            return;
                        }
                        UserManager.getInstance().setMetaData(metaData);
                        getmCallback().SuccessFullyUpdated();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnEdit("Check your internet connection");

                        } else {
                            getmCallback().ErrorOnEdit("No Internet Connection");

                        }

                    }
                }));
    }

    public void askForDangerousPermissions(UstadProfileEdit context) {
        ArrayList<String> permissions = new ArrayList<String>();

        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        DynamicPermission dynamicPermission = new DynamicPermission(context, permissions);
        boolean flag = dynamicPermission.checkAndRequestPermissions();

        if (flag) {
            browseImageFromGallery(context);
        }
    }

    private void browseImageFromGallery(UstadProfileEdit context) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onRequestPermissionsResult(UstadProfileEdit context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                context);
        builder.setTitle("Need Storage Permission");
        builder.setMessage("This app needs storage permission.");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @SuppressLint("WrongConstant")
                    public void run() {
                        Toast.makeText(UstadApp.getInstance().getApplicationContext(), "This app needs storage permission", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        if (requestCode == 11) {
            boolean isgranted = true;

            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    boolean showRationale = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        showRationale = context.shouldShowRequestPermissionRationale(permission);
                    }
                    if (!showRationale) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivityForResult(intent, 1);
                        isgranted = false;
                        break;
                    } else {
                        builder.show();
                    }

                    isgranted = false;
                    break;
                }
            }
            if (!isgranted) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @SuppressLint("WrongConstant")
                    public void run() {
                        Toast.makeText(UstadApp.getInstance().getApplicationContext(), "This app needs storage permission", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                browseImageFromGallery(context);
            }


        }
    }

    public Intent dispatchTakePictureIntent(UstadProfileEdit context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File newFile = null;
                try {
                    newFile = createImageFile();
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Uri photoURI = FileProvider.getUriForFile(context, "com.andrinotech.ustadapp.fileprovider", newFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createImageFile()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (takePictureIntent != null)
            context.startActivityForResult(takePictureIntent, CAMERA_PREVIEW_RESULT);

        return null;
    }


    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!storageDir.exists()) {
            if (!storageDir.mkdir()) {
                Log.e("TAG", "Throwing Errors....");
                throw new IOException();
            }
        }
        currentImagefile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = currentImagefile.getAbsolutePath();
        return currentImagefile;
    }

    public void onActivityResult(UstadProfileEdit app_context, int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri inputImageUri1 = Uri.fromFile(currentImagefile);
                    try {
                        File p = Utilforbitmap.getCompressed(app_context, inputImageUri1.getPath());
                        if (p != null) {
                            Uri temp = FileProvider.getUriForFile(app_context, "com.andrinotech.ustadapp.fileprovider", p);
                            if (temp != null) {
                                getmCallback().getImageFromIntent(temp, p);

                            } else {
                                getmCallback().getImageFromIntent(inputImageUri1, p);

                            }
                        } else {
                            getmCallback().getImageFromIntent(inputImageUri1, p);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case 2:
                    Uri uri = data.getData();
                    try {
                        String path = FileHelper.getPath(app_context, uri);

                        File p = Utilforbitmap.getCompressed(app_context, path);
                        if (p != null) {
                            Uri temp = FileProvider.getUriForFile(app_context, "com.andrinotech.ustadapp.fileprovider", p);
                            if (temp != null) {
                                getmCallback().getImageFromIntent(temp, p);

                            } else {
                                getmCallback().getImageFromIntent(uri, p);
                            }
                        } else {
                            getmCallback().getImageFromIntent(uri, p);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                default:
            }

        }
    }

}
