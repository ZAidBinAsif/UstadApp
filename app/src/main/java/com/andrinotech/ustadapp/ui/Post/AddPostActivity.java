package com.andrinotech.ustadapp.ui.Post;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.utils.CommonUtils;
import com.andrinotech.ustadapp.utils.multiselect.MultiSelectDialog;
import com.andrinotech.ustadapp.utils.multiselect.MultiSelectModel;

import java.util.ArrayList;

public class AddPostActivity extends BaseActivity<PostViewModel> implements PostCallback, View.OnClickListener {
    private EditText title, description;
    TextView category;
    Button addpost;
    private AVProgressDialog mLoadingDialog;
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
        setTitle("Add Post");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_post;

    }


    @Override
    public PostViewModel initViewModel() {
        return new PostViewModel();
    }

    public void initViews() {

        title = findViewById(R.id.et_title);
        description = findViewById(R.id.et_dec);
        category = findViewById(R.id.category);
        addpost = findViewById(R.id.addpost);
        category.setOnClickListener(this);
        addpost.setOnClickListener(this);
        mLoadingDialog = new AVProgressDialog(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addpost:
                addpost();
                break;
            case R.id.category:
                showDialog();
                break;
            case android.R.id.home:
                finish();

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
//                                    Toast.makeText(UstadProfileEdit.this, "Selected Ids : " + selectedIds.get(i) + "\n" +
//                                            "Selected Names : " + selectedNames.get(i) + "\n" +
//                                            "DataString : " + dataString, Toast.LENGTH_SHORT).show();
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


    private void addpost() {
        mLoadingDialog.show();
        String titletext = title.getText().toString();
        String desxription = description.getText().toString();
        String text = category.getText().toString();

        getViewModel().addPost(titletext, desxription, text);

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
        finish();
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

    }

    @Override
    public void allcomments(ArrayList<Comment> postModelResponses) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
