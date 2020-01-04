package com.survey.surveyapp.vo;

import java.io.Serializable;

public class VoResponseCreateSurveyAddData implements Serializable {

    private String id = "";
    private String category_id = "";
    private String title = "";
    private String response = "";
    private String extra_point = "";
    private String start_date = "";
    private String end_date = "";
    private String promotion_text = "";
    private String is_active = "";
    private String created_by = "";
    private String updated_by = "";
    private String updatedAt = "";
    private String createdAt = "";
    private String banner = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getExtra_point() {
        return extra_point;
    }

    public void setExtra_point(String extra_point) {
        this.extra_point = extra_point;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPromotion_text() {
        return promotion_text;
    }

    public void setPromotion_text(String promotion_text) {
        this.promotion_text = promotion_text;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
