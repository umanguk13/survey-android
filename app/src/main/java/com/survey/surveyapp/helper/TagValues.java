package com.survey.surveyapp.helper;


public class TagValues {

    public static String MainURL = "http://13.232.222.229:3000/";

    public static final String PREFS_NAME = "SurveyApp_Preferences";

    public static String GOOGLE_CLIENT_ID = "718413996139-9ht1m8061inq3n88bar9dcs2gapgdukb.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "wgyUIIz9fxn8WjEIiH67TEXl";

    //Predefined Data
    public static final String ADMIN_ROLE_ID = "1";
    public static final String VENDOR_ROLE_ID = "2";
    public static final String USER_ROLE_ID = "3";

    public static final String SOCIAL_FACEBOOK_ID = "1";
    public static final String SOCIAL_LINKEDIN_ID = "2";
    public static final String SOCIAL_INSTAGRAM_ID = "3";
    public static final String SOCIAL_GOOGLE_PLUS_ID = "4";
    public static final String SOCIAL_TWITTER_ID = "5";
    public static final String SOCIAL_PHONE_NUMBER_ID = "6";

    public static final String DEVICE_TYPE = "1";

    //Shared Preferences
    public static final String PREF_SELECTED_ROLE_ID = "PREF_SELECTED_ROLE_ID";
    public static final String PREF_USER_ACCESS_TOKEN = "PREF_USER_ACCESS_TOKEN";

    //API Endpoints
    public static final String URL_REGISTER = "register";
    public static final String URL_NORMAL_LOGIN = "mobile_login";
    public static final String URL_SOCIAL_LOGIN = "social_login";
    public static final String URL_CHECK_USERNAME_EXIST = "check_username_exist";
    public static final String URL_CHECK_USER_EXIST = "check_user_exits";
    public static final String URL_CREATE_NEW_SURVEY = "api/v1/m/add_survey";
    public static final String URL_ADD_SURVEY_QUESTIONS = "api/v1/m/add_survey_question_answer";
    public static final String URL_FETCH_CATEGORY = "api/v1/m/list_category";
    public static final String URL_FETCH_CURRENT_SURVEY = "api/v1/m/current_survey";
    public static final String URL_FETCH_PREVIOUS_SURVEY = "api/v1/m/previous_survey";

    //User Information
    public static final String PREF_USER_FIRST_NAME = "pref_user_first_name";
    public static final String PREF_USER_LAST_NAME = "pref_user_last_name";
    public static final String PREF_USER_ID = "pref_user_id";
    public static final String PREF_USER_ROLE_ID = "pref_user_role_id";
    public static final String PREF_USER_PROFESSION_ID = "pref_user_profession_id";
    public static final String PREF_USER_EMAIL = "pref_user_email";
    public static final String PREF_USER_PHONE = "pref_user_phone";
    public static final String PREF_USER_PROFILE_PIC = "pref_user_profile_pic";
    public static final String PREF_USER_DEVICE_ID = "pref_user_device_id";

}
