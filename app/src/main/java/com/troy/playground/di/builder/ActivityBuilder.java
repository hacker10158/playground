package com.troy.playground.di.builder;

import com.troy.playground.main.MainActivity;
import com.troy.playground.di.MainScope;
import com.troy.playground.main.di.MainFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainFragmentProvider.class})
    abstract MainActivity bindMainActivity();

}
