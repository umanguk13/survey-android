package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseSocialLogin implements Serializable {

    private VoResponseSocialLoginUserData user_details;

    public VoResponseSocialLoginUserData getUser_details() {
        return user_details;
    }

    public void setUser_details(VoResponseSocialLoginUserData user_details) {
        this.user_details = user_details;
    }
}
