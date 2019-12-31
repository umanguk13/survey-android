package com.survey.surveyapp.activities.vendor_flow.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorDraftSurveys;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorReceivedResponses;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentVendorMenu extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    ActivityVendorMain mActivityVendorMain;

    @BindView(R.id.fragment_vendor_menu_textview_saved_surveys)
    TextView mTextViewSavedSurveys;

    @BindView(R.id.fragment_vendor_menu_textview_posted_surveys)
    TextView mTextViewPostedSurveys;

    @BindView(R.id.fragment_vendor_menu_textview_received_survey_response)
    TextView mTextViewReceivedSurveyResponses;

    @BindView(R.id.fragment_vendor_menu_textview_draw_a_raffle)
    TextView mTextViewDrawRaffle;

    @BindView(R.id.fragment_vendor_menu_textview_winners)
    TextView mTextViewWinners;

    @BindView(R.id.fragment_vendor_menu_textview_reports)
    TextView mTextViewReports;

    @BindView(R.id.fragment_vendor_menu_textview_purchase_a_plan)
    TextView mTextViewPurchasePlan;

    @BindView(R.id.fragment_vendor_menu_textview_about_survey_raffle)
    TextView mTextViewAboutSurveyRaffle;

    @BindView(R.id.fragment_vendor_menu_textview_give_feedback)
    TextView mTextViewGiveFeedback;

    @BindView(R.id.fragment_vendor_menu_textview_advertisement_with_us)
    TextView mTextViewAdvertisementWithUs;

    @BindView(R.id.fragment_vendor_menu_textview_terms_of_use)
    TextView mTextViewTermsOfUse;

    @BindView(R.id.fragment_vendor_menu_textview_privacy_policy)
    TextView mTextViewPrivacyPolicy;

    @BindView(R.id.fragment_vendor_menu_textview_logout)
    TextView mTextViewLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityVendorMain = (ActivityVendorMain) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_menu, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        mTextViewSavedSurveys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mActivityVendorMain, ActivityVendorDraftSurveys.class);
                startActivity(mIntent);
            }
        });

        mTextViewPostedSurveys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityVendorMain.moveToPostedSurveys();
            }
        });

        mTextViewReceivedSurveyResponses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mActivityVendorMain, ActivityVendorReceivedResponses.class);
                startActivity(mIntent);
            }
        });

        mTextViewDrawRaffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivityVendorMain, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewWinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivityVendorMain, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivityVendorMain, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewPurchasePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivityVendorMain, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
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
