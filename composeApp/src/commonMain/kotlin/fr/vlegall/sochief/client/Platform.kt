package fr.vlegall.sochief.client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform