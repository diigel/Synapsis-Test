package digel.synapsis.test.utils.extension

import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

suspend fun <T: Any> fetchState(call: suspend () -> ResultState<T>): ResultState<T> {
    return try {
        call.invoke()
    } catch (e: ConnectException) {
        ResultState.Failed(e)
    } catch (e: Exception) {
        ResultState.Failed(e)
    } catch (e: Throwable) {
        ResultState.Failed(e)
    }
}

suspend fun <T: Any> fetch( call: suspend () -> T): Flow<ResultState<T>> = flow {
    emit(ResultState.Loading())
    try {
        val result = call.invoke()
        emit(ResultState.Success(result))
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        emit(ResultState.Failed(throwable))
    }
}