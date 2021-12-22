package com.demo.myfundvisorykmmapp

import android.os.Handler
import android.os.Looper

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun runDelayed(ms: Long, cb: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(cb, ms)
}
