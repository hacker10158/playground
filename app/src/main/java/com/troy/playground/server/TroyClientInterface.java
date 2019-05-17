package com.troy.playground.server;

import com.troy.playground.server.response.SearchPictureResponse;

import io.reactivex.Single;

public interface TroyClientInterface {
    Single<SearchPictureResponse> searchPicture(String keyword);
}
