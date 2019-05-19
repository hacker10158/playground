package com.troy.playground.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.troy.playground.R;
import com.troy.playground.databinding.FragmentSearchBinding;
import com.troy.playground.search.model.ImageData;
import com.troy.playground.search.view.SearchImageAdapter;
import com.troy.playground.search.view.SearchView;
import com.troy.playground.search.viewmodel.SearchViewModel;
import com.troy.playground.utility.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends DaggerFragment implements SearchView, View.OnKeyListener {
    private final int DEFAULT_SPAN_COUNT = 1;

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
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

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init () {
        binding.tvVersion.setText(getString(R.string.version, versionName));
        binding.etInputField.setOnKeyListener(this);
        binding.rvContent.setAdapter(searchImageAdapter);

        layoutManager = new StaggeredGridLayoutManager(
                DEFAULT_SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL);

        binding.rvContent.setLayoutManager(layoutManager);
        binding.rvContent.addOnScrollListener(viewModel.createScrollListener());
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void cleanSearchData() {
        searchImageAdapter.cleanData();
    }

    @Override
    public void onFinishFetch(List<ImageData> data) {
        Log.d("onFinishFetch");
        searchImageAdapter.addData(data);
    }

    @Override
    public void switchDisplayType() {
        int count = layoutManager.getSpanCount();
        if (count == 1) {
            layoutManager.setSpanCount(3);
            binding.tvSwitch.setText(getString(R.string.grid));
        } else {
            layoutManager.setSpanCount(1);
            binding.tvSwitch.setText(getString(R.string.list));
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            Log.d("enter clicked");
            viewModel.onSearchClick();
            return true;
        }
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }

        View decorView = getActivity().getWindow().getDecorView();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
        }
    }

    @Override
    public void showEmptyInputToast() {
        Toast.makeText(getContext(), R.string.input_empty_hint, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingMore(boolean show) {
        searchImageAdapter.setLoadingMore(show);
    }
}
