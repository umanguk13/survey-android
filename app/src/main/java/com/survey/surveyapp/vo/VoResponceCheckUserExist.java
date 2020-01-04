package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponceCheckUserExist implements Serializable {

    String is_username_exist = "";

    public String getIs_username_exist() {
        return is_username_exist;
    }

    public void setIs_username_exist(String is_username_exist) {
        this.is_username_exist = is_username_exist;
    }
}
