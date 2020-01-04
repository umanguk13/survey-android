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
import com.survey.surveyapp.helper.TagValues;

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

                if (mUtility.getAppPrefString(TagValues.PREF_USER_ID).equalsIgnoreCase("")) {
                    Intent mIntentLogin = new Intent(ActivitySplash.this, ActivitySelectFlow.class);
                    finishAffinity();
                    startActivity(mIntentLogin);
                } else {
                    if (mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID).equalsIgnoreCase(TagValues.USER_ROLE_ID)) {
                        Intent mIntentLogin = new Intent(ActivitySplash.this, ActivityUserMain.class);
                        finishAffinity();
                        startActivity(mIntentLogin);
                    } else {
                        Intent mIntentLogin = new Intent(ActivitySplash.this, ActivityVendorMain.class);
                        finishAffinity();
                        startActivity(mIntentLogin);
                    }
                }

            }
        }, 2000);

    }

}
