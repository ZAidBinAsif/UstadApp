package com.andrinotech.ustadapp.ui.Post;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrinotech.ustadapp.HomeActivity;
import com.andrinotech.ustadapp.R;
import com.andrinotech.ustadapp.helper.AVProgressDialog;
import com.andrinotech.ustadapp.ui.base.BaseFragment;
import com.andrinotech.ustadapp.utils.CommonUtils;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class ShowPostFragment extends BaseFragment<PostViewModel> implements PostCallback, PostAdapter.ClickListener {
    private AVProgressDialog mLoadingDialog;
    PostAdapter adapter;
    TextView emptyview;
    Toolbar toolbar;
    TextView title;
    TextView name;
    private RecyclerView recyclerView;
    private ArrayList<PostModelResponse> postModelResponses = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getViewModel().setCallBack(this);
        mLoadingDialog = new AVProgressDialog(getContext());
        initViews();
        setHasOptionsMenu(true);
        return mRootView;
    }

    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_news_feed;
    }

    @Override
    public PostViewModel initViewHolder() {
        return new PostViewModel();
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
        adapter = new PostAdapter(getContext(), postModelResponses, this);
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
        adapter = new PostAdapter(getContext(), postModelResponses, this);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    @Override
    public void allcomments(ArrayList<Comment> postModelResponses) {
    }

    @Override
    public void initViews() {
        emptyview = mRootView.findViewById(R.id.emptyview);
        name=mRootView.findViewById(R.id.text);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter(getContext(), new ArrayList<PostModelResponse>(), this);
        recyclerView.setAdapter(adapter);
        getPosts();
    }

    public void getPosts() {
        mLoadingDialog.show();
        getViewModel().getPosts();
    }

    @Override
    public void onFavClick(int pos, PostModelResponse model) {
        mLoadingDialog.show();
        getViewModel().likepost(Integer.parseInt(model.getId()), pos);
    }

    @Override
    public void onProfileClick(int pos, PostModelResponse model) {
        ((HomeActivity) getActivity()).setProfileFragment();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.post_fragment_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        ImageView icon = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        ImageView icon1 = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
//        ImageView icon2 = mSearchView.findViewById(android.support.v7.appcompat.R.id.sea);

        icon.setColorFilter(Color.WHITE);
        icon1.setColorFilter(Color.WHITE);
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).showHideText(true);
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ((HomeActivity)getActivity()).showHideText(false);

                return false;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }
}