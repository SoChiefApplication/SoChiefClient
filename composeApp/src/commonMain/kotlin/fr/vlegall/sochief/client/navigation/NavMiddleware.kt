package fr.vlegall.sochief.client.navigation

fun interface NavMiddleware {
    fun intercept(request: NavRequest, next: (NavRequest) -> Unit)
}

data class NavRequest(
    val from: AppRoute,
    val to: AppRoute,
    val extras: Map<String, Any?> = emptyMap()
)