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
    lateinit var potatos: List<Potato>
    val potatoImgs = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.ac_pto_1),
        BitmapFactory.decodeResource(resources, R.drawable.ac_pto_2),
        BitmapFactory.decodeResource(resources, R.drawable.ac_pto_3),
        BitmapFactory.decodeResource(resources, R.drawable.ac_pto_4),
        BitmapFactory.decodeResource(resources, R.drawable.ac_pto_5),
        BitmapFactory.decodeResource(resources, R.drawable.ac_pto_6),
        BitmapFactory.decodeResource(resources, R.drawable.ac_stone),
    )
    init {
        myHolder.addCallback(this)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        bgImg = BackgroundImg(BitmapFactory.decodeResource(resources, R.drawable.ac_bg_run))
        val boxX= 260
        val boxY= 1100

        potatos = listOf(
            Potato(potatoImgs,boxX,boxY),
            Potato(potatoImgs,boxX+200,boxY),
            Potato(potatoImgs,boxX+400,boxY),
            Potato(potatoImgs,boxX,1300),
            Potato(potatoImgs,boxX+200,boxY+200),
            Potato(potatoImgs,boxX+400,boxY+200),
            Potato(potatoImgs,boxX,1500),
            Potato(potatoImgs,boxX+200,boxY+400),
            Potato(potatoImgs,boxX+400,boxY+400),
        )
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