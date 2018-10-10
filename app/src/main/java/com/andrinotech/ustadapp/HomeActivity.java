package com.andrinotech.ustadapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.andrinotech.ustadapp.FragNav.FragNavController;
import com.andrinotech.ustadapp.ui.Post.AddPostActivity;
import com.andrinotech.ustadapp.ui.Post.ShowPostFragment;
import com.andrinotech.ustadapp.ui.base.BaseActivity;
import com.andrinotech.ustadapp.ui.base.tempViewModel;
import com.andrinotech.ustadapp.ui.profile.UstadProfileFragment;

import java.lang.reflect.Field;

public class HomeActivity extends BaseActivity<tempViewModel> implements FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    private BottomNavigationView mnav_bottomBar;
    private final int FRAG_FEED = FragNavController.TAB1;
    private final int FRAG_MAP = FragNavController.TAB2;
    private final int FRAG_FRIEND = FragNavController.TAB3;
    private final int FRAG_TRENDING = FragNavController.TAB4;
    private final int FRAG_ME = FragNavController.TAB5;
    private FloatingActionButton addPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_feeds);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initViews();
        initData(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_local_feeds;
    }

    @Override
    public tempViewModel initViewModel() {
        return new tempViewModel();
    }

    private void initViews() {
        mnav_bottomBar = (BottomNavigationView) findViewById(R.id.nav_bottomBar);
        addPost = (FloatingActionButton) findViewById(R.id.fabChat);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddPostActivity.class));
            }
        });

        disableShiftMode(mnav_bottomBar);
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void initData(Bundle savedInstanceState) {
        setmNavController(FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_frame)
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .build());

        setmTransactionListener(this);
        replaceFragment(FRAG_FEED);

        mnav_bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        addPost.setVisibility(View.VISIBLE);

                        replaceFragment(FRAG_FEED);
                        return true;
                    case R.id.nav_order:
                        addPost.setVisibility(View.GONE);

                        replaceFragment(FRAG_FRIEND);
                        return true;
                    case R.id.nav_account:
                        addPost.setVisibility(View.GONE);
                        replaceFragment(FRAG_TRENDING);
                        return true;
                    default:
                        return true;
                }
            }
        });

    }

    // Replace fragment
    public void replaceFragment(final int map) {
        getmNavController().switchTab(map);
    }


    @Override
    public void onTabTransaction(Fragment fragment, int index) {
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

    }

    public void setProfileFragment() {
        mnav_bottomBar.setSelectedItemId(R.id.nav_account);

        replaceFragment(FRAG_TRENDING);
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FRAG_FEED:
                addPost.setVisibility(View.VISIBLE);
                return new ShowPostFragment();
            case FRAG_TRENDING:
                addPost.setVisibility(View.GONE);

                return new UstadProfileFragment();
            case FRAG_FRIEND:
                addPost.setVisibility(View.GONE);

                return new UstadProfileFragment();
        }
        throw new IllegalStateException("");
    }
}