package com.survey.surveyapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.databinding.BaseActivityBinding;
import com.survey.surveyapp.helper.Utility;

public abstract class BaseActivity<T extends ViewDataBinding, V extends ViewModel> extends MyDaggerActivity {

    BaseActivityBinding mBaseActivityBinding;

    //Helper Objects
    public Context mContext;
    public Utility mUtility;

    //Toolbar
    public Toolbar mToolbarMain;
    public ImageView mImageViewMenu;
    public TextView mTextViewTitle;

    //Navigation Bar
    public DrawerLayout mDrawerLayout;
    public RelativeLayout mRelativeLayoutMainHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mContext = BaseActivity.this;
        mBaseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity);
        mUtility = new Utility(BaseActivity.this);

        initToolbar();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    protected <T extends ViewDataBinding> T putContentView(@LayoutRes int resId) {
        return DataBindingUtil.inflate(getLayoutInflater(), resId, mBaseActivityBinding.baseActivityFlContent, true);
    }

    public void initToolbar() {
        mToolbarMain = mBaseActivityBinding.baseActivityToolbar;
        mDrawerLayout = mBaseActivityBinding.baseActivityDrawerContainer;
        mImageViewMenu = mBaseActivityBinding.baseActivityToolbarImageviewMenu;
        mTextViewTitle = mBaseActivityBinding.baseActivityToolbarTextviewTitle;

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbarMain,
                R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mImageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    //region FUNCTION TO SHOW LOG
    public void ShowLog(String mStringActivityName, String mStringTitle, String mStringMessage) {
        System.out.println("Darshan... " + mStringActivityName + " " + mStringTitle + " : " + mStringMessage);
    }
    //endregion

}
