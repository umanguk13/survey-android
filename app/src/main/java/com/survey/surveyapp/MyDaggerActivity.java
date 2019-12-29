package com.survey.surveyapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.survey.surveyapp.service.MyService;

import javax.inject.Inject;

public class MyDaggerActivity extends AppCompatActivity {

    @Inject
    public MyService mMyService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SurveyApplication) getApplication()).getDeps().injectMyDaggerActivity(this);

    }
}
