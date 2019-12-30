package com.survey.surveyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivitySelectFlowBinding;
import com.survey.surveyapp.helper.TagValues;

public class ActivitySelectFlow extends BaseActivity {

    ActivitySelectFlowBinding mActivitySelectFlowBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivitySelectFlowBinding = (ActivitySelectFlowBinding) putContentView(R.layout.activity_select_flow);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mActivitySelectFlowBinding.activitySelectFlowTextviewUserFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUtility.writeSharedPreferencesString(TagValues.PREF_SELECTED_ROLE_ID, TagValues.USER_ROLE_ID);
                gotoLogin();
            }
        });

        mActivitySelectFlowBinding.activitySelectFlowTextviewVendorFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUtility.writeSharedPreferencesString(TagValues.PREF_SELECTED_ROLE_ID, TagValues.VENDOR_ROLE_ID);
                gotoLogin();
            }
        });

    }

    public void gotoLogin() {
        Intent mIntentLogin = new Intent(ActivitySelectFlow.this, ActivityLogin.class);
        finishAffinity();
        startActivity(mIntentLogin);
    }

}
