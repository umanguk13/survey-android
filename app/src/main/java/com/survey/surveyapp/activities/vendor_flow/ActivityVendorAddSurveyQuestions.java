package com.survey.surveyapp.activities.vendor_flow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityVendorAddSurveyQuestionsBinding;
import com.survey.surveyapp.helper.DividerItemDecorationNavigation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityVendorAddSurveyQuestions extends VendorBaseActivity {

    ActivityVendorAddSurveyQuestionsBinding mActivityVendorAddSurveyQuestionsBinding;

    AddSurveyQuestionsAdapter mAddSurveyQuestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityVendorAddSurveyQuestionsBinding = (ActivityVendorAddSurveyQuestionsBinding) putContentView(R.layout.activity_vendor_add_survey_questions);

        initToolbar();
        mTextViewTitle.setText("Add Survey Question");

        mAddSurveyQuestionsAdapter = new AddSurveyQuestionsAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ActivityVendorAddSurveyQuestions.this, RecyclerView.VERTICAL, false);
        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionsRecyclerview.setLayoutManager(mLinearLayoutManager);
        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionsRecyclerview.addItemDecoration(new DividerItemDecorationNavigation(getApplicationContext(), DividerItemDecorationNavigation.VERTICAL_LIST));
        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionsRecyclerview.setAdapter(mAddSurveyQuestionsAdapter);

        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ActivityVendorAddSurveyQuestions.this, ActivityVendorDraftSurveys.class);
                startActivity(mIntent);
            }
        });

    }

    public class AddSurveyQuestionsAdapter extends RecyclerView.Adapter<AddSurveyQuestionsAdapter.ViewHolder> {
        @androidx.annotation.NonNull
        @Override
        public AddSurveyQuestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_vendor_add_survey_question_item, parent, false);
            return new AddSurveyQuestionsAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull final AddSurveyQuestionsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.mTextViewQuestionNumber.setText(getResources().getString(R.string.text_add_questions_title, String.valueOf(position + 1)));

        }

        @Override
        public int getItemCount() {
            return 7;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.raw_vendor_add_survey_question_item_textview_question_number)
            TextView mTextViewQuestionNumber;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}
