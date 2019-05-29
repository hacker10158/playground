package com.troy.playground.piccollagequiz2

import android.graphics.Paint
import android.graphics.Path

class PathInfo {
    var path : Path? = null
    var paint : Paint? = null

    constructor(path: Path?, paint: Paint?) {
        this.path = path
        this.paint = paint
    }
}