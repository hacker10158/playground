package com.troy.playground.omlet;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troy.playground.R;
import com.troy.playground.base.viewmodel.BaseViewModel;
import com.troy.playground.databinding.FragmentBaseBinding;
import com.troy.playground.databinding.FragmentOmletBinding;
import com.troy.playground.omlet.view.OmletView;
import com.troy.playground.omlet.viewmodel.OmletViewModel;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;

public class OmletFragment extends DaggerFragment implements OmletView {

    private FragmentOmletBinding binding;
    private OmletViewModel viewModel;

    @Inject
    @Named("versionName")
    String versionName;

    @Inject
    @Named("omlet")
    ViewModelProvider.Factory viewModelFactory;

    private CompositeDisposable compositeDisposable;

    public static OmletFragment newInstance() {
        return new OmletFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OmletViewModel.class);
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_omlet, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init () {
        binding.tvHelloWorld.setText("Omlet v " + versionName);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
