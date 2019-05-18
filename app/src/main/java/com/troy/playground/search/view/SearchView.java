package com.troy.playground.search.view;

import com.troy.playground.search.model.ImageData;

import java.util.List;

public interface SearchView {
    void cleanSearchData();
    void onFinishFetch(List<ImageData> data);
    void switchDisplayType();
    void hideKeyboard();
    void showEmptyInputToast();
}
