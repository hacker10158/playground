package com.troy.playground.base.view;

import com.troy.playground.base.model.ImageData;

import java.util.List;

public interface BaseView {
    void onFinishFetch(List<ImageData> data);
}
