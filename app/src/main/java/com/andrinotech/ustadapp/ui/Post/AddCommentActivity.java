package com.andrinotech.ustadapp.ui.Post;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.helper.DateUtils;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.StringUtils;
import com.andrinotech.ustadapp.utils.multiselect.MultiSelectModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Calendar;

public class AddCommentActivity extends BaseActivity<PostViewModel> implements PostCallback, View.OnClickListener {
    private EditText inputAddComment;
    Button addCommentBtn;
    TextView textNotComnt;
    ProgressBar progressComments;
    private AVProgressDialog mLoadingDialog;
    ArrayList<MultiSelectModel> strings = new ArrayList<>();
    private int postid;
    private RecyclerView recyclerView;
    private CustomCommentsAdapter adapter;
    private ArrayList<Comment> postModelResponses;
    private Toolbar mToolbarl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setCallBack(this);
        initViews();

    }

    @Override
    public int getLayout() {
        return R.layout.write_cmt_activity;

    }


    @Override
    public PostViewModel initViewModel() {
        return new PostViewModel();
    }

    public void initViews() {
        if (getIntent() != null && getIntent().getStringExtra("id") != null) {
            postid = Integer.parseInt(getIntent().getStringExtra("id"));
            getViewModel().getPostComment(postid);
        } else {

            finish();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomCommentsAdapter();
        recyclerView.setAdapter(adapter);
        mToolbarl = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(mToolbarl);
        addCommentBtn = findViewById(R.id.addCommentBtn);
        progressComments = findViewById(R.id.progressComments);
        inputAddComment = findViewById(R.id.inputAddComment);
        textNotComnt = findViewById(R.id.textNotComnt);
        addCommentBtn.setOnClickListener(this);
        mLoadingDialog = new AVProgressDialog(this);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (postModelResponses!=null &&postModelResponses.size() > 1) {
                    if (bottom < oldBottom) {
                        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                        int firstVisiblePosition = layoutManager.findLastVisibleItemPosition();

                        boolean endHasBeenReached = firstVisiblePosition + 5 >= postModelResponses.size();
                        if (postModelResponses.size() > 0 && endHasBeenReached) {
                            recyclerView.smoothScrollToPosition(
                                    recyclerView.getAdapter().getItemCount() - 1);
                        }
//                        chatListView.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                chatListView.smoothScrollToPosition(
//                                        chatListView.getAdapter().getCount() - 1);
//                            }
//                        }, 100);
                    }
                }
            }

        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addCommentBtn:
                addpost();
                break;

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


    private void addpost() {
        mLoadingDialog.show();
        String comment = inputAddComment.getText().toString();
        if (StringUtils.isNullOrEmpty(comment)) {
            CommonUtils.showToast("Please add some text");
        } else {
            getViewModel().addComment(comment, postid);

        }

    }


    @Override
    public void ValidationError(PostViewModel.validationEnum validationEnum) {
        if (validationEnum.equals(validationEnum.title)) {
            CommonUtils.showToast("title required");
        }
        if (validationEnum.equals(validationEnum.descrtion)) {
            CommonUtils.showToast("Description required");
        }
        if (validationEnum.equals(validationEnum.category)) {
            CommonUtils.showToast("category required");
        }
        dismissDialog();

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
        recyclerView.setVisibility(View.GONE);
        textNotComnt.setVisibility(View.VISIBLE);
        progressComments.setVisibility(View.GONE);
        textNotComnt.setText(str);

    }

    @Override
    public void InternetError(String str) {
        dismissDialog();
        CommonUtils.showToast(str);
        recyclerView.setVisibility(View.GONE);
        textNotComnt.setVisibility(View.VISIBLE);
        progressComments.setVisibility(View.GONE);
        textNotComnt.setText(str);
    }

    @Override
    public void allposts(ArrayList<PostModelResponse> postModelResponses) {

    }

    @Override
    public void onePostResponse(PostModelResponse postModelResponses) {

    }

    @Override
    public void allcomments(ArrayList<Comment> postModelResponses) {
        if (postModelResponses.size() > 0) {
            progressComments.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new CustomCommentsAdapter();
            textNotComnt.setVisibility(View.GONE);
            this.postModelResponses = postModelResponses;
            recyclerView.setAdapter(adapter);

            recyclerView.scrollToPosition(postModelResponses.size() - 1);
        } else {
            recyclerView.setVisibility(View.GONE);
            textNotComnt.setVisibility(View.VISIBLE);
            progressComments.setVisibility(View.GONE);
        }
        inputAddComment.setText("");
        dismissDialog();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    class CustomCommentsAdapter extends RecyclerView.Adapter<CustomCommentsAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageComments, unlike, like, comment;
            TextView textCmntsUsername, textCmntsData, textCmntsDate, time, decription, totallike, totalunlike;

            public ViewHolder(View view) {
                super(view);
                imageComments = (ImageView) view
                        .findViewById(R.id.imageComments);
                textCmntsUsername = (TextView) view
                        .findViewById(R.id.textCmntsUsername);
                textCmntsData = (TextView) view
                        .findViewById(R.id.textCmntsData);
                textCmntsDate = (TextView) view
                        .findViewById(R.id.textCmntsDate);
            }


        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(AddCommentActivity.this).inflate(R.layout.custom_comments, parent, false);
            return new CustomCommentsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String logo = postModelResponses.get(position).getUstad().getLogo() == null ? "" : postModelResponses.get(position).getUstad().getLogo();
            Glide.with(AddCommentActivity.this)
                    .load(logo).dontAnimate()
                    .fitCenter()
                    .error(R.drawable.ic_profile_plc)
                    .placeholder(R.drawable.ic_profile_plc)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageComments);
            holder.textCmntsUsername.setText(postModelResponses.get(position).getUstad().getName());
            holder.textCmntsData.setText(postModelResponses.get(position).getText());
            holder.textCmntsDate.setText(DateUtils.ConvertMilliSecondsToTime(postModelResponses.get(position).getTime()));


            String date;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(postModelResponses.get(position).getTime() * 1000);
            Calendar now = Calendar.getInstance();
            if (android.text.format.DateUtils.isToday(postModelResponses.get(position).getTime() * 1000)) {
                date = "Today";
            } else if (now.get(Calendar.DATE) - cal1.get(Calendar.DATE) == 1) {
                date = "Yesterday";
            } else {
                String cTime = DateUtils.ConvertMilliSecondsToDate(postModelResponses.get(position).getTime() * 1000);
                date = cTime.trim();
            }

            holder.textCmntsDate.setText(DateUtils.convertMillisecondsToTime(postModelResponses.get(position).getTime()) + " " + date);

        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getItemCount() {
            return postModelResponses == null ? 0 : postModelResponses.size();
        }



    }

}
