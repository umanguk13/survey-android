package com.survey.surveyapp.viewmodels;

import android.widget.Toast;

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
import com.survey.surveyapp.activities.ActivityLoginVerifyOTP;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;

import java.util.concurrent.TimeUnit;

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

    public void sendVerificationCode(String stringCountryCode, String stringPhoneNumber) {
        isCountryCodeGet.setValue(Integer.parseInt(stringCountryCode));
        strPhoneNumber.setValue(stringPhoneNumber);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                stringCountryCode + stringPhoneNumber,
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    public void verifyVerificationCode(String code) {
        isLoginProgress = true;
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
            if (isLoginProgress) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
            }
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivityLoginVerifyOTP, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(mActivityLoginVerifyOTP, "SignIn Success!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
