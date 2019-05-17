package com.troy.playground.base.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.troy.playground.base.view.BaseView;
import com.troy.playground.server.TroyClientInterface;
import com.troy.playground.server.response.SearchPictureResponse;
import com.troy.playground.utility.AutoDisposeViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseViewModel extends AutoDisposeViewModel {
    public ObservableField<String> searchText = new ObservableField<>();

    private TroyClientInterface troyClientInterface;
    private BaseView baseView;

    public BaseViewModel(BaseView baseView, TroyClientInterface troyClientInterface) {
        this.baseView = baseView;
        this.troyClientInterface = troyClientInterface;
    }

    public void onSearchClick() {
        String keyword = searchText.get();
        if (TextUtils.isEmpty(keyword)) {
            //TODO please enter key word
            return;
        }

        search(keyword);
    }

    public void search(String keyword) {
        Disposable disposable = troyClientInterface.searchPicture(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchPictureResponse>() {
                    @Override
                    public void accept(SearchPictureResponse searchPictureResponse) throws Exception {
                        baseView.onFinishFetch(searchPictureResponse.getHits());
                    }
                });

        addDisposable(disposable);
    }

}
