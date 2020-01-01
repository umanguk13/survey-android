package com.survey.surveyapp.activities.user_flow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityLogin;
import com.survey.surveyapp.activities.ActivitySelectFlow;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUserMenu extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    private ActivityUserMain mActivityUserMain;

    @BindView(R.id.fragment_user_menu_textview_logout)
    TextView mTextViewLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityUserMain = (ActivityUserMain) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_user_menu, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        mTextViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentLogout = new Intent(mActivityUserMain, ActivitySelectFlow.class);
                mActivityUserMain.finishAffinity();
                startActivity(mIntentLogout);
            }
        });

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {

        }
    }

}
