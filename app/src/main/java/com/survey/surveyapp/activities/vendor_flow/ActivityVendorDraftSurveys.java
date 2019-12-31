package com.survey.surveyapp.activities.vendor_flow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityVendorDraftSurveysBinding;
import com.survey.surveyapp.helper.DividerItemDecorationNavigation;

import butterknife.ButterKnife;

public class ActivityVendorDraftSurveys extends VendorBaseActivity {

    ActivityVendorDraftSurveysBinding mActivityVendorDraftSurveysBinding;

    DraftSurveysAdapter mDraftSurveysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityVendorDraftSurveysBinding = (ActivityVendorDraftSurveysBinding) putContentView(R.layout.activity_vendor_draft_surveys);

        initToolbar();
        mTextViewTitle.setText("Draft Surveys");

        mDraftSurveysAdapter = new DraftSurveysAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mActivityVendorDraftSurveysBinding.activityVendorDraftSurveysRecyclerview.addItemDecoration(new DividerItemDecorationNavigation(getApplicationContext(), DividerItemDecorationNavigation.VERTICAL_LIST));
        mActivityVendorDraftSurveysBinding.activityVendorDraftSurveysRecyclerview.setLayoutManager(mLinearLayoutManager);
        mActivityVendorDraftSurveysBinding.activityVendorDraftSurveysRecyclerview.setAdapter(mDraftSurveysAdapter);

    }

    public class DraftSurveysAdapter extends RecyclerView.Adapter<DraftSurveysAdapter.ViewHolder> {

        @NonNull
        @Override
        public DraftSurveysAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_vendor_draft_survey_list_item, parent, false);
            return new DraftSurveysAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull final DraftSurveysAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

    }

}
