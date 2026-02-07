package com.vlegall.sochiefapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform