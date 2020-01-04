package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseCreateNewSurveyCriteria implements Serializable {

    private String id = "";
    private String survey_id = "";
    private String profession_id = "";
    private String start_age = "";
    private String end_age = "";
    private String location = "";
    private String group_id = "";
    private String is_active = "";
    private String created_by = "";
    private String updated_by = "";
    private String updatedAt = "";
    private String createdAt = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(String survey_id) {
        this.survey_id = survey_id;
    }

    public String getProfession_id() {
        return profession_id;
    }

    public void setProfession_id(String profession_id) {
        this.profession_id = profession_id;
    }

    public String getStart_age() {
        return start_age;
    }

    public void setStart_age(String start_age) {
        this.start_age = start_age;
    }

    public String getEnd_age() {
        return end_age;
    }

    public void setEnd_age(String end_age) {
        this.end_age = end_age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
