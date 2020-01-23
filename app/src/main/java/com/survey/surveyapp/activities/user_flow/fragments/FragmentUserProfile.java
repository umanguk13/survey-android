package com.survey.surveyapp.activities.user_flow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.ActivityUserMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseFetchUserProfile;
import com.survey.surveyapp.vo.VoResponseFetchUserProfileData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.HttpException;

public class FragmentUserProfile extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    private ActivityUserMain mActivityUserMain;

    private SimpleDateFormat mSimpleDateFormatFetch = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    private SimpleDateFormat mSimpleDateFormatPrint = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @BindView(R.id.fragment_user_profile_textview_user_name)
    TextView mTextViewUsername;

    @BindView(R.id.fragment_user_profile_textview_member_since)
    TextView mTextViewMemberSince;

    @BindView(R.id.fragment_user_profile_edittext_username)
    TextInputEditText mEditTextUsername;

    @BindView(R.id.fragment_user_profile_edittext_user_email)
    TextInputEditText mEditTextUserEmail;

    @BindView(R.id.fragment_user_profile_edittext_gender)
    TextInputEditText mEditTextUserGender;

    @BindView(R.id.fragment_user_profile_edittext_phone_number)
    TextInputEditText mEditTextUserPhoneNumber;

    @BindView(R.id.fragment_user_profile_edittext_address)
    TextInputEditText mEditTextUserAddress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUserMain = (ActivityUserMain) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {
            if (mActivityUserMain.mUtility.haveInternet()) {
                fetchUserProfile();
            }
        }
    }

    private void setUserData(VoResponseFetchUserProfileData voResponseFetchUserProfileData) {
        if (voResponseFetchUserProfileData != null) {

            if (voResponseFetchUserProfileData.getFirst_name() != null
                    && !voResponseFetchUserProfileData.getFirst_name().equalsIgnoreCase("")) {
                if (voResponseFetchUserProfileData.getLast_name() != null
                        && !voResponseFetchUserProfileData.getLast_name().equalsIgnoreCase("")) {
                    mTextViewUsername.setText(getResources().getString(R.string.text_user_profile_username,
                            voResponseFetchUserProfileData.getFirst_name(), voResponseFetchUserProfileData.getLast_name()));
                } else {
                    mTextViewUsername.setText(voResponseFetchUserProfileData.getLast_name());
                }
            }

            if (voResponseFetchUserProfileData.getCreatedAt() != null
                    && !voResponseFetchUserProfileData.getCreatedAt().equalsIgnoreCase("")) {
                try {
                    Date mDate = mSimpleDateFormatFetch.parse(voResponseFetchUserProfileData.getCreatedAt());
                    mTextViewMemberSince.setText(getResources().getString(R.string.text_vendor_dashboard_survey_member_since,
                            mSimpleDateFormatPrint.format(mDate)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (voResponseFetchUserProfileData.getUsername() != null
                    && !voResponseFetchUserProfileData.getUsername().equalsIgnoreCase("")) {
                mEditTextUsername.setText(voResponseFetchUserProfileData.getUsername());
            }

            if (voResponseFetchUserProfileData.getEmail() != null
                    && !voResponseFetchUserProfileData.getEmail().equalsIgnoreCase("")) {
                mEditTextUserEmail.setText(voResponseFetchUserProfileData.getEmail());
            }

            if (voResponseFetchUserProfileData.getGender() != null
                    && !voResponseFetchUserProfileData.getGender().equalsIgnoreCase("")) {
                mEditTextUserGender.setText(voResponseFetchUserProfileData.getGender());
            }

            if (voResponseFetchUserProfileData.getPhone() != null
                    && !voResponseFetchUserProfileData.getPhone().equalsIgnoreCase("")) {
                mEditTextUserPhoneNumber.setText(voResponseFetchUserProfileData.getPhone());
            }

            if (voResponseFetchUserProfileData.getAddress() != null
                    && !voResponseFetchUserProfileData.getAddress().equalsIgnoreCase("")) {
                mEditTextUserAddress.setText(voResponseFetchUserProfileData.getAddress());
            }

        }
    }

    private void fetchUserProfile() {
        mActivityUserMain.mUtility.showAnimatedProgress(mActivityUserMain);

        mActivityUserMain.mMyService.fetchUserProfile(mActivityUserMain.mUtility.getAppPrefString(TagValues.PREF_USER_ACCESS_TOKEN),
                mActivityUserMain.mUtility.getAppPrefString(TagValues.PREF_USER_ID), new MyService.ServiceCallback<VoResponseFetchUserProfile>() {
                    @Override
                    public void onSuccess(VoResponseFetchUserProfile data) {
                        mActivityUserMain.mUtility.hideAnimatedProgress();

                        if (data != null && data.getUser_data() != null) {
                            setUserData(data.getUser_data());
                        }
                    }

                    @Override
                    public void onError(Throwable networkError) {
                        mActivityUserMain.mUtility.hideAnimatedProgress();
                        networkError.printStackTrace();

                        if (networkError instanceof HttpException) {
                            try {
                                int statusCode = ((HttpException) networkError).code();
                                JSONObject mJsonObjectMsg = new JSONObject(((HttpException) networkError).response().errorBody().string());
                                mActivityUserMain.mUtility.errorDialog(mJsonObjectMsg.optString("message"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else if (networkError instanceof SocketTimeoutException) {
                            mActivityUserMain.mUtility.errorDialog(mActivityUserMain.getString(R.string.something_went_wrong_fix_soon));
                        } else {
                            mActivityUserMain.mUtility.errorDialog(mActivityUserMain.getString(R.string.something_went_wrong_fix_soon));
                        }
                    }
                });

    }

}
