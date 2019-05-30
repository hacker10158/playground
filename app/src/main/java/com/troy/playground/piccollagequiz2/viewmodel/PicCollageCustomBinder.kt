package com.troy.playground.piccollagequiz2.viewmodel

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.troy.playground.R
import com.troy.playground.piccollagequiz2.view.PaintView

object PicCollageCustomBinder {

    @JvmStatic
    @BindingAdapter("piccollage:pencil")
    fun ImageView.updatePencil(mode : Int) {
        if (mode == PaintView.MODE_PENCIL) {
            setImageResource(R.drawable.ic_pencil)
        } else {
            setImageResource(R.drawable.ic_pencil_disable)
        }
    }

    @JvmStatic
    @BindingAdapter("piccollage:eraser")
    fun ImageView.updateEraser(mode : Int) {
        if (mode == PaintView.MODE_ERASER) {
            setImageResource(R.drawable.ic_eraser)
        } else {
            setImageResource(R.drawable.ic_eraser_disable)
        }
    }

    @JvmStatic
    @BindingAdapter("piccollage:undo")
    fun ImageView.updateUndo(allow : Boolean) {
        if (allow) {
            setImageResource(R.drawable.ic_undo)
        } else {
            setImageResource(R.drawable.ic_undo_disable)
        }
    }

    @JvmStatic
    @BindingAdapter("piccollage:redo")
    fun ImageView.updateRedo(allow : Boolean) {
        if (allow) {
            setImageResource(R.drawable.ic_redo)
        } else {
            setImageResource(R.drawable.ic_redo_disable)
        }
    }
}
