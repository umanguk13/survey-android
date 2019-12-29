package com.survey.surveyapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceHelper {

    private final SharedPreferences mSharedPreferences;

    public PreferenceHelper(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String PREF_UserId = "UserId";
    private String PREF_AccessToken = "AccessToken";
    private String PREF_TokenType = "TokenType";

    public String getUserId() {
        String str = mSharedPreferences.getString(PREF_UserId, "");
        return str;
    }

    public void setUserId(String PREF_AccessToken) {
        Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(PREF_UserId, PREF_AccessToken);
        mEditor.commit();
    }

    public String getAccessToken() {
        String str = mSharedPreferences.getString(PREF_AccessToken, "");
        return str;
    }

    public void setAccessToken(String pref_AccessToken) {
        Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(PREF_AccessToken, pref_AccessToken);
        mEditor.commit();
    }

    public String getTokenType() {
        String str = mSharedPreferences.getString(PREF_TokenType, "");
        return str;
    }

    public void setTokenType(String pref_TokenType) {
        Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(PREF_TokenType, pref_TokenType);
        mEditor.commit();
    }

}
