package com.troy.playground.piccollagequiz2.view

import com.troy.playground.piccollagequiz2.PathInfo

interface PicCollageView {
    fun setMode(mode : Int)
    fun updatePathInfos(pathInfos: ArrayList<PathInfo>)
}