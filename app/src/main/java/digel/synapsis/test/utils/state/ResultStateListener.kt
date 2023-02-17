package digel.synapsis.test.utils.state

interface ResultStateListener<T> {
    fun onSuccess(data: T)
    fun onFailed(t: Throwable)
}