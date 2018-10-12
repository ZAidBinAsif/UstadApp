package com.andrinotech.ustadapp.ui.profile;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.andrinotech.ustadapp.HomeActivity;
import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.databinding.ProfilePageOfustadBinding;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.helper.DateUtils;
import com.andrinotech.ustadapp.ui.Post.Comment;
import com.andrinotech.ustadapp.ui.Post.LikePostResponseModel;
import com.andrinotech.ustadapp.ui.Post.PostCallback;
import com.andrinotech.ustadapp.ui.Post.PostModelResponse;
import com.andrinotech.ustadapp.ui.Post.PostViewModel;
import com.andrinotech.ustadapp.ui.Post.ShowOthersPostActivity;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.GlideHelper;
import com.andrinotech.ustadapp.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Calendar;

public class UstadProfile extends BaseActivity<PostViewModel> implements PostCallback, View.OnClickListener {
    private AVProgressDialog mLoadingDialog;
    //    private  binding;
    private PostModelResponse ustad;
    private ProfilePageOfustadBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setCallBack(this);
        binding = DataBindingUtil.setContentView(this, R.layout.profile_page_ofustad);
        initViews();

    }

    @Override
    public int getLayout() {
        return R.layout.profile_page_ofustad;

    }


    @Override
    public PostViewModel initViewModel() {
        return new PostViewModel();
    }

    public void initViews() {
        mLoadingDialog = new AVProgressDialog(this);

        getPosts();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
    }


    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    public void getPosts() {

        if (getIntent() != null && getIntent().getStringExtra("ustad") != null) {
            String ustadjson = getIntent().getStringExtra("ustad");
            ustad = StringUtils.getGson().fromJson(ustadjson, PostModelResponse.class);
            if (ustad == null) {
                finish();
            } else {
                getViewModel().getPostsofUstad(Integer.parseInt(ustad.getUstad().getId()));
                Glide.with(this)
                        .load(ustad.getUstad().getLogo())
                        .fitCenter()
                        .error(R.drawable.ic_profile_plc)
                        .placeholder(R.drawable.ic_profile_plc)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.image);
                binding.name.setText(ustad.getUstad().getName());
                String price = ustad.getUstad().getPrice() == null ? " " : "Rs : " + ustad.getUstad().getPrice();
                binding.price.setText(price);
                binding.categoryustad.setText(ustad.getCategory() == null ? "" : ustad.getCategory());
                binding.skilltext.setText(ustad.getUstad().getSkils());
                binding.personaltext.setText(ustad.getUstad().getInfo());
                binding.personalskill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UstadProfile.this, UstadProfileDetail.class);
                        intent.putExtra("ustad", StringUtils.getGson().toJson(ustad));
                        startActivity(intent);
                    }
                });
                binding.readskill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UstadProfile.this, UstadProfileDetail.class);
                        intent.putExtra("ustad", StringUtils.getGson().toJson(ustad));
                        startActivity(intent);
                    }
                });
                binding.backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(UstadProfile.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        } else {
            finish();
        }
        mLoadingDialog.show();

    }

    @Override
    public void ValidationError(PostViewModel.validationEnum validationEnum) {


    }

    @Override
    public void SuccessFullyAdded() {
        dismissDialog();
    }

    @Override
    public void likeAdded(int post, LikePostResponseModel likePostResponseModel) {

    }

    @Override
    public void ErrorOnAddPost(String str) {
        dismissDialog();
        CommonUtils.showToast(str);
    }

    @Override
    public void InternetError(String str) {
        dismissDialog();
        CommonUtils.showToast(str);

    }

    @Override
    public void allposts(ArrayList<PostModelResponse> postModelResponses) {
        dismissDialog();
        if (postModelResponses.size() > 0) {
            final PostModelResponse model = postModelResponses.get(0);
            String path = ustad.getUstad().getLogo() == null ? "" : ustad.getUstad().getLogo();

            GlideHelper.loadImage(this, "http://192.168.42.71/appserver/public/uploads/ustads/3054ce3851fc260e1e4191e06f4cab04.png", binding.imageViewLogo, R.drawable.ic_profile_plc);
            binding.category.setText(model.getCategory() == null ? " " : model.getCategory());
            binding.decription.setText(model.getText());
            binding.postname.setText(model.getUstad().getName());
            binding.title.setText(model.getTitle());
            binding.status.setText(model.getUstad().getStatus());
            String date;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(model.getTime() * 1000);
            Calendar now = Calendar.getInstance();
            if (android.text.format.DateUtils.isToday(model.getTime() * 1000)) {
                date = "Today";
            } else if (now.get(Calendar.DATE) - cal1.get(Calendar.DATE) == 1) {
                date = "Yesterday";
            } else {
                String cTime = DateUtils.ConvertMilliSecondsToDate(model.getTime() * 1000);
                date = cTime.trim();
            }

            binding.time.setText(DateUtils.convertMillisecondsToTime(model.getTime()) + " " + date);

            binding.view9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UstadProfile.this, ShowOthersPostActivity.class);
                    intent.putExtra("ustad", StringUtils.getGson().toJson(ustad));
                    startActivity(intent);
                }
            });
        } else {
            binding.newfeedlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onePostResponse(PostModelResponse postModelResponses) {

    }

    @Override
    public void allcomments(ArrayList<Comment> postModelResponses) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
