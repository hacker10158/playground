package com.troy.playground.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    private StaggeredGridLayoutManager layoutManager;

    @Inject
    @Named("versionName")
    String versionName;

    @Inject
    @Named("base")
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    SearchImageAdapter searchImageAdapter;

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
        binding.tvVersion.setText("Search v" + versionName);

        binding.rvContent.setAdapter(searchImageAdapter);

        int spanCount = 3;
        layoutManager = new StaggeredGridLayoutManager(
                spanCount,
                StaggeredGridLayoutManager.VERTICAL);

        binding.rvContent.setLayoutManager(layoutManager);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onFinishFetch(List<ImageData> data) {
        Log.d("onFinishFetch");
        searchImageAdapter.updateData(data);
    }

    @Override
    public void switchDisplayType() {
        int count = layoutManager.getSpanCount();
        if (count == 1) {
            layoutManager.setSpanCount(3);
            binding.ivSwitch.setImageResource(R.drawable.grid_icon);
        } else {
            layoutManager.setSpanCount(1);
            binding.ivSwitch.setImageResource(R.drawable.list_icon);
        }
    }
}
