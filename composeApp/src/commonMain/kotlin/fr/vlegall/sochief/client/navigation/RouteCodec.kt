package fr.vlegall.sochief.client.navigation

import java.net.URLDecoder
import java.net.URLEncoder

object RouteCodec {
    fun encode(value: String): String = URLEncoder.encode(value, Charsets.UTF_8.name())
    fun decode(value: String): String = URLDecoder.decode(value, Charsets.UTF_8.name())
}