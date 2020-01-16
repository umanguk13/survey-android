package com.survey.surveyapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityLoginVerifyOtpBinding;
import com.survey.surveyapp.viewmodels.LoginVerifyOtpViewModel;

public class ActivityLoginVerifyOTP extends BaseActivity {

    ActivityLoginVerifyOtpBinding mActivityLoginVerifyOtpBinding;

    LoginVerifyOtpViewModel mLoginVerifyOtpViewModel;

    String mStringPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityLoginVerifyOtpBinding = (ActivityLoginVerifyOtpBinding) putContentView(R.layout.activity_login_verify_otp);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mLoginVerifyOtpViewModel = new LoginVerifyOtpViewModel(ActivityLoginVerifyOTP.this, mMyService);
        mActivityLoginVerifyOtpBinding.setLoginVerifyOtpViewModel(mLoginVerifyOtpViewModel);
        mActivityLoginVerifyOtpBinding.setLifecycleOwner(this);

        if (getIntent().hasExtra("phone_number")) {
            mStringPhoneNumber = getIntent().getStringExtra("phone_number");
            
            if (mStringPhoneNumber != null && !mStringPhoneNumber.equalsIgnoreCase("")) {
                mLoginVerifyOtpViewModel.sendVerificationCode(mStringPhoneNumber);
            }
        }

        mActivityLoginVerifyOtpBinding.activityLoginVerifyOtpEmailTextviewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    mLoginVerifyOtpViewModel.verifyVerificationCode(mActivityLoginVerifyOtpBinding.activityLoginVerifyOtpInputLayoutEnterOtp.getText().toString());
                }
            }
        });

    }

    public boolean isValid() {
        if (!mUtility.haveInternet()) {
            mUtility.errorDialog(getResources().getString(R.string.internet_error));
            return false;
        }

        if (mActivityLoginVerifyOtpBinding.activityLoginVerifyOtpInputLayoutEnterOtp.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(mContext, getString(R.string.enter_otp), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
