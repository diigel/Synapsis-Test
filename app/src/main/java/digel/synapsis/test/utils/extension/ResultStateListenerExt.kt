package digel.synapsis.test.utils.extension

import digel.synapsis.test.utils.state.ResultState
import digel.synapsis.test.utils.state.ResultStateListener


fun <T> ResultState<T>.setOnListener(listener: ResultStateListener<T>) {
    when (this) {
        is ResultState.Loading -> {}
        is ResultState.Success -> listener.onSuccess(this.data)
        is ResultState.Failed -> listener.onFailed(this.exception)
    }
}