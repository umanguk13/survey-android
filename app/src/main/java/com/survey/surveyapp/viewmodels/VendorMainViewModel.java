package com.survey.surveyapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseCreateNewSurvey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import retrofit2.HttpException;

public class VendorMainViewModel extends ViewModel {

    private ActivityVendorMain mActivityVendorMain;
    private MyService mMyService;
    private Utility mUtility;

    public MutableLiveData<VoResponseCreateNewSurvey> mLiveDataCreateNewSurvey = new MutableLiveData<>();

    public VendorMainViewModel(ActivityVendorMain mActivityVendorMain, MyService myService) {
        this.mActivityVendorMain = mActivityVendorMain;
        mUtility = new Utility(this.mActivityVendorMain);
        mMyService = myService;
    }

    public void createNewSurvey(String mStringSelectedCategoryID,
                                String mStringSurveyTitle,
                                String mStringRequiredResponses,
                                String mStringStartDate,
                                String mStringEndDate,
                                String mStringSelectedProfession,
                                String mStringStartAge,
                                String mStringEndAge,
                                String mStringSelectedLocation,
                                String mStringSelectedGroup) {

        SimpleDateFormat mSimpleDateFormatSelected = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat mSimpleDateFormatPassing = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        try {
            mStringStartDate = mSimpleDateFormatPassing.format(Objects.requireNonNull(mSimpleDateFormatSelected.parse(mStringStartDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mStringEndDate = mSimpleDateFormatPassing.format(Objects.requireNonNull(mSimpleDateFormatSelected.parse(mStringEndDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mUtility.showAnimatedProgress(mActivityVendorMain);

        JSONObject mJsonObjectVerifyProduct = new JSONObject();

        try {
            mJsonObjectVerifyProduct.put("category_id", mStringSelectedCategoryID);
            mJsonObjectVerifyProduct.put("title", mStringSurveyTitle);
            mJsonObjectVerifyProduct.put("response", mStringRequiredResponses);
            mJsonObjectVerifyProduct.put("extra_point", "10");
            mJsonObjectVerifyProduct.put("start_date", mStringStartDate);
            mJsonObjectVerifyProduct.put("end_date", mStringEndDate);
            mJsonObjectVerifyProduct.put("promotion_text", "ABC");
            mJsonObjectVerifyProduct.put("profession_id", "1");
            mJsonObjectVerifyProduct.put("start_age", mStringStartAge);
            mJsonObjectVerifyProduct.put("end_age", mStringEndAge);
            mJsonObjectVerifyProduct.put("location", mStringSelectedLocation);
            mJsonObjectVerifyProduct.put("group_id", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.createNewSurvey(mUtility.getAppPrefString(TagValues.PREF_USER_ACCESS_TOKEN), mUtility.getAppPrefString(TagValues.PREF_USER_ID),
                mJsonObjectVerifyProduct, new MyService.ServiceCallback<VoResponseCreateNewSurvey>() {
                    @Override
                    public void onSuccess(VoResponseCreateNewSurvey data) {
                        mUtility.hideAnimatedProgress();

                        if (data != null && data.getSurvey_add_data() != null
                                && data.getSurvey_criteria_add_data() != null) {
                            mLiveDataCreateNewSurvey.setValue(data);
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
                            mUtility.errorDialog(mActivityVendorMain.getString(R.string.something_went_wrong_fix_soon));
                        } else {
                            mUtility.errorDialog(mActivityVendorMain.getString(R.string.something_went_wrong_fix_soon));
                        }
                    }
                });
    }

}
