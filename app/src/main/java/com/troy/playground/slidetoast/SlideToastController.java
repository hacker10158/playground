package com.troy.playground.slidetoast;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.troy.playground.R;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SlideToastController {
    private final static int STATUS_IDLE        = 0;
    private final static int STATUS_SHOW        = 1;

    private final static int BUFFER_SIZE        = 50;

    private FlowableProcessor<String> dataSubject;
    private Subscription subscription;
    private List<View> toastItemViews;
    private View containerView;

    public SlideToastController(){
        dataSubject = PublishProcessor.create();
        setupDataSubject();
    }

    public void init(View view) {
        if (containerView != null) {
            return;
        }
        containerView = view;

        if (toastItemViews == null) {
            toastItemViews = new ArrayList<>();
        }
        toastItemViews.clear();

        View item1 = containerView.findViewById(R.id.toast_item_1);
        item1.setTag(STATUS_IDLE);
        View item2 = containerView.findViewById(R.id.toast_item_2);
        item2.setTag(STATUS_IDLE);
        View item3 = containerView.findViewById(R.id.toast_item_3);
        item3.setTag(STATUS_IDLE);
        toastItemViews.add(item1);
        toastItemViews.add(item2);
        toastItemViews.add(item3);
    }

    void setupDataSubject(){
        dataSubject
                .subscribeOn(Schedulers.computation())
                .onBackpressureBuffer( BUFFER_SIZE, new Action() {
                    @Override
                    public void run() throws Exception {
                        // do nothing
                    }
                }, BackpressureOverflowStrategy.DROP_LATEST)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        subscription.request(3);
                    }

                    @Override
                    public void onNext(String s) {
                        View view = getIdleItem();
                        if (view == null)
                            return;
                        showAnimation(view, s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void showView(String number){
        dataSubject.onNext(number);
    }

    private View getIdleItem() {
        for (View view : toastItemViews) {
            if (view.getTag().equals(STATUS_IDLE)) {
                return view;
            }
        }

        return null;
    }

    private void showAnimation(final View view, String number) {
        TextView textView = view.findViewById(R.id.item_text);
        textView.setText(number);

        AnimationSet amSet = new AnimationSet(false);
        Animation amFadeIn = new AlphaAnimation(0, 1.0f);
        amFadeIn.setDuration(300);
        amFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setTag(STATUS_SHOW);
                view.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        Animation amFadeOut = new AlphaAnimation(1.0f, 0);
        amFadeOut.setDuration(300);
        amFadeOut.setStartOffset(3000);
        amFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setTag(STATUS_IDLE);
                view.setVisibility(INVISIBLE);
                subscription.request(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        Animation amTranslateIn = new TranslateAnimation( -view.getWidth(), 0.0f, 0.0f, 0.0f);
        amTranslateIn.setDuration(300);

        Animation amTranslateOut = new TranslateAnimation(0.0f, 0.0f, 0.0f, -100.0f);
        amTranslateOut.setDuration(300);
        amTranslateOut.setStartOffset(3000);

        amSet.addAnimation(amFadeIn);
        amSet.addAnimation(amFadeOut);
        amSet.addAnimation(amTranslateIn);
        amSet.addAnimation(amTranslateOut);
        view.startAnimation(amSet);
    }

    public void clean(){
        if (subscription != null) {
            subscription.cancel();
            subscription = null;
        }
    }
}
