package com.survey.surveyapp.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class VoResponseCurrentSurvey implements Serializable {

    private ArrayList<VoResponseCurrentSurveyData> survey_list = new ArrayList<>();

    public ArrayList<VoResponseCurrentSurveyData> getSurvey_list() {
        return survey_list;
    }

    public void setSurvey_list(ArrayList<VoResponseCurrentSurveyData> survey_list) {
        this.survey_list = survey_list;
    }
}
