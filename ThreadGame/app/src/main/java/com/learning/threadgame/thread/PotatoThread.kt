package com.learning.threadgame.thread

class PotatoThread : Thread() {
    private var _grown = 0
    val grown get() = _grown
    private var speed = (1..9).random()
    private var isLive = true
    override fun run() {
        while (isLive) {
            sleep((2000 - (speed * 100)).toLong())
            _grown++
            if (grown >= 6)
                isLive = false
        }
    }
}