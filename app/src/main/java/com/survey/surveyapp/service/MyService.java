package com.survey.surveyapp.service;

import com.survey.surveyapp.vo.VoResponseCheckUserExist;
import com.survey.surveyapp.vo.VoResponseAddSurveyQuestions;
import com.survey.surveyapp.vo.VoResponseCreateNewSurvey;
import com.survey.surveyapp.vo.VoResponseCurrentSurvey;
import com.survey.surveyapp.vo.VoResponseFetchCategory;
import com.survey.surveyapp.vo.VoResponseFetchUserProfile;
import com.survey.surveyapp.vo.VoResponseLogin;
import com.survey.surveyapp.vo.VoResponsePreviousSurvey;
import com.survey.surveyapp.vo.VoResponseRegister;
import com.survey.surveyapp.vo.VoResponseSocialLogin;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyService {

    private final API mApiService;

    public MyService(API apiService) {
        this.mApiService = apiService;
    }

    public void doNormalRegister(JSONObject mJsonObjectData, final ServiceCallback<VoResponseRegister> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.userRegister(mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseRegister>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseRegister voResponseRegister) {
                        mServiceCallback.onSuccess(voResponseRegister);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void checkUserExist(JSONObject mJsonObjectData, final ServiceCallback<VoResponseCheckUserExist> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.checkUserExist(mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseCheckUserExist>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseCheckUserExist voResponseRegister) {
                        mServiceCallback.onSuccess(voResponseRegister);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void verifyUserPhone(JSONObject mJsonObjectData, final ServiceCallback<VoResponseCheckUserExist> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.verifyUserPhone(mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseCheckUserExist>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseCheckUserExist voResponseRegister) {
                        mServiceCallback.onSuccess(voResponseRegister);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void doNormalLogin(JSONObject mJsonObjectData, final ServiceCallback<VoResponseLogin> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.userNormalLogin(mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseLogin>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseLogin voResponseLogin) {
                        mServiceCallback.onSuccess(voResponseLogin);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void doSocialLogin(JSONObject mJsonObjectData, final ServiceCallback<VoResponseSocialLogin> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.userSocialLogin(mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseSocialLogin>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseSocialLogin voResponseSocialLogin) {
                        mServiceCallback.onSuccess(voResponseSocialLogin);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void createNewSurvey(String mStringAccessToken, String mStringKey, JSONObject mJsonObjectData, final ServiceCallback<VoResponseCreateNewSurvey> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.createNewSurvey(mStringAccessToken, mStringKey, mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseCreateNewSurvey>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseCreateNewSurvey voResponseCreateNewSurvey) {
                        mServiceCallback.onSuccess(voResponseCreateNewSurvey);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addSurveyQuestions(String mStringAccessToken, String mStringKey, JSONObject mJsonObjectData, final ServiceCallback<VoResponseAddSurveyQuestions> mServiceCallback) {
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonObjectData.toString());

        mApiService.addSurveyQuestions(mStringAccessToken, mStringKey, mRequestBody)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseAddSurveyQuestions>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseAddSurveyQuestions voResponseAddSurveyQuestions) {
                        mServiceCallback.onSuccess(voResponseAddSurveyQuestions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fetchSurveyCategory(String mStringAccessToken, String mStringKey, final ServiceCallback<VoResponseFetchCategory> mServiceCallback) {

        mApiService.fetchSurveyCategory(mStringAccessToken, mStringKey)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseFetchCategory>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseFetchCategory voResponseFetchCategory) {
                        mServiceCallback.onSuccess(voResponseFetchCategory);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fetchCurrentSurvey(String mStringAccessToken, String mStringKey, final ServiceCallback<VoResponseCurrentSurvey> mServiceCallback) {

        mApiService.fetchCurrentSurvey(mStringAccessToken, mStringKey)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseCurrentSurvey>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseCurrentSurvey voResponseCurrentSurvey) {
                        mServiceCallback.onSuccess(voResponseCurrentSurvey);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void fetchPreviousSurvey(String mStringAccessToken, String mStringKey, final ServiceCallback<VoResponsePreviousSurvey> mServiceCallback) {

        mApiService.fetchPreviousSurvey(mStringAccessToken, mStringKey)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponsePreviousSurvey>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponsePreviousSurvey voResponsePreviousSurvey) {
                        mServiceCallback.onSuccess(voResponsePreviousSurvey);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void fetchUserProfile(String mStringAccessToken, String mStringKey, final ServiceCallback<VoResponseFetchUserProfile> mServiceCallback) {

        mApiService.fetchUserProfile(mStringAccessToken, mStringKey)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(data -> Observable.just(data))
                .filter(data -> data != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoResponseFetchUserProfile>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VoResponseFetchUserProfile voResponseFetchUserProfile) {
                        mServiceCallback.onSuccess(voResponseFetchUserProfile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mServiceCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface ServiceCallback<T> {
        void onSuccess(T data);

        void onError(Throwable networkError);
    }

}
