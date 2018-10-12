package com.andrinotech.ustadapp.ui.Post;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.StringUtils;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class ShowOthersPostActivity extends BaseActivity<PostViewModel> implements PostCallback, PostAdapter.ClickListener {
    private AVProgressDialog mLoadingDialog;
    PostAdapter adapter;
    TextView emptyview;
    private RecyclerView recyclerView;
    private ArrayList<PostModelResponse> postModelResponses = new ArrayList<>();
    private PostModelResponse ustad;
    private TextView text;


    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_othersnews_feed;
    }

    @Override
    public PostViewModel initViewModel() {
        return new PostViewModel();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getViewModel().setCallBack(this);
        initViews();
    }

    @Override
    public void ValidationError(PostViewModel.validationEnum validationEnum) {
    }

    @Override
    public void SuccessFullyAdded() {
        emptyview.setVisibility(View.VISIBLE);
        dismissDialog();
    }

    @Override
    public void likeAdded(int pos, LikePostResponseModel likePostResponseModel) {
        postModelResponses.set(pos, likePostResponseModel.getPostModel());
        adapter = new PostAdapter(this, postModelResponses, this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    @Override
    public void ErrorOnAddPost(String str) {
        CommonUtils.showToast(str);
        dismissDialog();
        emptyview.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void InternetError(String str) {
        CommonUtils.showToast(str);
        dismissDialog();
        emptyview.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void allposts(ArrayList<PostModelResponse> postModelResponses) {
        this.postModelResponses = postModelResponses;
        adapter = new PostAdapter(this, postModelResponses, this);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    @Override
    public void onePostResponse(PostModelResponse postModelResponses) {

    }

    @Override
    public void allcomments(ArrayList<Comment> postModelResponses) {
    }

    @Override
    public void initViews() {
        emptyview = findViewById(R.id.emptyview);
        mLoadingDialog = new AVProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(this, new ArrayList<PostModelResponse>(), this);
        recyclerView.setAdapter(adapter);
        text = findViewById(R.id.text);

        getPosts();
    }

    public void getPosts() {

        mLoadingDialog.show();
        if (getIntent() != null && getIntent().getStringExtra("ustad") != null) {
            String ustadjson = getIntent().getStringExtra("ustad");
            ustad = StringUtils.getGson().fromJson(ustadjson, PostModelResponse.class);
            if (ustad == null) {
                finish();
            } else {
                text.setText("Posts of " + ustad.getUstad().getName());

                getViewModel().getPostsofUstad(Integer.parseInt(ustad.getUstad().getId()));
            }

        }
    }

    @Override
    public void onFavClick(int pos, PostModelResponse model) {
        mLoadingDialog.show();
        getViewModel().likepost(Integer.parseInt(model.getId()), pos);
    }

    @Override
    public void onProfileClick(int pos, PostModelResponse model) {
//        ((HomeActivity) getActivity()).setProfileFragment();
    }

    @Override
    public void onFavDeleteClick(int pos, PostModelResponse model) {
        mLoadingDialog.show();
        getViewModel().unlikepost(Integer.parseInt(model.getId()), pos);
    }

    @Override
    public void onResume() {
        super.onResume();
//        getPosts();
    }
}