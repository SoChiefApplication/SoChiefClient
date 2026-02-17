package fr.vlegall.sochief.client.configuration

class MemoryApiConfigProvider(initial: ApiConfig? = null) : ApiConfigProvider {
    @Volatile
    private var cfg: ApiConfig? = initial
    fun update(newCfg: ApiConfig?) {
        cfg = newCfg
    }

    override suspend fun get(): ApiConfig? = cfg
}