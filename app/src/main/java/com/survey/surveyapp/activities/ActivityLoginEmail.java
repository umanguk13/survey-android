package com.survey.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.databinding.ActivityLoginEmailBinding;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.viewmodels.LoginEmailViewModel;

public class ActivityLoginEmail extends BaseActivity {

    ActivityLoginEmailBinding mActivityLoginEmailBinding;

    LoginEmailViewModel mLoginEmailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityLoginEmailBinding = (ActivityLoginEmailBinding) putContentView(R.layout.activity_login_email);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mLoginEmailViewModel = new LoginEmailViewModel(ActivityLoginEmail.this, mMyService);
        mActivityLoginEmailBinding.setLoginEmailViewModel(mLoginEmailViewModel);
        mActivityLoginEmailBinding.setLifecycleOwner(this);

        mActivityLoginEmailBinding.activityLoginEmailEmailTextviewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID).equalsIgnoreCase(TagValues.USER_ROLE_ID)) {
                    Intent mIntentUser = new Intent(ActivityLoginEmail.this, ActivityUserMain.class);
                    startActivity(mIntentUser);
                } else if (mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID).equalsIgnoreCase(TagValues.VENDOR_ROLE_ID)) {
                    Intent mIntentVendor = new Intent(ActivityLoginEmail.this, ActivityVendorMain.class);
                    startActivity(mIntentVendor);
                }
                
//                if (isValid()) {
//                    if (mUtility.haveInternet()) {
//                        mLoginEmailViewModel.doNormalLogin();
//                    } else {
//                        mUtility.errorDialog(getResources().getString(R.string.internet_error));
//                    }
//                }
            }
        });

    }

    public boolean isValid() {

        if (mLoginEmailViewModel.mStringEmail.getValue() == null
                || mLoginEmailViewModel.mStringEmail.getValue().equalsIgnoreCase("")) {
            mActivityLoginEmailBinding.activityLoginEmailEmailEdittextUsername.setError("Please enter username!");
            return false;
        }

        if (mLoginEmailViewModel.mStringPassword.getValue() == null
                || mLoginEmailViewModel.mStringPassword.getValue().equalsIgnoreCase("")) {
            mActivityLoginEmailBinding.activityLoginEmailEmailEdittextPassword.setError("Please enter password!");
            return false;
        }

        return true;
    }

}
