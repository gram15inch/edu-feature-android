package com.learning.threadgame.thread

import android.graphics.Canvas


enum class Step { Start, Run, ReStart }
interface Stage {
    fun stageStart(canvas: Canvas)
    fun stageRun(canvas: Canvas)
    fun stageReStart(canvas: Canvas)
}


