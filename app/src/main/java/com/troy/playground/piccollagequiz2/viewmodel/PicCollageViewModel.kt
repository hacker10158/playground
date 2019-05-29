package com.troy.playground.piccollagequiz2.viewmodel

import android.databinding.ObservableField
import com.troy.playground.piccollagequiz2.PathInfo
import com.troy.playground.piccollagequiz2.view.PaintView
import com.troy.playground.piccollagequiz2.view.PicCollageView
import com.troy.playground.rxbus.PathUpdater
import com.troy.playground.utility.AutoDisposeViewModel
import com.troy.playground.utility.Log
import io.reactivex.rxkotlin.subscribeBy

class PicCollageViewModel(picCollageView: PicCollageView) : AutoDisposeViewModel() {
    val modeText = ObservableField<String>()

    private var picCollageView : PicCollageView? = null
    private var currentMode = PaintView.MODE_PENCIL

    private val pathInfos = ArrayList<PathInfo>()
    private val undonePathInfos = ArrayList<PathInfo>()

    init {
        this.picCollageView = picCollageView
        this.modeText.set("Mode : PANCIL")
        val disposable = PathUpdater.getInstance().updater
                .subscribeBy(
                        onNext = {
                            pathInfos.add(it)
                            undonePathInfos.clear() //clear undone path when draw
                        },
                        onError = {
                            Log.e("Error on catch path info. Throwable : " + it.message )
                        }
                )

        addDisposable(disposable)

    }

    fun onClickSwitchMode() {
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

    fun onClickUndo() {
        if (pathInfos.size > 0)
        {
            undonePathInfos.add(pathInfos[pathInfos.size-1])
            pathInfos.removeAt(pathInfos.size-1)
            picCollageView?.updatePathInfos(pathInfos)
        }
    }

    fun onClickRedo() {
        if (undonePathInfos.size > 0)
        {
            pathInfos.add(undonePathInfos[undonePathInfos.size-1])
            undonePathInfos.removeAt(undonePathInfos.size-1)
            picCollageView?.updatePathInfos(pathInfos)
        }
    }
}