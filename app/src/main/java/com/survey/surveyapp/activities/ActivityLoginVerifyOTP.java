package com.survey.surveyapp.activities;

import android.os.Bundle;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityLoginVerifyOtpBinding;
import com.survey.surveyapp.viewmodels.LoginVerifyOtpViewModel;

public class ActivityLoginVerifyOTP extends BaseActivity {

    ActivityLoginVerifyOtpBinding mActivityLoginVerifyOtpBinding;

    LoginVerifyOtpViewModel mLoginVerifyOtpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityLoginVerifyOtpBinding = (ActivityLoginVerifyOtpBinding) putContentView(R.layout.activity_login_verify_otp);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mLoginVerifyOtpViewModel = new LoginVerifyOtpViewModel(ActivityLoginVerifyOTP.this, mMyService);
        mActivityLoginVerifyOtpBinding.setLoginVerifyOtpViewModel(mLoginVerifyOtpViewModel);
        mActivityLoginVerifyOtpBinding.setLifecycleOwner(this);

    }

}
