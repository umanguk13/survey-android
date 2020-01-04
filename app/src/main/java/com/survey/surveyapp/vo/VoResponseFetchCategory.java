package com.survey.surveyapp.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class VoResponseFetchCategory implements Serializable {

    private ArrayList<VoResponseFetchCategoryData> list_category = new ArrayList<>();

    public ArrayList<VoResponseFetchCategoryData> getList_category() {
        return list_category;
    }

    public void setList_category(ArrayList<VoResponseFetchCategoryData> list_category) {
        this.list_category = list_category;
    }
}
