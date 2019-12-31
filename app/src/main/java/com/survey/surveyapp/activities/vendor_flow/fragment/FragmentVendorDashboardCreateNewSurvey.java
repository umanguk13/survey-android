package com.survey.surveyapp.activities.vendor_flow.fragment;

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
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorAddSurveyQuestions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentVendorDashboardCreateNewSurvey extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    @BindView(R.id.fragment_vendor_dashboard_create_new_survey_textview_next)
    TextView mTextViewNext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_dashboard_create_new_survey, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        mTextViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), ActivityVendorAddSurveyQuestions.class);
                startActivity(mIntent);
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
