package com.troy.playground;

import android.content.Context;
import android.support.annotation.NonNull;

import com.troy.playground.di.AppComponent;
import com.troy.playground.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApplication extends DaggerApplication {

    private AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public static AppComponent injector(@NonNull Context context) {
        return ((MyApplication)context.getApplicationContext()).appComponent;
    }


}
