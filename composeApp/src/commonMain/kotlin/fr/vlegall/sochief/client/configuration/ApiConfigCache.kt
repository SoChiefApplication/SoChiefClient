package fr.vlegall.sochief.client.configuration

class ApiConfigCache(initial: ApiConfig? = null) {
    @Volatile
    private var cfg: ApiConfig? = initial

    fun get(): ApiConfig? = cfg
    fun update(newCfg: ApiConfig?) {
        cfg = newCfg
    }

    fun clear() {
        cfg = null
    }
}