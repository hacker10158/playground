package com.troy.playground.piccollagequiz2.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import com.troy.playground.piccollagequiz2.PathInfo
import com.troy.playground.piccollagequiz2.view.PaintView
import com.troy.playground.piccollagequiz2.view.PicCollageView
import com.troy.playground.rxbus.PathUpdater
import com.troy.playground.utility.AutoDisposeViewModel
import com.troy.playground.utility.Log
import io.reactivex.rxkotlin.subscribeBy

class PicCollageViewModel(picCollageView: PicCollageView) : AutoDisposeViewModel() {
    var mode = ObservableInt(PaintView.MODE_PENCIL)
    var enableUndo = ObservableBoolean(false)
    var enableRedo = ObservableBoolean(false)
    private var picCollageView : PicCollageView? = null

    private val pathInfos = ArrayList<PathInfo>()
    private val undonePathInfos = ArrayList<PathInfo>()

    init {
        this.picCollageView = picCollageView
        val disposable = PathUpdater.getInstance().updater
                .subscribeBy(
                        onNext = {
                            pathInfos.add(it)
                            undonePathInfos.clear() //clear undone path when draw
                            checkUndoRedoIcon()
                        },
                        onError = {
                            Log.e("Error on catch path info. Throwable : " + it.message )
                        }
                )

        addDisposable(disposable)

    }

    fun onClickPencil() {
        mode.set(PaintView.MODE_PENCIL)
        picCollageView?.setMode(PaintView.MODE_PENCIL)
    }

    fun onClickEraser() {
        mode.set(PaintView.MODE_ERASER)
        picCollageView?.setMode(PaintView.MODE_ERASER)
    }

    fun onClickUndo() {
        if (pathInfos.size > 0)
        {
            undonePathInfos.add(pathInfos[pathInfos.size-1])
            pathInfos.removeAt(pathInfos.size-1)
            picCollageView?.updatePathInfos(pathInfos)

            checkUndoRedoIcon()
        }
    }

    fun onClickRedo() {
        if (undonePathInfos.size > 0)
        {
            pathInfos.add(undonePathInfos[undonePathInfos.size-1])
            undonePathInfos.removeAt(undonePathInfos.size-1)
            picCollageView?.updatePathInfos(pathInfos)

            checkUndoRedoIcon()
        }
    }

    fun checkUndoRedoIcon() {
        enableUndo.set(pathInfos.size > 0)
        enableRedo.set(undonePathInfos.size > 0)
    }
}