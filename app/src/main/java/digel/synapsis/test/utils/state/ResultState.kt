package digel.synapsis.test.utils.state

import digel.synapsis.test.utils.constant.ConstantState.STATE_LOADING

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Failed<T>(val exception: Throwable) : ResultState<T>()
    data class Loading<T>(val loading: String = STATE_LOADING) : ResultState<T>()
}