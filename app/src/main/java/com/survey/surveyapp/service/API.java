package com.survey.surveyapp.service;

import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.vo.VoResponceCheckUserExist;
import com.survey.surveyapp.vo.VoResponseLogin;
import com.survey.surveyapp.vo.VoResponseRegister;
import com.survey.surveyapp.vo.VoResponseSocialLogin;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    @Headers("Content-Type: application/json")
    @POST(TagValues.URL_REGISTER)
    Observable<VoResponseRegister> userRegister(@Body RequestBody mRequestBody);

    @Headers("Content-Type: application/json")
    @POST(TagValues.URL_NORMAL_LOGIN)
    Observable<VoResponseLogin> userNormalLogin(@Body RequestBody mRequestBody);

    @Headers("Content-Type: application/json")
    @POST(TagValues.URL_SOCIAL_LOGIN)
    Observable<VoResponseSocialLogin> userSocialLogin(@Body RequestBody mRequestBody);

    @Headers("Content-Type: application/json")
    @POST(TagValues.URL_CHECK_USERNAME_EXIST)
    Observable<VoResponceCheckUserExist> checkUserExist(@Body RequestBody mRequestBody);

    @Headers("Content-Type: application/json")
    @POST(TagValues.URL_CHECK_USER_EXIST)
    Observable<VoResponceCheckUserExist> verifuUserPhome(@Body RequestBody mRequestBody);

}