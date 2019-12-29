package com.survey.surveyapp.activities.user_flow;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.MyDaggerActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.UserBaseActivityBinding;
import com.survey.surveyapp.helper.Utility;

public abstract class UserBaseActivity<T extends ViewDataBinding, V extends ViewModel> extends MyDaggerActivity {

    UserBaseActivityBinding mUserBaseActivityBinding;

    //Helper Objects
    public Context mContext;
    public Utility mUtility;

    //Toolbar
    public Toolbar mToolbarMain;
    public ImageView mImageViewMenu;
    public TextView mTextViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mContext = UserBaseActivity.this;
        mUserBaseActivityBinding = DataBindingUtil.setContentView(this, R.layout.user_base_activity);
        mUtility = new Utility(UserBaseActivity.this);

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
        return DataBindingUtil.inflate(getLayoutInflater(), resId, mUserBaseActivityBinding.userBaseActivityFlContent, true);
    }

    public void initToolbar() {
        mToolbarMain = mUserBaseActivityBinding.userBaseActivityToolbar;
        mImageViewMenu = mUserBaseActivityBinding.userBaseActivityToolbarImageviewMenu;
        mTextViewTitle = mUserBaseActivityBinding.userBaseActivityToolbarTextviewTitle;
    }

    //region FUNCTION TO SHOW LOG
    public void ShowLog(String mStringActivityName, String mStringTitle, String mStringMessage) {
        System.out.println("Darshan... " + mStringActivityName + " " + mStringTitle + " : " + mStringMessage);
    }
    //endregion

}
