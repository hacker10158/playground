package com.troy.playground.piccollagequiz2.viewmodel

import android.databinding.ObservableField
import com.troy.playground.piccollagequiz2.view.PaintView
import com.troy.playground.piccollagequiz2.view.PicCollageView
import com.troy.playground.utility.AutoDisposeViewModel

class PicCollageViewModel(picCollageView: PicCollageView) : AutoDisposeViewModel() {
    public val modeText = ObservableField<String>()

    private var picCollageView : PicCollageView? = null
    private var currentMode = PaintView.MODE_PENCIL

    init {
        this.picCollageView = picCollageView
        this.modeText.set("Mode : PANCIL")
    }

    public fun onClickSwitchMode() {
        when(currentMode) {
            PaintView.MODE_PENCIL -> {
                currentMode = PaintView.MODE_ERASER
                picCollageView?.switchMode(currentMode)
                this.modeText.set("Mode : ERASER")
            }
            PaintView.MODE_ERASER -> {
                currentMode = PaintView.MODE_PENCIL
                picCollageView?.switchMode(currentMode)
                this.modeText.set("Mode : PANCIL")
            }
        }
    }

    public fun onClickUndo() {
        picCollageView?.undo()
    }

    public fun onClickRedo() {
        picCollageView?.redo()
    }
}