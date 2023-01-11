package com.learning.threadgame.thread

import android.annotation.SuppressLint
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
                   // MovingCar(3, 0, 0, 1000, Color.GRAY).draw(canvas)
                    baseMap.bgImg.draw(canvas)
                    // todo 무한루프 안에 처리할것들

                }
            } finally {
                if (canvas != null) {
                    surHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }


}