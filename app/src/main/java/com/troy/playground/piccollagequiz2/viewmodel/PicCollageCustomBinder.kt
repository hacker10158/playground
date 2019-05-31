package com.troy.playground.piccollagequiz2.viewmodel

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.troy.playground.R
import com.troy.playground.piccollagequiz2.view.PaintView

@BindingAdapter("android:pencil")
fun ImageView.updatePencil(mode : Int) {
    if (mode == PaintView.MODE_PENCIL) {
        setImageResource(R.drawable.ic_pencil)
    } else {
        setImageResource(R.drawable.ic_pencil_disable)
    }
}

@BindingAdapter("android:eraser")
fun ImageView.updateEraser(mode : Int) {
    if (mode == PaintView.MODE_ERASER) {
        setImageResource(R.drawable.ic_eraser)
    } else {
        setImageResource(R.drawable.ic_eraser_disable)
    }
}

@BindingAdapter("android:undo")
fun ImageView.updateUndo(enable : Boolean) {
    if (enable) {
        setImageResource(R.drawable.ic_undo)
    } else {
        setImageResource(R.drawable.ic_undo_disable)
    }
}

@BindingAdapter("android:redo")
fun ImageView.updateRedo(enable : Boolean) {
    if (enable) {
        setImageResource(R.drawable.ic_redo)
    } else {
        setImageResource(R.drawable.ic_redo_disable)
    }
}
