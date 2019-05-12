package com.troy.playground.main.di;

import com.troy.playground.omlet.OmletFragment;
import com.troy.playground.omlet.di.OmletModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = OmletModule.class)
    abstract OmletFragment provideOmletFragment();
}
