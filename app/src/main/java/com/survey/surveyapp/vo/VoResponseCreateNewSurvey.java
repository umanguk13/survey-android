package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseCreateNewSurvey implements Serializable {

    private VoResponseCreateSurveyAddData survey_add_data;
    private VoResponseCreateNewSurveyCriteria survey_criteria_add_data;

    public VoResponseCreateSurveyAddData getSurvey_add_data() {
        return survey_add_data;
    }

    public void setSurvey_add_data(VoResponseCreateSurveyAddData survey_add_data) {
        this.survey_add_data = survey_add_data;
    }

    public VoResponseCreateNewSurveyCriteria getSurvey_criteria_add_data() {
        return survey_criteria_add_data;
    }

    public void setSurvey_criteria_add_data(VoResponseCreateNewSurveyCriteria survey_criteria_add_data) {
        this.survey_criteria_add_data = survey_criteria_add_data;
    }
}
