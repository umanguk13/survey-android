package com.survey.surveyapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityLogin;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseLogin;
import com.survey.surveyapp.vo.VoResponseSocialLogin;

import org.json.JSONObject;

public class LoginViewModel extends ViewModel {

    private ActivityLogin mActivityLogin;
    private MyService mMyService;
    private Utility mUtility;

    public LoginViewModel(ActivityLogin mActivityLogin, MyService myService) {
        this.mActivityLogin = mActivityLogin;
        mUtility = new Utility(this.mActivityLogin);
        mMyService = myService;
    }

    public void doSocialLogin() {
        JSONObject mJsonObjectLogin = new JSONObject();

        try {
            mJsonObjectLogin.put("first_name", "");
            mJsonObjectLogin.put("last_name", "");
            mJsonObjectLogin.put("social_id", "");
            mJsonObjectLogin.put("social_type", "");
            mJsonObjectLogin.put("email", "");
            mJsonObjectLogin.put("role_id", "");
            mJsonObjectLogin.put("profile_pic", "");
            mJsonObjectLogin.put("device_type", "");
            mJsonObjectLogin.put("device_token", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.doSocialLogin(mJsonObjectLogin, new MyService.ServiceCallback<VoResponseSocialLogin>() {
            @Override
            public void onSuccess(VoResponseSocialLogin data) {
                //TODO Got Data Here
            }

            @Override
            public void onError(Exception networkError) {
                mUtility.hideAnimatedProgress();
                networkError.printStackTrace();
                mUtility.errorDialog(mActivityLogin.getString(R.string.something_went_wrong_fix_soon));
            }
        });
    }

}
