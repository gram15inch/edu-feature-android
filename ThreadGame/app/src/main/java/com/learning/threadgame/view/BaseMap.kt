package com.learning.threadgame.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.learning.threadgame.R
import com.learning.threadgame.thread.MapThread

import timber.log.Timber


class BaseMap(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    private val myHolder: SurfaceHolder = holder
    lateinit var mainThread: MapThread
    lateinit var uiStatus: UiStatus
    lateinit var potatoes: List<Potato>
    val bgImgs: List<BackImg>
    val backMusic: MediaPlayer
    val potatoImgs: List<Bitmap>
    var stage = Step.Start

    init {
        myHolder.addCallback(this)
        bgImgs = listOf(
            BackImg(BitmapFactory.decodeResource(resources, R.drawable.ac_bg_start)),
            BackImg(BitmapFactory.decodeResource(resources, R.drawable.ac_bg_run)),
            BackImg(BitmapFactory.decodeResource(resources, R.drawable.ac_bg_restart)),
        )
        potatoImgs = listOf(
            BitmapFactory.decodeResource(resources, R.drawable.ac_pto_1),
            BitmapFactory.decodeResource(resources, R.drawable.ac_pto_2),
            BitmapFactory.decodeResource(resources, R.drawable.ac_pto_3),
            BitmapFactory.decodeResource(resources, R.drawable.ac_pto_4),
            BitmapFactory.decodeResource(resources, R.drawable.ac_pto_5),
            BitmapFactory.decodeResource(resources, R.drawable.ac_pto_6),
            BitmapFactory.decodeResource(resources, R.drawable.ac_stone),
        )
        backMusic = MediaPlayer.create(context, R.raw.bg_run)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {

        val boxX = 260
        val boxY = 1100
        potatoes = listOf(
            Potato(potatoImgs, boxX, boxY),
            Potato(potatoImgs, boxX + 200, boxY),
            Potato(potatoImgs, boxX + 400, boxY),
            Potato(potatoImgs, boxX, 1300),
            Potato(potatoImgs, boxX + 200, boxY + 200),
            Potato(potatoImgs, boxX + 400, boxY + 200),
            Potato(potatoImgs, boxX, 1500),
            Potato(potatoImgs, boxX + 200, boxY + 400),
            Potato(potatoImgs, boxX + 400, boxY + 400),
        )
        uiStatus = UiStatus()

        backMusic.seekTo(2000)
        backMusic.start()

        mainThread = MapThread(myHolder, this)
        mainThread.threadRun = true
        mainThread.start()

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        mainThread.threadRun = false
        var retry = true
        while (retry) {
            try {
                mainThread.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (stage) {
                    Step.Start -> {
                        checkClickStepButton(event.x, event.y)
                    }
                    Step.Run -> {
                        checkClickPotatoes(event.x, event.y)
                    }
                    Step.ReStart -> {
                        checkClickStepButton(event.x, event.y)
                    }
                }

                return true
            }
        }

        return false
    }

    private fun checkClickPotatoes(eventX: Float, eventY: Float) {
        potatoes.firstOrNull() { potato -> potato.isClick(eventX, eventY) }
            ?.apply {
                if (digPotato())
                    uiStatus.score += 10
                else
                    uiStatus.life--
                initPotato()
            }
        if (uiStatus.life <= 0) {
            stage = Step.ReStart
            if (backMusic.isPlaying) {
                backMusic.pause()
                Timber.tag("music").d("pause")
            }

        }
    }

    private fun checkClickStepButton(eventX: Float, eventY: Float) {
        if (uiStatus.isClick(stage, eventX, eventY)) {
            initStageRun()
            stage = Step.Run
            if (!backMusic.isPlaying) {
                backMusic.seekTo(2000)
                backMusic.start()
                Timber.tag("music").d("reStart")
            } else
                Timber.tag("music").d("start")

        }
    }

    private fun initStageRun() {
        for (pto in potatoes)
            pto.initPotato()
        uiStatus.initStatus()
    }
}