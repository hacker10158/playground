package com.troy.playground.omlet.di;

import android.arch.lifecycle.ViewModelProvider;

import com.troy.playground.ViewModelProviderFactory;
import com.troy.playground.omlet.OmletFragment;
import com.troy.playground.omlet.view.OmletView;
import com.troy.playground.omlet.viewmodel.OmletViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class OmletModule {

    @Provides
    public OmletView provideOmletView(OmletFragment omletFragment) {
        return omletFragment;
    }

    @Provides
    public OmletViewModel provideOmletViewModel(OmletView omletView) {
        return new OmletViewModel(omletView);
    }

    @Provides
    @Named("omlet")
    ViewModelProvider.Factory provideOmletViewModelProviderFactory(OmletViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
