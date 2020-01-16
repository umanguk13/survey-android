package com.survey.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityLoginPhoneNumberBinding;
import com.survey.surveyapp.viewmodels.LoginEnterPhoneViewModel;

public class ActivityLoginPhoneNumber extends BaseActivity {

    ActivityLoginPhoneNumberBinding mActivityLoginPhoneNumberBinding;

    LoginEnterPhoneViewModel mLoginEnterPhoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityLoginPhoneNumberBinding = (ActivityLoginPhoneNumberBinding) putContentView(R.layout.activity_login_phone_number);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mLoginEnterPhoneViewModel = new LoginEnterPhoneViewModel(ActivityLoginPhoneNumber.this, mMyService);
        mActivityLoginPhoneNumberBinding.setLoginEnterPhoneViewModel(mLoginEnterPhoneViewModel);
        mActivityLoginPhoneNumberBinding.setLifecycleOwner(this);

        mActivityLoginPhoneNumberBinding.activityLoginPhoneNumberEmailTextviewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (!mLoginEnterPhoneViewModel.getPhoneNumber().contains("+")) {
                        mLoginEnterPhoneViewModel.strPhoneNumber.setValue("+" + mLoginEnterPhoneViewModel.strPhoneNumber.getValue());
                    }
                    
                    Intent mIntentVerifyOtp = new Intent(ActivityLoginPhoneNumber.this, ActivityLoginVerifyOTP.class);
                    mIntentVerifyOtp.putExtra("phone_number", mLoginEnterPhoneViewModel.getPhoneNumber());
                    startActivity(mIntentVerifyOtp);
                }
            }
        });

    }

    public boolean isValid() {
        if (!mUtility.haveInternet()) {
            mUtility.errorDialog(getResources().getString(R.string.internet_error));
            return false;
        }

        if (mLoginEnterPhoneViewModel.getPhoneNumber() == null || mLoginEnterPhoneViewModel.getPhoneNumber().equalsIgnoreCase("")) {
            Toast.makeText(mContext, getString(R.string.enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
