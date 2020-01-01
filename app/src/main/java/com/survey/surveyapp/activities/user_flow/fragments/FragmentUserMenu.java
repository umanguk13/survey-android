package com.survey.surveyapp.activities.user_flow.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.ActivitySelectFlow;
import com.survey.surveyapp.activities.ActivitySplash;
import com.survey.surveyapp.helper.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.annotation.Id;

public class FragmentUserMenu extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    @BindView(R.id.fragment_user_menu_txt_logout)
    TextView mTextViewLogout;

    Utility mUtility;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUtility = new Utility(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_user_menu, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        //Logout From LinkedIn
//        LISessionManager.getInstance(getApplicationContext()).clearSession();


        return createView;
    }

    @OnClick(R.id.fragment_user_menu_txt_logout)
    public void onClickLogout() {

        mUtility.errorDialogWithTitleBothClicks(getResources().getString(R.string.text_menu_logout),
                getResources().getString(R.string.text_vendor_menu_logout_msg),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mUtility.clearAllPrefData();
                        Intent mIntentLogin = new Intent(getActivity(), ActivitySelectFlow.class);
                        getActivity().finishAffinity();
                        startActivity(mIntentLogin);

                    }
                }, getResources().getString(R.string.text_vendor_menu_logout_yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }, getResources().getString(R.string.text_vendor_menu_logout_cancel));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {

        }
    }

}
