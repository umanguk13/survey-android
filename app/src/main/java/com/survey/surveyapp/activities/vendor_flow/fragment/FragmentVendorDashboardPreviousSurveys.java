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
import com.survey.surveyapp.vo.VoResponsePreviousSurvey;
import com.survey.surveyapp.vo.VoResponsePreviousSurveyData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentVendorDashboardPreviousSurveys extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    ActivityVendorMain mActivityVendorMain;

    @BindView(R.id.fragment_vendor_dashboard_previous_surveys_recyclerview)
    RecyclerView mRecyclerViewTrendingSurveys;

    //Adapter
    PreviousSurveysAdapter mPreviousSurveysAdapter;

    //Data
    ArrayList<VoResponsePreviousSurveyData> mArrayListPreviousSurvey = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityVendorMain = (ActivityVendorMain) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_dashboard_previou_surveys, container, false);
        _isLoaded = true;
        ButterKnife.bind(this, createView);

//        if (mPreviousSurveysAdapter != null) {
//            mPreviousSurveysAdapter.notifyDataSetChanged();
//        } else {
        mPreviousSurveysAdapter = new PreviousSurveysAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerViewTrendingSurveys.addItemDecoration(new DividerItemDecorationNavigation(getActivity(), DividerItemDecorationNavigation.VERTICAL_LIST));
        mRecyclerViewTrendingSurveys.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewTrendingSurveys.setAdapter(mPreviousSurveysAdapter);
//        }

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {

            if (mActivityVendorMain.mUtility.haveInternet()) {
                mActivityVendorMain.mVendorMainViewModel.fetchPreviousSurvey();
            }

        }
    }

    public void gotServiceResponse(VoResponsePreviousSurvey voResponsePreviousSurvey) {
        if (voResponsePreviousSurvey != null && voResponsePreviousSurvey.getSurvey_list() != null
                && voResponsePreviousSurvey.getSurvey_list().size() > 0) {
            mArrayListPreviousSurvey.clear();
            mArrayListPreviousSurvey.addAll(voResponsePreviousSurvey.getSurvey_list());

            mPreviousSurveysAdapter = new PreviousSurveysAdapter();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            mRecyclerViewTrendingSurveys.addItemDecoration(new DividerItemDecorationNavigation(getActivity(), DividerItemDecorationNavigation.VERTICAL_LIST));
            mRecyclerViewTrendingSurveys.setLayoutManager(mLinearLayoutManager);
            mRecyclerViewTrendingSurveys.setAdapter(mPreviousSurveysAdapter);

        }
    }

    public class PreviousSurveysAdapter extends RecyclerView.Adapter<PreviousSurveysAdapter.ViewHolder> {

        @NonNull
        @Override
        public PreviousSurveysAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_vendor_survey_list_item, parent, false);
            return new PreviousSurveysAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull final PreviousSurveysAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            if (mArrayListPreviousSurvey.get(position) != null) {

                if (mArrayListPreviousSurvey.get(position).getId() != null
                        && !mArrayListPreviousSurvey.get(position).getId().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyId.setText(mArrayListPreviousSurvey.get(position).getId());
                }

                if (mArrayListPreviousSurvey.get(position).getTitle() != null
                        && !mArrayListPreviousSurvey.get(position).getTitle().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyTitle.setText(mArrayListPreviousSurvey.get(position).getTitle());
                }

//                if (mArrayListPreviousSurvey.get(position).getSurvey_category() != null
//                        && mArrayListPreviousSurvey.get(position).getSurvey_category() != null
//                        && mArrayListPreviousSurvey.get(position).getSurvey_category().getName() != null
//                        && !mArrayListPreviousSurvey.get(position).getSurvey_category().getName().equalsIgnoreCase("")) {
//                    holder.mTextViewSurveyCategory.setText(mArrayListPreviousSurvey.get(position).getSurvey_category().getName());
//                }

                if (mArrayListPreviousSurvey.get(position).getStart_date() != null
                        && !mArrayListPreviousSurvey.get(position).getStart_date().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyStartDate.setText(mArrayListPreviousSurvey.get(position).getStart_date());
                }

                if (mArrayListPreviousSurvey.get(position).getEnd_date() != null
                        && !mArrayListPreviousSurvey.get(position).getEnd_date().equalsIgnoreCase("")) {
                    holder.mTextViewSurveyEndDate.setText(mArrayListPreviousSurvey.get(position).getEnd_date());
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
            return mArrayListPreviousSurvey.size();
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
