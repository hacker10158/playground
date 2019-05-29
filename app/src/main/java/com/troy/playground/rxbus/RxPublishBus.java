package com.troy.playground.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxPublishBus {
    private static volatile RxPublishBus mDefaultInstance;

    private final Subject<Object> bus;

    private RxPublishBus() {
        bus = PublishSubject.create().toSerialized();
    }

    public static RxPublishBus getInstance() {
        if (mDefaultInstance == null) {
            synchronized (RxPublishBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxPublishBus();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * Single object
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
