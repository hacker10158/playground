package com.troy.playground.base.di;

import android.arch.lifecycle.ViewModelProvider;

import com.troy.playground.ViewModelProviderFactory;
import com.troy.playground.base.BaseFragment;
import com.troy.playground.base.SearchImageAdapter;
import com.troy.playground.base.view.BaseView;
import com.troy.playground.base.viewmodel.BaseViewModel;
import com.troy.playground.server.TroyClientInterface;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {

    @Provides
    public BaseView provideBaseView(BaseFragment baseFragment) {
        return baseFragment;
    }

    @Provides
    public BaseViewModel provideBaseViewModel(BaseView baseView, TroyClientInterface troyClientInterface) {
        return new BaseViewModel(baseView, troyClientInterface);
    }

    @Provides
    public SearchImageAdapter provideSearchImageAdapter() {
        return new SearchImageAdapter();
    }

    @Provides
    @Named("base")
    ViewModelProvider.Factory provideBaseViewModelProviderFactory(BaseViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
