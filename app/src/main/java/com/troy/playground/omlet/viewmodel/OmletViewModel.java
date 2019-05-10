package com.troy.playground.omlet.viewmodel;

import com.troy.playground.omlet.view.OmletView;
import com.troy.playground.utility.AutoDisposeViewModel;

public class OmletViewModel extends AutoDisposeViewModel {
    private OmletView omletView;

    public OmletViewModel(OmletView omletView) {
        this.omletView = omletView;
    }
}
