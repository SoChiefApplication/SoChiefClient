package fr.vlegall.sochief.client.navigation.middleware

import fr.vlegall.sochief.client.navigation.AppRoute
import fr.vlegall.sochief.client.navigation.NavMiddleware
import fr.vlegall.sochief.client.navigation.NavRequest

class ConfigurationMiddleware(
    private val isConfigured: () -> Boolean
) : NavMiddleware {
    override fun intercept(request: NavRequest, next: (NavRequest) -> Unit) {
        if (request.to == AppRoute.Configuration) {
            next(request)
            return
        }

        if (!isConfigured()) {
            next(request.copy(to = AppRoute.Configuration))
            return
        }
        next(request)
    }

}