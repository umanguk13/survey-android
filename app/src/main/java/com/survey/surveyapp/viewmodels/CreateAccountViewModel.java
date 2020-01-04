package com.survey.surveyapp.viewmodels;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivityCreateAccount;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.helper.Utility;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponceCheckUserExist;
import com.survey.surveyapp.vo.VoResponseRegister;
import com.survey.surveyapp.vo.VoResponseSocialLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class CreateAccountViewModel extends ViewModel {

    private ActivityCreateAccount mActivityCreateAccount;
    private MyService mMyService;
    private Utility mUtility;

    public MutableLiveData<String> mStringUsername = new MutableLiveData<>();
    public MutableLiveData<String> mStringFirstName = new MutableLiveData<>();
    public MutableLiveData<String> mStringLastName = new MutableLiveData<>();
    public MutableLiveData<String> mStringEmail = new MutableLiveData<>();
    public MutableLiveData<String> mStringPassword = new MutableLiveData<>();
    public MutableLiveData<String> mStringConfirmPassword = new MutableLiveData<>();
    public MutableLiveData<String> mStringPhoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> mStringProfession = new MutableLiveData<>();
    public MutableLiveData<String> mStringAge = new MutableLiveData<>();
    public MutableLiveData<String> mStringAddress = new MutableLiveData<>();
    public MutableLiveData<Boolean> mBooleanUserExist = new MutableLiveData<>();

    public CreateAccountViewModel(ActivityCreateAccount mActivityCreateAccount, MyService myService) {

        this.mActivityCreateAccount = mActivityCreateAccount;
        mUtility = new Utility(this.mActivityCreateAccount);
        mMyService = myService;

        mStringUsername.setValue("");
        mStringFirstName.setValue("");
        mStringLastName.setValue("");
        mStringEmail.setValue("");
        mStringPassword.setValue("");
        mStringConfirmPassword.setValue("");
        mStringPhoneNumber.setValue("");
        mStringProfession.setValue("");
        mStringAge.setValue("");
        mStringAddress.setValue("");
    }

    public void checkUserExist(String strUserName) {

        JSONObject mJsonObjectUserExist = new JSONObject();
        try {
            mJsonObjectUserExist.put("username", strUserName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.checkUserExist(mJsonObjectUserExist, new MyService.ServiceCallback<VoResponceCheckUserExist>() {
            @Override
            public void onSuccess(VoResponceCheckUserExist data) {
                //TODO Got Data Here
                if (data != null) {
                    if (data.getIs_username_exist().equalsIgnoreCase("true")) {
                        mBooleanUserExist.setValue(true);
                    } else {
                        mBooleanUserExist.setValue(false);
                    }
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
                    mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
                }
            }
        });
    }

    public void doNormalRegister() {
        JSONObject mJsonObjectLogin = new JSONObject();

        try {
            mJsonObjectLogin.put("role_id", mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID));
            mJsonObjectLogin.put("first_name", mStringFirstName.getValue());
            mJsonObjectLogin.put("last_name", mStringLastName.getValue());
            mJsonObjectLogin.put("username", mStringUsername.getValue());
            mJsonObjectLogin.put("email", mStringEmail.getValue());
            mJsonObjectLogin.put("password", mStringPassword.getValue());
            mJsonObjectLogin.put("phone", mStringPhoneNumber.getValue());
            mJsonObjectLogin.put("profession", mStringProfession.getValue());
            mJsonObjectLogin.put("address", mStringAddress.getValue());
            mJsonObjectLogin.put("age", mStringAge.getValue());
            mJsonObjectLogin.put("country_id", "1");
            mJsonObjectLogin.put("state_id", "1");
            mJsonObjectLogin.put("city_id", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyService.doNormalRegister(mJsonObjectLogin, new MyService.ServiceCallback<VoResponseRegister>() {
            @Override
            public void onSuccess(VoResponseRegister data) {
                //TODO Got Data Here
                mUtility.hideAnimatedProgress();
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
                    mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
                }
            }
        });
    }

    public boolean isValid() {

        if (mStringUsername.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your username.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mBooleanUserExist.getValue()) {
            Toast.makeText(mActivityCreateAccount, "Username already available. Please enter new one.", Toast.LENGTH_LONG).show();
        }

        if (mStringFirstName.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your first name.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringLastName.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your last name.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringEmail.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your email address.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mUtility.isValidEmail(mStringEmail.getValue())) {
            Toast.makeText(mActivityCreateAccount, "Please enter valid email address.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringPassword.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your password.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringConfirmPassword.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your confirm password.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringConfirmPassword.getValue().equalsIgnoreCase(mStringPassword.getValue())) {
            Toast.makeText(mActivityCreateAccount, "Your confirm password not matching with password. Please enter correct password", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringPhoneNumber.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your phone number.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringProfession.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please select your profession.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mStringAddress.getValue().equalsIgnoreCase("")) {
            Toast.makeText(mActivityCreateAccount, "Please enter your address.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
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

                    if (data.getUser_details().getRole_id() != null) {
                        if (data.getUser_details().getRole_id().equalsIgnoreCase(TagValues.USER_ROLE_ID)) {
                            Intent mIntentLogin = new Intent(mActivityCreateAccount, ActivityUserMain.class);
                            mActivityCreateAccount.finishAffinity();
                            mActivityCreateAccount.startActivity(mIntentLogin);
                        } else {
                            Intent mIntentLogin = new Intent(mActivityCreateAccount, ActivityVendorMain.class);
                            mActivityCreateAccount.finishAffinity();
                            mActivityCreateAccount.startActivity(mIntentLogin);
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
                    mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
                } else {
                    mUtility.errorDialog(mActivityCreateAccount.getString(R.string.something_went_wrong_fix_soon));
                }

//                mUtility.errorDialog(mActivityLogin.getString(R.string.something_went_wrong_fix_soon));
            }
        });
    }

}
