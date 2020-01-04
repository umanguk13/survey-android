package com.survey.surveyapp.activities.vendor_flow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityVendorAddSurveyQuestionsBinding;
import com.survey.surveyapp.helper.DividerItemDecorationNavigation;
import com.survey.surveyapp.viewmodels.AddSurveyQuestionsViewModel;
import com.survey.surveyapp.vo.VoDataSurveyQuestions;
import com.survey.surveyapp.vo.VoResponseCreateNewSurvey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityVendorAddSurveyQuestions extends VendorBaseActivity {

    ActivityVendorAddSurveyQuestionsBinding mActivityVendorAddSurveyQuestionsBinding;

    AddSurveyQuestionsViewModel mAddSurveyQuestionsViewModel;

    AddSurveyQuestionsAdapter mAddSurveyQuestionsAdapter;

    VoResponseCreateNewSurvey mVoResponseCreateNewSurvey;

    ArrayList<VoDataSurveyQuestions> mArrayListSurveyQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityVendorAddSurveyQuestionsBinding = (ActivityVendorAddSurveyQuestionsBinding) putContentView(R.layout.activity_vendor_add_survey_questions);

        mAddSurveyQuestionsViewModel = new AddSurveyQuestionsViewModel(this, mMyService);
        mActivityVendorAddSurveyQuestionsBinding.setAddSurveyQuestionsViewModel(mAddSurveyQuestionsViewModel);
        mActivityVendorAddSurveyQuestionsBinding.setLifecycleOwner(this);

        initToolbar();
        mTextViewTitle.setText("Add Survey Question");

        if (getIntent().hasExtra("mVoResponseCreateNewSurvey")) {
            mVoResponseCreateNewSurvey = (VoResponseCreateNewSurvey) getIntent().getSerializableExtra("mVoResponseCreateNewSurvey");
        }

        for (int i = 0; i < 7; i++) {
            VoDataSurveyQuestions mVoDataSurveyQuestions = new VoDataSurveyQuestions();
            mArrayListSurveyQuestions.add(mVoDataSurveyQuestions);
        }

        mAddSurveyQuestionsAdapter = new AddSurveyQuestionsAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ActivityVendorAddSurveyQuestions.this, RecyclerView.VERTICAL, false);
        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionsRecyclerview.setLayoutManager(mLinearLayoutManager);
        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionsRecyclerview.addItemDecoration(new DividerItemDecorationNavigation(getApplicationContext(), DividerItemDecorationNavigation.VERTICAL_LIST));
        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionsRecyclerview.setAdapter(mAddSurveyQuestionsAdapter);

        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray mJsonArraySurveyQuestions = new JSONArray();
                for (int i = 0; i < mArrayListSurveyQuestions.size(); i++) {
                    try {
                        JSONObject mJsonObjectSurveyQuestion = new JSONObject();
                        JSONArray mJsonArraySurveyAnswer = new JSONArray();

                        mJsonObjectSurveyQuestion.put("survey_que", mArrayListSurveyQuestions.get(i).getSurvey_question());
                        mJsonObjectSurveyQuestion.put("survey_option_type", mArrayListSurveyQuestions.get(i).getSurvey_answer_type());

                        JSONObject mJsonObjectSurveyAnswer1 = new JSONObject();
                        mJsonObjectSurveyAnswer1.put("answer", mArrayListSurveyQuestions.get(i).getSurvey_option_a());

                        JSONObject mJsonObjectSurveyAnswer2 = new JSONObject();
                        mJsonObjectSurveyAnswer2.put("answer", mArrayListSurveyQuestions.get(i).getSurvey_option_b());

                        JSONObject mJsonObjectSurveyAnswer3 = new JSONObject();
                        mJsonObjectSurveyAnswer3.put("answer", mArrayListSurveyQuestions.get(i).getSurvey_option_c());

                        JSONObject mJsonObjectSurveyAnswer4 = new JSONObject();
                        mJsonObjectSurveyAnswer4.put("answer", mArrayListSurveyQuestions.get(i).getSurvey_option_d());

                        mJsonArraySurveyAnswer.put(mJsonObjectSurveyAnswer1);
                        mJsonArraySurveyAnswer.put(mJsonObjectSurveyAnswer2);
                        mJsonArraySurveyAnswer.put(mJsonObjectSurveyAnswer3);
                        mJsonArraySurveyAnswer.put(mJsonObjectSurveyAnswer4);

                        mJsonObjectSurveyQuestion.put("survey_answers", mJsonArraySurveyAnswer);

                        mJsonArraySurveyQuestions.put(mJsonObjectSurveyQuestion);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (mUtility.haveInternet()) {
                    mAddSurveyQuestionsViewModel.addSurveyQuestions("dde324cf-a72f-4153-b76f-4798dc63a671", mJsonArraySurveyQuestions);
                } else {
                    mUtility.errorDialog(getResources().getString(R.string.internet_error));
                }
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

            holder.mEditTextSurveyQuestion.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mArrayListSurveyQuestions.get(position).setSurvey_question(holder.mEditTextSurveyQuestion.getText().toString().trim());
                    isValid();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.mEditTextSurveyChoiceA.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mArrayListSurveyQuestions.get(position).setSurvey_option_a(holder.mEditTextSurveyChoiceA.getText().toString().trim());
                    isValid();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.mEditTextSurveyChoiceB.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mArrayListSurveyQuestions.get(position).setSurvey_option_b(holder.mEditTextSurveyChoiceB.getText().toString().trim());
                    isValid();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.mEditTextSurveyChoiceC.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mArrayListSurveyQuestions.get(position).setSurvey_option_c(holder.mEditTextSurveyChoiceC.getText().toString().trim());
                    isValid();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.mEditTextSurveyChoiceD.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mArrayListSurveyQuestions.get(position).setSurvey_option_d(holder.mEditTextSurveyChoiceD.getText().toString().trim());
                    isValid();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.mCheckBoxUserComments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mArrayListSurveyQuestions.get(position).setCommentsNeeded(isChecked);

                    if (!isChecked) {
                        holder.mEditTextSurveyUserComments.setText("");
                    }
                }
            });

            holder.mEditTextSurveyUserComments.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mArrayListSurveyQuestions.get(position).setSurvey_comments(holder.mEditTextSurveyUserComments.getText().toString().trim());
                    isValid();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.mRadioGroupSurveyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.raw_vendor_add_survey_question_item_radiobutton_multi_choice) {
                        mArrayListSurveyQuestions.get(position).setSurvey_answer_type("1");
                    } else if (checkedId == R.id.raw_vendor_add_survey_question_item_radiobutton_single_choice) {
                        mArrayListSurveyQuestions.get(position).setSurvey_answer_type("2");
                    }

                    isValid();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mArrayListSurveyQuestions.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.raw_vendor_add_survey_question_item_textview_question_number)
            TextView mTextViewQuestionNumber;

            @BindView(R.id.raw_vendor_add_survey_question_item_edittext_choose_survey_title)
            TextInputEditText mEditTextSurveyQuestion;

            @BindView(R.id.raw_vendor_add_survey_question_item_edittext_choice_a)
            TextInputEditText mEditTextSurveyChoiceA;

            @BindView(R.id.raw_vendor_add_survey_question_item_edittext_choice_b)
            TextInputEditText mEditTextSurveyChoiceB;

            @BindView(R.id.raw_vendor_add_survey_question_item_edittext_choice_c)
            TextInputEditText mEditTextSurveyChoiceC;

            @BindView(R.id.raw_vendor_add_survey_question_item_edittext_choice_d)
            TextInputEditText mEditTextSurveyChoiceD;

            @BindView(R.id.raw_vendor_add_survey_question_item_checkbox_comments)
            AppCompatCheckBox mCheckBoxUserComments;

            @BindView(R.id.raw_vendor_add_survey_question_item_edittext_user_comments)
            TextInputEditText mEditTextSurveyUserComments;

            @BindView(R.id.raw_vendor_add_survey_question_item_radiogroup_question_type)
            RadioGroup mRadioGroupSurveyType;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    public void isValid() {
        boolean isValid = true;

        if (mArrayListSurveyQuestions != null && mArrayListSurveyQuestions.size() > 0) {
            for (int i = 0; i < mArrayListSurveyQuestions.size(); i++) {
                if (mArrayListSurveyQuestions.get(i).getSurvey_question().equalsIgnoreCase("")
                        || mArrayListSurveyQuestions.get(i).getSurvey_answer_type().equalsIgnoreCase("")
                        || mArrayListSurveyQuestions.get(i).getSurvey_option_a().equalsIgnoreCase("")
                        || mArrayListSurveyQuestions.get(i).getSurvey_option_b().equalsIgnoreCase("")
                        || mArrayListSurveyQuestions.get(i).getSurvey_option_c().equalsIgnoreCase("")
                        || mArrayListSurveyQuestions.get(i).getSurvey_option_d().equalsIgnoreCase("")) {
                    isValid = false;
                    break;
                }
            }
        } else {
            isValid = false;
        }

        if (isValid) {
            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostLater.setClickable(true);
            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostLater.setEnabled(true);

            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostNow.setClickable(true);
            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostNow.setEnabled(true);
        } else {
            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostLater.setClickable(false);
            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostLater.setEnabled(false);

            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostNow.setClickable(false);
            mActivityVendorAddSurveyQuestionsBinding.activityVendorAddSurveyQuestionTextviewPostNow.setEnabled(false);
        }
    }

}
