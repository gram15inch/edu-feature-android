package com.learning.threadgame.thread

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.learning.threadgame.model.BaseMap
import com.learning.threadgame.model.Potato


class MyThread(private val surHolder: SurfaceHolder, val baseMap: BaseMap) : Thread() {
    var threadRun = true

    override fun run() {
        while (threadRun) {
            var canvas: Canvas? = null
            try {
                canvas = surHolder.lockCanvas(null)
                synchronized(surHolder) {
                    baseMap.bgImg.draw(canvas)
                    // todo 무한루프 안에 처리할것들
                    for(potato in baseMap.potatos)
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