package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseFetchUserProfile implements Serializable {

    private VoResponseFetchUserProfileData user_data;

    public VoResponseFetchUserProfileData getUser_data() {
        return user_data;
    }

    public void setUser_data(VoResponseFetchUserProfileData user_data) {
        this.user_data = user_data;
    }
}
