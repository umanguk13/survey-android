package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseCheckUserExist implements Serializable {

    private String is_username_exist = "";
    private String user_exits = "";
    private VoResponseRegisterUserDetails user_data;

    public String getIs_username_exist() {
        return is_username_exist;
    }

    public void setIs_username_exist(String is_username_exist) {
        this.is_username_exist = is_username_exist;
    }

    public String getUser_exits() {
        return user_exits;
    }

    public void setUser_exits(String user_exits) {
        this.user_exits = user_exits;
    }

    public VoResponseRegisterUserDetails getUser_data() {
        return user_data;
    }

    public void setUser_data(VoResponseRegisterUserDetails user_data) {
        this.user_data = user_data;
    }
}
