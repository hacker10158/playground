package com.troy.playground.main.di;

import com.troy.playground.base.BaseFragment;
import com.troy.playground.base.di.BaseModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = BaseModule.class)
    abstract BaseFragment provideBaseFragment();
}
