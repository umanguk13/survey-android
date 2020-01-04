package com.survey.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorAddSurveyQuestions;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.databinding.ActivitySplashBinding;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;

public class ActivitySplash extends BaseActivity {

    ActivitySplashBinding mActivitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivitySplashBinding = (ActivitySplashBinding) putContentView(R.layout.activity_splash);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        Utility mUtility = new Utility(ActivitySplash.this);
        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ID, "4d75e8e3-2289-4798-8854-a0da5bd7001e");
        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ACCESS_TOKEN, "f7351b65b3d955992d0f0ddb0dcedcd6");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntentLogin = new Intent(ActivitySplash.this, ActivityVendorMain.class);
                finishAffinity();
                startActivity(mIntentLogin);
            }
        }, 2000);

    }

}
