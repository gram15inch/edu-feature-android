package com.learning.threadgame.model

import android.graphics.*
import android.view.MotionEvent
import com.learning.threadgame.thread.Stage
import com.learning.threadgame.thread.Step

class UiStatus : Stage {
    var score = 0
    var life = 3
    val startButton = StageButton("시작",420,1580)

    var textPaintSub = Paint().also {
        it.color = Color.WHITE
        it.textSize = 80f
    }
    var textPaintStep = Paint().also {
        it.color = Color.WHITE
        it.textSize = 170f
    }
    var textPaintMain = Paint().also {
        it.color = Color.WHITE
        it.textSize = 120f
    }

    fun draw(step:Step,canvas: Canvas) {
        when(step){
            Step.Start-> stageStart(canvas)
            Step.Run-> stageRun(canvas)
            Step.ReStart-> stageReStart(canvas)
        }
    }

    override fun stageStart(canvas: Canvas) {
        startButton.draw(canvas)
    }

    override fun stageRun(canvas: Canvas) {
        canvas.drawText("SCORE: $score", 20f, 90f, textPaintSub)
        var heart = ""
        repeat(life) {
            heart += "❤"
        }
        canvas.drawText("LIFE: $heart", 580f, 90f, textPaintSub)
    }

    override fun stageReStart(canvas: Canvas) {
        canvas.drawText("SCORE: $score", 160f, 1290f, textPaintMain)
        canvas.drawText("다시하기", 340f, 1620f, textPaintStep)
    }

    fun isClick(step:Step,x:Float,y:Float):Boolean{
       return when(step){
            Step.Start-> { startButton.isClick(x,y)}
            Step.Run-> {false}
            Step.ReStart-> {startButton.isClick(x,y)}
        }
    }
    fun initStatus(){
        score = 0
        life = 3
    }
}