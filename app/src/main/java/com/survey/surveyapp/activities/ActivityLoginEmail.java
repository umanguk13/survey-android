package com.survey.surveyapp.activities;

import android.os.Bundle;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityLoginEmailBinding;
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
                if (isValid()) {
                    if (mUtility.haveInternet()) {
                        mLoginEmailViewModel.doNormalLogin();
                    } else {
                        mUtility.errorDialog(getResources().getString(R.string.internet_error));
                    }
                }
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
