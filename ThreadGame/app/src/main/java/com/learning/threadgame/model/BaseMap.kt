package com.learning.threadgame.model

import android.content.Context
import android.graphics.BitmapFactory
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.learning.threadgame.R
import com.learning.threadgame.thread.MyThread

class BaseMap(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    private val myHolder: SurfaceHolder = holder
    lateinit var myThread: MyThread
    lateinit var bgImg: BackgroundImg
    init {
        myHolder.addCallback(this)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        bgImg = BackgroundImg(BitmapFactory.decodeResource(resources, R.drawable.ac_bg_run))
        myThread = MyThread(myHolder,this)
        myThread.threadRun = true
        myThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        myThread.threadRun = false
        var retry = true
        while (retry) {
            try {
                myThread.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}