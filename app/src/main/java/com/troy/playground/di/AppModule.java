package com.troy.playground.di;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    PackageManager providePackageManager(Context context) {
        return context.getPackageManager();
    }

    @Provides
    @Singleton
    @Named("versionName")
    String provideVersionName(Context context, PackageManager packageManager) {
        String version = "";
        try {
            version = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ignore){}

        return version;
    }
}
