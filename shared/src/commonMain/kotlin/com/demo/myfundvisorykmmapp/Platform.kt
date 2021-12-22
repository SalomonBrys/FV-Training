package com.demo.myfundvisorykmmapp

expect class Platform() {
    val platform: String
}

expect fun runDelayed(ms: Long, cb: () -> Unit)
