package com.bitflyer.testapp.util

import android.view.View

class ThrottleFirstOnClickListener(
    private val interval: Int,
    private val listenerBlock: (View) -> Unit
): View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val time = System.currentTimeMillis()
        if (time - lastClickTime >= interval) {
            lastClickTime = time
            listenerBlock(v)
        }
    }
}

fun View.setOnClickListener(interval: Int, listenerBlock: (View) -> Unit) =
    setOnClickListener(ThrottleFirstOnClickListener(interval, listenerBlock))