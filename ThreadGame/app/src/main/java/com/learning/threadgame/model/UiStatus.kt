package com.learning.threadgame.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface


class UiStatus : Stage {
    var score = 0
    var life = 3
    private val startButton = StepButton("시작",420,1580)
    private val reStartButton = StepButton("재시작",340,1620)

    var textPaintSub = Paint().also {
        it.color = Color.WHITE
        it.textSize = 80f
    }
    var textPaintMain = Paint().also {
        it.color = Color.WHITE
        it.textSize = 120f
    }
    var textPainTitle= Paint().also {
        it.color = Color.WHITE
        it.textSize = 130f
        it.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    fun draw(step:Step,canvas: Canvas) {
        when(step){
            Step.Start-> stageStart(canvas)
            Step.Run-> stageRun(canvas)
            Step.ReStart-> stageReStart(canvas)
        }
    }

    override fun stageStart(canvas: Canvas) {
        canvas.drawText("감자 타이쿤",230f,1290f,textPainTitle)
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
        reStartButton.draw(canvas)
    }

    fun isClick(step:Step,x:Float,y:Float):Boolean{
       return when(step){
            Step.Start-> { startButton.isClick(x,y)}
            Step.Run-> {false}
            Step.ReStart-> {reStartButton.isClick(x,y)}
        }
    }
    fun initStatus(){
        score = 0
        life = 3
    }
}