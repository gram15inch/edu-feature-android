package com.learning.threadgame.thread

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.learning.threadgame.model.BaseMap


class MyThread(private val surHolder: SurfaceHolder, val baseMap: BaseMap) : Thread() {
    var threadRun = true

    override fun run() {
        while (threadRun) {
            var canvas: Canvas? = null
            try {
                canvas = surHolder.lockCanvas(null)
                synchronized(surHolder) {
                    baseMap.bgImg.draw(canvas)
                    for(potato in baseMap.potatoes)
                        potato.draw(canvas)
                }
            } finally {
                if (canvas != null) {
                    surHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }


}