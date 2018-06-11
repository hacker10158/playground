package com.troy.playground.slidetoast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.troy.playground.R;

import java.util.ArrayList;
import java.util.List;

public class SlideContainer extends RelativeLayout {
    private final static int STATUS_IDLE        = 0;
    private final static int STATUS_SHOW       = 1;

    private List<View> toastItems = new ArrayList<>();
    private int counter = 0;
    public SlideContainer(Context context) {
        super(context);
        init(context);
    }

    public SlideContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        View item1 = findViewById(R.id.toast_item_1);
        item1.setTag(STATUS_IDLE);
        View item2 = findViewById(R.id.toast_item_2);
        item2.setTag(STATUS_IDLE);
        View item3 = findViewById(R.id.toast_item_3);
        item3.setTag(STATUS_IDLE);
        toastItems.add(item1);
        toastItems.add(item2);
        toastItems.add(item3);
    }
    public void showView(){
        View view = getIdleItem();
        if (view == null)
            return;
        showAnimation(view);
    }

    View getIdleItem() {
        for (View view : toastItems) {
            if (view.getTag().equals(STATUS_IDLE)) {
                return view;
            }
        }

        return null;
    }

    private void showAnimation(final View view) {
        TextView textView = view.findViewById(R.id.item_text);
        textView.setText(String.valueOf( counter));
        counter++;

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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        Animation amTranslateIn = new TranslateAnimation( - view.getWidth(), 0.0f, 0.0f, 0.0f);
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
}
