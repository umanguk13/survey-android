package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseAddSurveyQuestions implements Serializable {

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
