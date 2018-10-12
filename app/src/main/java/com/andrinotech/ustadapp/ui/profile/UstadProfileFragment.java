package com.andrinotech.ustadapp.ui.profile;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.ui.ChangePassword.ChangePasswordActvitiy;
import com.andrinotech.ustadapp.ui.EditPassword.EditPasswordActvitiy;
import com.andrinotech.ustadapp.ui.Post.AddPostActivity;
import com.andrinotech.ustadapp.ui.login.Ustad;
import com.andrinotech.ustadapp.utils.GlideHelper;


@SuppressLint("ValidFragment")
public class UstadProfileFragment extends Fragment implements View.OnClickListener {

    TextView name, username, phone, email, category, info, skils, changepasword, editprofile;
    ImageView imageView_logo;
    private TextView status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ustad_profile_screen, container, false);
        imageView_logo = view.findViewById(R.id.imageView_logo);
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        category = view.findViewById(R.id.category);
        info = view.findViewById(R.id.info);
        skils = view.findViewById(R.id.skils);
        changepasword = view.findViewById(R.id.changepasword);
        editprofile = view.findViewById(R.id.editprofile);
        editprofile.setOnClickListener(this);
        status = view.findViewById(R.id.status);

        changepasword.setOnClickListener(this);
        setData();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        Ustad user = UserManager.getInstance().getMetaData().getUser();
        name.setText(user.getName());
        username.setText(user.getUsername());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        category.setText(user.getCategory() == null ? " " : user.getCategory());
        info.setText(user.getInfo() == null ? " " : user.getInfo());
        skils.setText(user.getSkils() == null ? " " : user.getSkils());
        String path = user.getLogo() == null ? " " : user.getLogo();
        GlideHelper.loadImage(getContext(), path, imageView_logo, R.drawable.ic_profile_plc);
        status.setText(user.getStatus());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editprofile:
                startActivity(new Intent(getActivity(), UstadProfileEdit.class));
                break;
            case R.id.changepasword:
                Intent intent = new Intent(getActivity(), EditPasswordActvitiy.class);
                intent.putExtra("fromlogin", "false");
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
