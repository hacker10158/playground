package com.troy.playground.base;

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
import com.troy.playground.base.model.ImageData;
import com.troy.playground.base.view.BaseView;
import com.troy.playground.base.viewmodel.BaseViewModel;
import com.troy.playground.databinding.FragmentBaseBinding;
import com.troy.playground.utility.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;

public class BaseFragment extends DaggerFragment implements BaseView {

    private FragmentBaseBinding binding;
    private BaseViewModel viewModel;

    @Inject
    @Named("versionName")
    String versionName;

    @Inject
    @Named("base")
    ViewModelProvider.Factory viewModelFactory;

    private CompositeDisposable compositeDisposable;

    public static BaseFragment newInstance() {
        return new BaseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel.class);
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init () {
        binding.tvHelloWorld.setText("Search " + versionName);
        viewModel.search();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onFinishFetch(List<ImageData> data) {
        for (ImageData imageData : data) {
            Log.d("5566 " + imageData.getWebformatURL());
        }
    }
}
