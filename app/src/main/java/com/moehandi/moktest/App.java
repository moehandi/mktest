package com.moehandi.moktest;

import android.app.Application;

import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.di.ApplicationModule;
import com.moehandi.moktest.di.DaggerApplicationComponent;

/**
 * Created by moehandi on 22/5/19.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
