package com.troy.playground.search.di;

import android.arch.lifecycle.ViewModelProvider;

import com.troy.playground.ViewModelProviderFactory;
import com.troy.playground.search.SearchFragment;
import com.troy.playground.search.view.SearchImageAdapter;
import com.troy.playground.search.view.SearchView;
import com.troy.playground.search.viewmodel.SearchViewModel;
import com.troy.playground.server.TroyClientInterface;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    public SearchView provideBaseView(SearchFragment searchFragment) {
        return searchFragment;
    }

    @Provides
    public SearchViewModel provideBaseViewModel(SearchView searchView, TroyClientInterface troyClientInterface) {
        return new SearchViewModel(searchView, troyClientInterface);
    }

    @Provides
    public SearchImageAdapter provideSearchImageAdapter() {
        return new SearchImageAdapter();
    }

    @Provides
    @Named("base")
    ViewModelProvider.Factory provideBaseViewModelProviderFactory(SearchViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
