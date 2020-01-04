package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoDataSurveyQuestions implements Serializable {

    private String survey_question = "";
    private String survey_answer_type = "";
    private String survey_option_a = "";
    private String survey_option_b = "";
    private String survey_option_c = "";
    private String survey_option_d = "";

    private boolean isCommentsNeeded = false;
    private String survey_comments = "";

    public String getSurvey_question() {
        return survey_question;
    }

    public void setSurvey_question(String survey_question) {
        this.survey_question = survey_question;
    }

    public String getSurvey_answer_type() {
        return survey_answer_type;
    }

    public void setSurvey_answer_type(String survey_answer_type) {
        this.survey_answer_type = survey_answer_type;
    }

    public String getSurvey_option_a() {
        return survey_option_a;
    }

    public void setSurvey_option_a(String survey_option_a) {
        this.survey_option_a = survey_option_a;
    }

    public String getSurvey_option_b() {
        return survey_option_b;
    }

    public void setSurvey_option_b(String survey_option_b) {
        this.survey_option_b = survey_option_b;
    }

    public String getSurvey_option_c() {
        return survey_option_c;
    }

    public void setSurvey_option_c(String survey_option_c) {
        this.survey_option_c = survey_option_c;
    }

    public String getSurvey_option_d() {
        return survey_option_d;
    }

    public void setSurvey_option_d(String survey_option_d) {
        this.survey_option_d = survey_option_d;
    }

    public String getSurvey_comments() {
        return survey_comments;
    }

    public void setSurvey_comments(String survey_comments) {
        this.survey_comments = survey_comments;
    }

    public boolean isCommentsNeeded() {
        return isCommentsNeeded;
    }

    public void setCommentsNeeded(boolean commentsNeeded) {
        isCommentsNeeded = commentsNeeded;
    }
}
