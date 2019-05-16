package com.troy.playground.base.viewmodel;

import com.troy.playground.base.view.BaseView;
import com.troy.playground.server.TroyClientInterface;
import com.troy.playground.server.response.SearchPictureResponse;
import com.troy.playground.utility.AutoDisposeViewModel;
import com.troy.playground.utility.Log;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseViewModel extends AutoDisposeViewModel {
    private TroyClientInterface troyClientInterface;
    private BaseView baseView;

    public BaseViewModel(BaseView baseView, TroyClientInterface troyClientInterface) {
        this.baseView = baseView;
        this.troyClientInterface = troyClientInterface;
    }

    public void search() {
        Disposable disposable = troyClientInterface.searchPicture()
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
