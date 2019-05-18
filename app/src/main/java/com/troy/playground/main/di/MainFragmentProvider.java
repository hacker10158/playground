package com.troy.playground.main.di;

import com.troy.playground.search.SearchFragment;
import com.troy.playground.search.di.SearchModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment provideBaseFragment();
}
