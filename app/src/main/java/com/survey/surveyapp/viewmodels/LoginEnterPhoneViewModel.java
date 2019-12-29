package com.survey.surveyapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.survey.surveyapp.activities.ActivityLoginPhoneNumber;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;

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

}
