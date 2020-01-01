package com.survey.surveyapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityLoginEmail;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseLogin;

import org.json.JSONObject;

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

//        mStringEmail.setValue("darshan.popat");
//        mStringPassword.setValue("123456");
    }

    public void doNormalLogin() {
        mUtility.showAnimatedProgress(mActivityLoginEmail);

        JSONObject mJsonObjectLogin = new JSONObject();

        try {
            mJsonObjectLogin.put("username", mStringEmail.getValue());
            mJsonObjectLogin.put("password", mStringPassword.getValue());
            mJsonObjectLogin.put("role_id", "2");
            mJsonObjectLogin.put("device_type", "1");
            mJsonObjectLogin.put("device_token", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.doNormalLogin(mJsonObjectLogin, new MyService.ServiceCallback<VoResponseLogin>() {
            @Override
            public void onSuccess(VoResponseLogin data) {
                //TODO Got Data Here
                mUtility.hideAnimatedProgress();
                mUtility.errorDialog("Login Success !!!");
            }

            @Override
            public void onError(Exception networkError) {
                mUtility.hideAnimatedProgress();
                networkError.printStackTrace();
                mUtility.errorDialog(mActivityLoginEmail.getString(R.string.something_went_wrong_fix_soon));
            }
        });
    }

}
