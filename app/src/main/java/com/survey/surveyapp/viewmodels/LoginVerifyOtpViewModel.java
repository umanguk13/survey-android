package com.survey.surveyapp.viewmodels;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityCreateAccount;
import com.survey.surveyapp.activities.ActivityLoginVerifyOTP;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseCheckUserExist;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.HttpException;

public class LoginVerifyOtpViewModel extends ViewModel {

    private ActivityLoginVerifyOTP mActivityLoginVerifyOTP;
    private MyService mMyService;
    private Utility mUtility;

    //Firebase
    private FirebaseAuth mAuth;
    private boolean isLoginProgress = false;
    private String mVerificationId;

    private MutableLiveData<String> strPhoneNumber = new MutableLiveData<>();
    private MutableLiveData<Integer> isCountryCodeGet = new MutableLiveData<>();

    public LoginVerifyOtpViewModel(ActivityLoginVerifyOTP mActivityLoginVerifyOTP, MyService myService) {
        this.mActivityLoginVerifyOTP = mActivityLoginVerifyOTP;
        mUtility = new Utility(mActivityLoginVerifyOTP);
        mMyService = myService;
        mAuth = FirebaseAuth.getInstance();
    }

    public void sendVerificationCode(String stringPhoneNumber) {
        strPhoneNumber.setValue(stringPhoneNumber);
        mUtility.showAnimatedProgress(mActivityLoginVerifyOTP);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Objects.requireNonNull(strPhoneNumber.getValue()),
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    public void verifyVerificationCode(String code) {
        mUtility.showAnimatedProgress(mActivityLoginVerifyOTP);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            if (isLoginProgress) {
                String code = phoneAuthCredential.getSmsCode();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            mUtility.hideAnimatedProgress();
            if (!e.getMessage().equalsIgnoreCase("1")) {
                mUtility.errorDialog(e.getMessage());
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            mUtility.hideAnimatedProgress();
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivityLoginVerifyOTP, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mUtility.hideAnimatedProgress();

                        if (mUtility.haveInternet()) {
                            checkUserExist();
                        }
                    }
                });
    }

    public void checkUserExist() {
        mUtility.showAnimatedProgress(mActivityLoginVerifyOTP);

        JSONObject mJsonObjectUserExist = new JSONObject();
        try {
            mJsonObjectUserExist.put("phone", strPhoneNumber.getValue());
            mJsonObjectUserExist.put("role_id", mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID));
            mJsonObjectUserExist.put("device_type", TagValues.DEVICE_TYPE);
            mJsonObjectUserExist.put("device_token", mUtility.getAppPrefString(TagValues.PREF_USER_DEVICE_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.verifyUserPhone(mJsonObjectUserExist, new MyService.ServiceCallback<VoResponseCheckUserExist>() {
            @Override
            public void onSuccess(VoResponseCheckUserExist data) {
                mUtility.hideAnimatedProgress();

                if (data != null) {
                    if (data.getUser_exits() != null
                            && !data.getUser_exits().equalsIgnoreCase("")) {
                        if (data.getUser_exits().equalsIgnoreCase("1")) {
                            if (data.getUser_data() != null) {

                                if (data.getUser_data().getFirst_name() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_FIRST_NAME, data.getUser_data().getFirst_name());

                                if (data.getUser_data().getLast_name() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_LAST_NAME, data.getUser_data().getLast_name());

                                if (data.getUser_data().getEmail() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_EMAIL, data.getUser_data().getEmail());

                                if (data.getUser_data().getPhone() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PHONE, data.getUser_data().getPhone());

                                if (data.getUser_data().getProfile_pic() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PROFILE_PIC, data.getUser_data().getFirst_name());

                                if (data.getUser_data().getProfession_id() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_PROFESSION_ID, data.getUser_data().getProfession_id());

                                if (data.getUser_data().getId() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ID, data.getUser_data().getId());

                                if (data.getUser_data().getAccess_token() != null)
                                    mUtility.writeSharedPreferencesString(TagValues.PREF_USER_ACCESS_TOKEN, data.getUser_data().getAccess_token());

                                if (data.getUser_data().getRole_id() != null) {
                                    if (data.getUser_data().getRole_id().equalsIgnoreCase(TagValues.USER_ROLE_ID)) {
                                        Intent mIntentLogin = new Intent(mActivityLoginVerifyOTP, ActivityUserMain.class);
                                        mActivityLoginVerifyOTP.finishAffinity();
                                        mActivityLoginVerifyOTP.startActivity(mIntentLogin);
                                    } else {
                                        Intent mIntentLogin = new Intent(mActivityLoginVerifyOTP, ActivityVendorMain.class);
                                        mActivityLoginVerifyOTP.finishAffinity();
                                        mActivityLoginVerifyOTP.startActivity(mIntentLogin);
                                    }
                                }

                            }
                        } else {
                            Intent mIntentCreateAccount = new Intent(mActivityLoginVerifyOTP, ActivityCreateAccount.class);
                            mIntentCreateAccount.putExtra("phone_number", strPhoneNumber.getValue());
                            mIntentCreateAccount.putExtra("isFromPhoneNumber", true);
                            mActivityLoginVerifyOTP.finishAffinity();
                            mActivityLoginVerifyOTP.startActivity(mIntentCreateAccount);
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
                    mUtility.errorDialog(mActivityLoginVerifyOTP.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityLoginVerifyOTP.getString(R.string.something_went_wrong_fix_soon));
                }
            }
        });
    }

}
