package fr.vlegall.sochief.client.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class NavController(
    start: AppRoute,
    private var middlewares: List<NavMiddleware> = emptyList()
) {
    private val _backStack: SnapshotStateList<AppRoute> = mutableStateListOf(start)
    val backStack: List<AppRoute> get() = _backStack
    val current: AppRoute get() = _backStack.last()

    private fun runMiddlewares(request: NavRequest, terminal: (NavRequest) -> Unit) {
        val chainList = middlewares // snapshot
        val chain = chainList.asReversed().fold(terminal) { next, mw ->
            { req -> mw.intercept(req, next) }
        }
        chain(request)
    }

    fun bootstrap(extras: Map<String, Any?> = emptyMap()) {
        val request = NavRequest(from = current, to = current, extras = extras)

        val terminal: (NavRequest) -> Unit = { req ->
            if (req.to != current) {
                _backStack[_backStack.lastIndex] = req.to
            }
        }

        runMiddlewares(request, terminal)
    }

    fun canGoBack(): Boolean = _backStack.size > 1

    fun goBack(): Boolean {
        if (!canGoBack()) return false
        _backStack.removeAt(_backStack.lastIndex)
        return true
    }

    fun resetTo(route: AppRoute) {
        _backStack.clear()
        _backStack.add(route)
    }

    fun navigate(to: AppRoute, extras: Map<String, Any?> = emptyMap()) {
        val request = NavRequest(from = current, to = to, extras = extras)

        val terminal: (NavRequest) -> Unit = { req -> _backStack.add(req.to) }

        runMiddlewares(request, terminal)
    }

    fun withMiddleware(mw: NavMiddleware) {
        middlewares = middlewares + mw
    }
}