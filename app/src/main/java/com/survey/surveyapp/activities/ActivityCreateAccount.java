package com.survey.surveyapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityCreateAccountBinding;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.viewmodels.CreateAccountViewModel;
import com.survey.surveyapp.vo.VoResponseRegister;

import org.json.JSONObject;

public class ActivityCreateAccount extends BaseActivity {

    ActivityCreateAccountBinding mActivityCreateAccountBinding;

    CreateAccountViewModel mCreateAccountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCreateAccountBinding = (ActivityCreateAccountBinding) putContentView(R.layout.activity_create_account);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mCreateAccountViewModel = new CreateAccountViewModel(ActivityCreateAccount.this, mMyService);
        mActivityCreateAccountBinding.setCreateAccountViewModel(mCreateAccountViewModel);
        mActivityCreateAccountBinding.setLifecycleOwner(this);

    }

}
