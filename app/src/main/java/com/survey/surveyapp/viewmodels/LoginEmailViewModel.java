package com.survey.surveyapp.viewmodels;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityLoginEmail;
import com.survey.surveyapp.activities.ActivitySelectFlow;
import com.survey.surveyapp.activities.ActivitySplash;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class LoginEmailViewModel extends ViewModel {

    private ActivityLoginEmail mActivityLoginEmail;
    private MyService mMyService;
    private Utility mUtility;

    public MutableLiveData<String> mStringEmail = new MutableLiveData<>();
    public MutableLiveData<String> mStringPassword = new MutableLiveData<>();

    public LoginEmailViewModel(ActivityLoginEmail mActivityLoginEmail, MyService myService) {
        this.mActivityLoginEmail = mActivityLoginEmail;
        mUtility = new Utility(this.mActivityLoginEmail);
        mMyService = myService;

    }

    public void doNormalLogin() {
        mUtility.showAnimatedProgress(mActivityLoginEmail);

        JSONObject mJsonObjectLogin = new JSONObject();

        try {
            mJsonObjectLogin.put("username", mStringEmail.getValue());
            mJsonObjectLogin.put("password", mStringPassword.getValue());
            mJsonObjectLogin.put("role_id", mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID));
            mJsonObjectLogin.put("device_type", "1");
            mJsonObjectLogin.put("device_token", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.doNormalLogin(mJsonObjectLogin, new MyService.ServiceCallback<VoResponseLogin>() {
            @Override
            public void onSuccess(VoResponseLogin data) {

                System.out.println("Sanjay..." + new Gson().toJson(data));
                //TODO Got Data Here
                mUtility.hideAnimatedProgress();

                if (data != null) {
                    if (data.getFirst_name() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_FIRST_NAME, data.getFirst_name());

                    if (data.getLast_name() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_LAST_NAME, data.getLast_name());

                    if (data.getEmail() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_EMAIL, data.getEmail());

                    if (data.getPhone() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PHOME, data.getPhone());

                    if (data.getProfile_pic() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PROFILE_PIC, data.getFirst_name());

                    if (data.getProfession_id() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PROFESSION_ID, data.getProfession_id());

                    if (data.getId() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ID, data.getId());

                    if (data.getRole_id() != null) {
                        if (data.getRole_id().equalsIgnoreCase(TagValues.USER_ROLE_ID)) {
                            Intent mIntentLogin = new Intent(mActivityLoginEmail, ActivityUserMain.class);
                            mActivityLoginEmail.finishAffinity();
                            mActivityLoginEmail.startActivity(mIntentLogin);
                        } else {
                            Intent mIntentLogin = new Intent(mActivityLoginEmail, ActivityVendorMain.class);
                            mActivityLoginEmail.finishAffinity();
                            mActivityLoginEmail.startActivity(mIntentLogin);
                        }
                    }
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
                    mUtility.errorDialog(mActivityLoginEmail.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityLoginEmail.getString(R.string.something_went_wrong_fix_soon));
                }
            }
        });
    }

}
