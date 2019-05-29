package com.troy.playground.piccollagequiz2

import android.graphics.Paint
import android.graphics.Path

class PathInfo(path: Path?, paint: Paint?) {
    var path : Path? = null
    var paint : Paint? = null

    init {
        this.path = path
        this.paint = paint
    }
}