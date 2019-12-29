package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseRegister implements Serializable {

    private VoResponseRegisterUserDetails user_details;

    public VoResponseRegisterUserDetails getUser_details() {
        return user_details;
    }

    public void setUser_details(VoResponseRegisterUserDetails user_details) {
        this.user_details = user_details;
    }
}
