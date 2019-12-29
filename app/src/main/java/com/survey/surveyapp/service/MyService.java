package com.survey.surveyapp.service;

import com.survey.surveyapp.vo.VoResponseLogin;
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
                        mServiceCallback.onError(new Exception(e));
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

    public interface ServiceCallback<T> {
        void onSuccess(T data);

        void onError(Exception networkError);
    }

}
