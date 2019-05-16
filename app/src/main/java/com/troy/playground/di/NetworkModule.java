package com.troy.playground.di;

import com.troy.playground.server.TroyClient;
import com.troy.playground.server.TroyClientInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    TroyClientInterface provideTroyClientInterface() {
        return new TroyClient();
    }
}
