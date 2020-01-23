package com.survey.surveyapp.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class VoResponsePreviousSurvey implements Serializable {

    private ArrayList<VoResponsePreviousSurveyData> survey_list = new ArrayList<>();

    public ArrayList<VoResponsePreviousSurveyData> getSurvey_list() {
        return survey_list;
    }

    public void setSurvey_list(ArrayList<VoResponsePreviousSurveyData> survey_list) {
        this.survey_list = survey_list;
    }
}
