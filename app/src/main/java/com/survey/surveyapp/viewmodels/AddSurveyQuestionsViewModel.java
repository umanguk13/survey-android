package com.survey.surveyapp.viewmodels;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorAddSurveyQuestions;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseAddSurveyQuestions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class AddSurveyQuestionsViewModel extends ViewModel {

    private ActivityVendorAddSurveyQuestions mActivityVendorAddSurveyQuestions;
    private MyService mMyService;
    private Utility mUtility;

    public AddSurveyQuestionsViewModel(ActivityVendorAddSurveyQuestions mActivityVendorAddSurveyQuestions, MyService myService) {
        this.mActivityVendorAddSurveyQuestions = mActivityVendorAddSurveyQuestions;
        mUtility = new Utility(this.mActivityVendorAddSurveyQuestions);
        mMyService = myService;
    }

    public void addSurveyQuestions(String mStringSurveyId, JSONArray mJsonArraySurveyQuestions) {
        mUtility.showAnimatedProgress(mActivityVendorAddSurveyQuestions);

        JSONObject mJsonObjectAddSurveyQuestion = new JSONObject();

        try {
            mJsonObjectAddSurveyQuestion.put("survey_id", mStringSurveyId);
            mJsonObjectAddSurveyQuestion.put("survey_questions", mJsonArraySurveyQuestions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.addSurveyQuestions(mUtility.getAppPrefString(TagValues.PREF_USER_ACCESS_TOKEN), mUtility.getAppPrefString(TagValues.PREF_USER_ID),
                mJsonObjectAddSurveyQuestion, new MyService.ServiceCallback<VoResponseAddSurveyQuestions>() {
                    @Override
                    public void onSuccess(VoResponseAddSurveyQuestions data) {
                        mUtility.hideAnimatedProgress();

                        if (data != null
                                && data.getMessage() != null
                                && !data.getMessage().equalsIgnoreCase("")) {
                            Toast.makeText(mActivityVendorAddSurveyQuestions, data.getMessage(), Toast.LENGTH_SHORT).show();

                            Intent mIntentDashboard = new Intent(mActivityVendorAddSurveyQuestions, ActivityVendorMain.class);
                            mActivityVendorAddSurveyQuestions.finishAffinity();
                            mActivityVendorAddSurveyQuestions.startActivity(mIntentDashboard);
                        }

                    }

                    @Override
                    public void onError(Throwable networkError) {
                        mUtility.hideAnimatedProgress();
                        networkError.printStackTrace();

                        if (networkError instanceof HttpException) {
                            try {
                                int statusCode = ((HttpException) networkError).code();
                                JSONObject mJsonObjectMsg = new JSONObject(((HttpException) networkError).response().errorBody().string());
                                mUtility.errorDialog(mJsonObjectMsg.optString("message"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else if (networkError instanceof SocketTimeoutException) {
                            mUtility.errorDialog(mActivityVendorAddSurveyQuestions.getString(R.string.something_went_wrong_fix_soon));
                        } else {
                            mUtility.errorDialog(mActivityVendorAddSurveyQuestions.getString(R.string.something_went_wrong_fix_soon));
                        }
                    }
                });

    }

}
