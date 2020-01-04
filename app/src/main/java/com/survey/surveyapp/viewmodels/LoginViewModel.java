package com.survey.surveyapp.viewmodels;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityLogin;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseLogin;
import com.survey.surveyapp.vo.VoResponseSocialLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.HttpException;

public class LoginViewModel extends ViewModel {

    private BaseActivity mActivityLogin;
    private MyService mMyService;
    private Utility mUtility;

    public LiveData<ArrayList<String>> getData = new MutableLiveData<>();

    public LoginViewModel(BaseActivity mActivityLogin, MyService myService) {
        this.mActivityLogin = mActivityLogin;
        mUtility = new Utility(this.mActivityLogin);
        mMyService = myService;
    }

    public void doSocialLogin(String strFirstName, String strLastName, String strSocialID, String strSocialType, String strEmail, String strRoleID,
                              String strProfilePic) {
        JSONObject mJsonObjectLogin = new JSONObject();

        try {
            mJsonObjectLogin.put("first_name", strFirstName);
            mJsonObjectLogin.put("last_name", strLastName);
            mJsonObjectLogin.put("social_id", strSocialID);
            mJsonObjectLogin.put("social_type", strSocialType);
            mJsonObjectLogin.put("email", strEmail);
            mJsonObjectLogin.put("role_id", strRoleID);
            mJsonObjectLogin.put("profile_pic", strProfilePic);
            mJsonObjectLogin.put("device_type", "1");
            mJsonObjectLogin.put("device_token", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.doSocialLogin(mJsonObjectLogin, new MyService.ServiceCallback<VoResponseSocialLogin>() {
            @Override
            public void onSuccess(VoResponseSocialLogin data) {
                //TODO Got Data Here
                if (data != null && data.getUser_details() != null) {

                    if (data.getUser_details().getFirst_name() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_FIRST_NAME, data.getUser_details().getFirst_name());

                    if (data.getUser_details().getLast_name() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_LAST_NAME, data.getUser_details().getLast_name());

                    if (data.getUser_details().getEmail() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_EMAIL, data.getUser_details().getEmail());

                    if (data.getUser_details().getPhone() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PHOME, data.getUser_details().getPhone());

                    if (data.getUser_details().getProfile_pic() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PROFILE_PIC, data.getUser_details().getFirst_name());

                    if (data.getUser_details().getProfession_id() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PROFESSION_ID, data.getUser_details().getProfession_id());

                    if (data.getUser_details().getId() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ID, data.getUser_details().getId());

                    if (data.getUser_details().getAccess_token() != null)
                        mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ACCESS_TOKEN, data.getUser_details().getAccess_token());

                    if (data.getUser_details().getRole_id() != null) {
                        if (data.getUser_details().getRole_id().equalsIgnoreCase(TagValues.USER_ROLE_ID)) {
                            Intent mIntentLogin = new Intent(mActivityLogin, ActivityUserMain.class);
                            mActivityLogin.finishAffinity();
                            mActivityLogin.startActivity(mIntentLogin);
                        } else {
                            Intent mIntentLogin = new Intent(mActivityLogin, ActivityVendorMain.class);
                            mActivityLogin.finishAffinity();
                            mActivityLogin.startActivity(mIntentLogin);
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
                    mUtility.errorDialog(mActivityLogin.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityLogin.getString(R.string.something_went_wrong_fix_soon));
                }
            }
        });
    }

}
