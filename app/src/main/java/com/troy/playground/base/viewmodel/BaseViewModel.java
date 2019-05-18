package com.troy.playground.base.viewmodel;

import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.troy.playground.base.view.BaseView;
import com.troy.playground.server.TroyClientInterface;
import com.troy.playground.server.response.SearchPictureResponse;
import com.troy.playground.utility.AutoDisposeViewModel;
import com.troy.playground.utility.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class BaseViewModel extends AutoDisposeViewModel {

    private static final int PRELOAD_COUNT = 15;

    public ObservableField<String> searchText = new ObservableField<>();

    private TroyClientInterface troyClientInterface;
    private Subject<Integer> scrollSubject;
    private BaseView baseView;
    private int page = 1;
    private String keyword;
    private boolean isStartLoading = false;
    private boolean isLoadingMore = false;

    public BaseViewModel(BaseView baseView, TroyClientInterface troyClientInterface) {
        this.baseView = baseView;
        this.troyClientInterface = troyClientInterface;

        setupScrollSubject();
    }

    public void onSearchClick() {
        keyword = searchText.get();
        if (TextUtils.isEmpty(keyword)) {
            //TODO please enter key word
            return;
        }
        baseView.cleanSearchData();
        baseView.hideKeyboard();
        isStartLoading = true;
        isLoadingMore = false;
        page = 1;
        search(keyword);
    }

    public void search(String keyword) {

        if (!isStartLoading) {
            Log.d("not start loading");
            return;
        }

        if (isLoadingMore) {
            Log.d("is loading more");
            return;
        }

        isLoadingMore = true;

        Disposable disposable = troyClientInterface.searchPicture(keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchPictureResponse>() {
                    @Override
                    public void accept(SearchPictureResponse searchPictureResponse) throws Exception {
                        isLoadingMore = false;
                        page ++;
                        if (searchPictureResponse.getHits() == null || searchPictureResponse.getHits().size() == 0) {
                            isStartLoading = false;
                            return;
                        }
                        baseView.onFinishFetch(searchPictureResponse.getHits());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isStartLoading = false;
                        isLoadingMore = false;
                        Log.e("Error on fetch data. Throwable : " +throwable.getMessage() );
                    }
                });

        addDisposable(disposable);
    }

    private void setupScrollSubject() {
        scrollSubject = PublishSubject.<Integer>create().toSerialized();
        Disposable disposable = scrollSubject.throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("scrolled");
                        search(keyword);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Error on loading more. Throwable: " + throwable);

                    }
                });

        addDisposable(disposable);
    }

    public void onClickSwitchDisplayType() {
        baseView.switchDisplayType();
    }

    public RecyclerView.OnScrollListener createScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalCount = recyclerView.getLayoutManager().getItemCount();
                int lastPosition;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                } else {
                    View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                    if (lastChildView == null) {
                        Log.w("lastChildView null"); // what this mean?
                        return;
                    }
                    lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                }
                Log.d("lastPosition " + (lastPosition) + " totalCount " + totalCount);

                if (dy > 0 && lastPosition >= totalCount - PRELOAD_COUNT) {
                    scrollSubject.onNext(0);
                }
            }
        };
    }
}
