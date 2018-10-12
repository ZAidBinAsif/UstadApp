package com.andrinotech.ustadapp.ui.Post;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.databinding.PostdetailBinding;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.helper.DateUtils;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.ui.profile.UstadProfile;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.GlideHelper;
import com.andrinotech.ustadapp.utils.StringUtils;

import java.util.ArrayList;

public class PostDetail extends BaseActivity<PostViewModel> implements PostCallback, View.OnClickListener {
    private AVProgressDialog mLoadingDialog;
    //    private  binding;
    private PostModelResponse ustad;
    private PostdetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setCallBack(this);
        binding = DataBindingUtil.setContentView(this, R.layout.postdetail);
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

        if (getIntent() != null && getIntent().getStringExtra("postId") != null) {
            String posiID = getIntent().getStringExtra("postId");
            getViewModel().getPostsofUstad(Integer.parseInt(posiID));

            mLoadingDialog.show();
        }
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
        getPosts();
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

    }

    @Override
    public void onePostResponse(final PostModelResponse model) {
        dismissDialog();
        String path = ustad.getUstad().getLogo() == null ? "" : ustad.getUstad().getLogo();

        GlideHelper.loadImage(this, path, binding.imageViewLogo, R.drawable.ic_profile_plc);
        binding.category.setText(model.getCategory());
        binding.decription.setText(model.getText());
        binding.name.setText(model.getUstad().getName());
        binding.title.setText(model.getTitle());
        binding.time.setText(DateUtils.convertMillisecondsToTime(model.getTime()));

        if (model.getLike() != null && model.getLike().getType().equalsIgnoreCase("like")) {
            binding.like.setImageResource(R.drawable.ic_thumb_like);
        } else if (model.getLike() != null && model.getLike().getType().equalsIgnoreCase("unlike")) {
            binding.unlike.setImageResource(R.drawable.ic_thumb_unlike);
        } else {
            binding.like.setImageResource(R.drawable.ic_thumb);
            binding.unlike.setImageResource(R.drawable.ic_unlike);
        }
        binding.totallike.setText(model.getTotal_like());
        binding.totalunlike.setText(model.getTotal_unlike());
        binding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel().likepost(Integer.parseInt(model.getId()), 0);
            }
        });
        binding.unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel().unlikepost(Integer.parseInt(model.getId()), 0);
            }
        });
        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetail.this, AddCommentActivity.class);
                intent.putExtra("id", model.getId());
                startActivity(intent);
            }
        });
        binding.imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getUstad().getId().equalsIgnoreCase(UserManager.getInstance().getMetaData().getUser().getId())) {
//                        clickListener.onProfileClick(postition, model);
                } else {
                    Intent intent = new Intent(PostDetail.this, UstadProfile.class);
                    intent.putExtra("ustad", StringUtils.getGson().toJson(model));
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void allcomments(ArrayList<Comment> postModelResponses) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
