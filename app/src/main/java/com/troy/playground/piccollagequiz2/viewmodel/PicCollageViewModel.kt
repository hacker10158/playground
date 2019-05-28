package com.troy.playground.piccollagequiz2.viewmodel

import com.troy.playground.piccollagequiz2.view.PicCollageView
import com.troy.playground.utility.AutoDisposeViewModel

class PicCollageViewModel(picCollageView: PicCollageView) : AutoDisposeViewModel() {
    private var picCollageView : PicCollageView? = null

    init {
        this.picCollageView = picCollageView
    }

}