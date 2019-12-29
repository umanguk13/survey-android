package com.survey.surveyapp.helper.networking;

import android.app.Application;

import com.survey.surveyapp.SurveyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(SurveyApplication mSurveyApplication);

}
