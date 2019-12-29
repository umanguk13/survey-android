package com.survey.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.databinding.ActivitySplashBinding;

public class ActivitySplash extends BaseActivity {

    ActivitySplashBinding mActivitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivitySplashBinding = (ActivitySplashBinding) putContentView(R.layout.activity_splash);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntentLogin = new Intent(ActivitySplash.this, ActivityLogin.class);
                finishAffinity();
                startActivity(mIntentLogin);
            }
        }, 2000);

    }

}
