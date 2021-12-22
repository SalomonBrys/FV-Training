package com.demo.myfundvisorykmmapp

import kotlinx.cinterop.convert
import platform.UIKit.UIDevice
import platform.darwin.*

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun runDelayed(ms: Long, cb: () -> Unit) {
    dispatch_after(
        dispatch_time(DISPATCH_TIME_NOW, (ms.toULong() * NSEC_PER_MSEC).convert()),
        dispatch_get_main_queue(),
        cb
    )
}