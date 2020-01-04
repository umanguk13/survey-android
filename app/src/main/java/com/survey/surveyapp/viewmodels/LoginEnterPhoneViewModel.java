package com.survey.surveyapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityLoginPhoneNumber;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponceCheckUserExist;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class LoginEnterPhoneViewModel extends ViewModel {

    private ActivityLoginPhoneNumber mActivityLoginPhoneNumber;
    private MyService mMyService;
    private Utility mUtility;

    public MutableLiveData<String> strPhoneNumber = new MutableLiveData<>();
    public MutableLiveData<Integer> isCountryCodeGet = new MutableLiveData<>();

    public LoginEnterPhoneViewModel(ActivityLoginPhoneNumber mActivityLoginPhoneNumber, MyService myService) {
        mActivityLoginPhoneNumber = mActivityLoginPhoneNumber;
        mUtility = new Utility(mActivityLoginPhoneNumber);
        mMyService = myService;
    }

    public String getPhoneNumber() {
        return (strPhoneNumber.getValue() != null ? strPhoneNumber.getValue().replaceAll(" ", "") : "");
    }

    public void checkUserExist() {

        JSONObject mJsonObjectUserExist = new JSONObject();
        try {
            mJsonObjectUserExist.put("phone", strPhoneNumber.getValue());
            mJsonObjectUserExist.put("role_id", mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.verifyUserPhone(mJsonObjectUserExist, new MyService.ServiceCallback<VoResponceCheckUserExist>() {
            @Override
            public void onSuccess(VoResponceCheckUserExist data) {
                //TODO Got Data Here
                if (data != null) {


                }
            }

            @Override
            public void onError(Throwable networkError) {
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
                    mUtility.errorDialog(mActivityLoginPhoneNumber.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityLoginPhoneNumber.getString(R.string.something_went_wrong_fix_soon));
                }
            }
        });
    }

}
