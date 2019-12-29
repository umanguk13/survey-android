package com.survey.surveyapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityCreateAccount;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseRegister;

import org.json.JSONObject;

public class CreateAccountViewModel extends ViewModel {

    private ActivityCreateAccount mActivityCreateAccount;
    private MyService mMyService;
    private Utility mUtility;

    public CreateAccountViewModel(ActivityCreateAccount mActivityCreateAccount, MyService myService) {
        this.mActivityCreateAccount = mActivityCreateAccount;
        mUtility = new Utility(this.mActivityCreateAccount);
        mMyService = myService;
    }

    public void doNormalRegister() {
        JSONObject mJsonObjectLogin = new JSONObject();

        try {
            mJsonObjectLogin.put("role_id", "");
            mJsonObjectLogin.put("first_name", "");
            mJsonObjectLogin.put("last_name", "");
            mJsonObjectLogin.put("username", "");
            mJsonObjectLogin.put("email", "");
            mJsonObjectLogin.put("password", "");
            mJsonObjectLogin.put("phone", "");
            mJsonObjectLogin.put("profession", "");
            mJsonObjectLogin.put("address", "");
            mJsonObjectLogin.put("country_id", "");
            mJsonObjectLogin.put("state_id", "");
            mJsonObjectLogin.put("city_id", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.doNormalRegister(mJsonObjectLogin, new MyService.ServiceCallback<VoResponseRegister>() {
            @Override
            public void onSuccess(VoResponseRegister data) {
                //TODO Got Data Here
            }

            @Override
            public void onError(Exception networkError) {
                mUtility.hideAnimatedProgress();
                networkError.printStackTrace();
                mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
            }
        });
    }

}
