package com.survey.surveyapp.activities.vendor_flow.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorSurveyDetail;
import com.survey.surveyapp.helper.DividerItemDecorationNavigation;
import com.survey.surveyapp.vo.VoResponseCurrentSurvey;
import com.survey.surveyapp.vo.VoResponseCurrentSurveyData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentVendorDashboardCurrentSurveys extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    ActivityVendorMain mActivityVendorMain;

    @BindView(R.id.fragment_vendor_dashboard_current_surveys_recyclerview)
    RecyclerView mRecyclerViewHotSurveys;

    //Adapter
    private CurrentSurveysAdapter mCurrentSurveysAdapter;

    //Data
    ArrayList<VoResponseCurrentSurveyData> mArrayListCurrentSurvey = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityVendorMain = (ActivityVendorMain) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_dashboard_current_surveys, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        if (mActivityVendorMain.mUtility.haveInternet()) {
            mActivityVendorMain.mVendorMainViewModel.fetchCurrentSurvey();
        }

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {

            if (mActivityVendorMain.mUtility.haveInternet()) {
                mActivityVendorMain.mVendorMainViewModel.fetchCurrentSurvey();
            }

        }
    }

    public void gotServiceResponse(VoResponseCurrentSurvey voResponseCurrentSurvey) {
        if (voResponseCurrentSurvey != null && voResponseCurrentSurvey.getSurvey_list() != null
                && voResponseCurrentSurvey.getSurvey_list().size() > 0) {
            System.out.println("Darshan... Current Survey gotServiceResponse() : " + mArrayListCurrentSurvey.size());

            mArrayListCurrentSurvey.clear();
            mArrayListCurrentSurvey.addAll(voResponseCurrentSurvey.getSurvey_list());

            mCurrentSurveysAdapter = new CurrentSurveysAdapter();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            mRecyclerViewHotSurveys.addItemDecoration(new DividerItemDecorationNavigation(mActivityVendorMain, DividerItemDecorationNavigation.VERTICAL_LIST));
            mRecyclerViewHotSurveys.setLayoutManager(mLinearLayoutManager);
            mRecyclerViewHotSurveys.setAdapter(mCurrentSurveysAdapter);

        }
    }

    public class CurrentSurveysAdapter extends RecyclerView.Adapter<CurrentSurveysAdapter.ViewHolder> {

        @NonNull
        @Override
        public CurrentSurveysAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_vendor_survey_list_item, parent, false);
            return new CurrentSurveysAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull final CurrentSurveysAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            if (mArrayListCurrentSurvey.get(position) != null) {

                if (mArrayListCurrentSurvey.get(position).getId() != null
                        && !mArrayListCurrentSurvey.get(position).getId().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyId.setText(mArrayListCurrentSurvey.get(position).getId());
                }

                if (mArrayListCurrentSurvey.get(position).getTitle() != null
                        && !mArrayListCurrentSurvey.get(position).getTitle().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyTitle.setText(mArrayListCurrentSurvey.get(position).getTitle());
                }

                if (mArrayListCurrentSurvey.get(position).getSurvey_category() != null
                        && mArrayListCurrentSurvey.get(position).getSurvey_category() != null
                        && mArrayListCurrentSurvey.get(position).getSurvey_category().getName() != null
                        && !mArrayListCurrentSurvey.get(position).getSurvey_category().getName().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyCategory.setText(mArrayListCurrentSurvey.get(position).getSurvey_category().getName());
                }

                if (mArrayListCurrentSurvey.get(position).getStart_date() != null
                        && !mArrayListCurrentSurvey.get(position).getStart_date().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyStartDate.setText(mArrayListCurrentSurvey.get(position).getStart_date());
                }

                if (mArrayListCurrentSurvey.get(position).getEnd_date() != null
                        && !mArrayListCurrentSurvey.get(position).getEnd_date().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyEndDate.setText(mArrayListCurrentSurvey.get(position).getEnd_date());
                }

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntentSurveyDetail = new Intent(mActivityVendorMain, ActivityVendorSurveyDetail.class);
                    startActivity(mIntentSurveyDetail);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mArrayListCurrentSurvey.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.raw_vendor_survey_list_item_textview_survey_id)
            TextView mTextViewSurveyId;

            @BindView(R.id.raw_vendor_survey_list_item_textview_survey_title)
            TextView mTextViewSurveyTitle;

            @BindView(R.id.raw_vendor_survey_list_item_textview_survey_category)
            TextView mTextViewSurveyCategory;

            @BindView(R.id.raw_vendor_survey_list_item_textview_survey_start_date)
            TextView mTextViewSurveyStartDate;

            @BindView(R.id.raw_vendor_survey_list_item_textview_survey_end_date)
            TextView mTextViewSurveyEndDate;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

    }

}
