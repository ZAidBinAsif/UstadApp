package com.andrinotech.ustadapp.ui.profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.ui.Post.PostModelResponse;
import com.andrinotech.ustadapp.utils.GlideHelper;
import com.andrinotech.ustadapp.utils.StringUtils;


public class UstadProfileDetail extends AppCompatActivity {

    TextView name, username, phone, email, category, info, skils, status, editprofile;
    ImageView imageView_logo;
    LinearLayout passwoedlayput;
    private PostModelResponse ustad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ustad_profile_screen);
        initViews();

    }


    public void initViews() {

        if (getIntent() != null && getIntent().getStringExtra("ustad") != null) {
            String ustadjson = getIntent().getStringExtra("ustad");
            ustad = StringUtils.getGson().fromJson(ustadjson, PostModelResponse.class);
            if (ustad == null) {
                finish();
            }
        } else {
            finish();
        }

        imageView_logo = findViewById(R.id.imageView_logo);
        passwoedlayput = findViewById(R.id.passwoedlayput);
        editprofile = findViewById(R.id.editprofile);
        editprofile.setVisibility(View.GONE);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        status = findViewById(R.id.status);
        email = findViewById(R.id.email);
        category = findViewById(R.id.category);
        info = findViewById(R.id.info);
        skils = findViewById(R.id.skils);
        passwoedlayput.setVisibility(View.GONE);
        setData();
    }

    private void setData() {
        name.setText(ustad.getUstad().getName());
        status.setText(ustad.getUstad().getStatus());
        username.setText(ustad.getUstad().getUsername());
        phone.setText(ustad.getUstad().getPhone());
        email.setText(ustad.getUstad().getEmail());
        category.setText(ustad.getCategory() == null ? "" : ustad.getCategory());
        info.setText(ustad.getUstad().getInfo() == null ? "" : ustad.getUstad().getInfo());
        skils.setText(ustad.getUstad().getSkils() == null ? "" : ustad.getUstad().getSkils());
        String path = ustad.getUstad().getLogo() == null ? "" : ustad.getUstad().getLogo();
        GlideHelper.loadImage(this, path, imageView_logo, R.drawable.ic_profile_plc);

    }

}
