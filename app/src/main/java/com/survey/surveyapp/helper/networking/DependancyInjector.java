package com.survey.surveyapp.helper.networking;

import com.survey.surveyapp.MyDaggerActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface DependancyInjector {
    void injectMyDaggerActivity(MyDaggerActivity myDaggerActivity);
}
