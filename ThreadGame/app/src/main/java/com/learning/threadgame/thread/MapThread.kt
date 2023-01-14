package com.learning.threadgame.thread

import android.graphics.Canvas
import android.media.audiofx.DynamicsProcessing

import android.view.SurfaceHolder
import com.learning.threadgame.model.BaseMap
import com.learning.threadgame.model.Stage
import com.learning.threadgame.model.Step


open class MapThread(private val surHolder: SurfaceHolder, val baseMap: BaseMap) : Thread()
    , Stage {
    var threadRun = true

    override fun run() {
        while (threadRun) {
            var canvas: Canvas? = null
            try {
                canvas = surHolder.lockCanvas(null)
                synchronized(surHolder) {
                    when(baseMap.stage){
                        Step.Start-> stageStart(canvas)
                        Step.Run-> stageRun(canvas)
                        Step.ReStart-> stageReStart(canvas)
                    }
                }
            } finally {
                if (canvas != null) {
                    surHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    override fun stageStart(canvas: Canvas) {
        baseMap.bgImgs[0].draw(canvas)
        baseMap.uiStatus.draw(baseMap.stage,canvas)
    }

    override fun stageRun(canvas: Canvas) {
        baseMap.bgImgs[1].draw(canvas)
        baseMap.uiStatus.draw(baseMap.stage,canvas)
        for (potato in baseMap.potatoes)
            potato.draw(canvas)
    }

    override fun stageReStart(canvas: Canvas) {
        baseMap.bgImgs[2].draw(canvas)
        baseMap.uiStatus.draw(baseMap.stage,canvas)
        if(baseMap.backMusic.isPlaying)
            baseMap.backMusic.pause()
    }


}