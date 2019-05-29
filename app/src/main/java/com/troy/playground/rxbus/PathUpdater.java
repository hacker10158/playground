package com.troy.playground.rxbus;

import com.troy.playground.piccollagequiz2.PathInfo;

import io.reactivex.Observable;

public class PathUpdater {

    private static volatile PathUpdater mDefaultInstance;

    private Observable<PathInfo> updater;

    public static PathUpdater getInstance() {
        if (mDefaultInstance == null) {
            synchronized (PathUpdater.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new PathUpdater();
                }
            }
        }
        return mDefaultInstance;
    }

    private PathUpdater() {
        // initialize updaters
        updater = RxPublishBus.getInstance()
                .toObservable(PathInfo.class)
                .share();
    }

    public Observable<PathInfo> getUpdater() {
        return updater;
    }

    public void notify(PathInfo pathInfo) {
        RxPublishBus.getInstance().post(pathInfo);
    }
}
