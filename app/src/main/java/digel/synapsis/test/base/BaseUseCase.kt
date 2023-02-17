package digel.synapsis.test.base

import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<P, R : Any> constructor(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(param: P): Flow<ResultState<R>> {
        return execute(param)
            .catch { emit(ResultState.Failed(it)) }
            .flowOn(dispatcher)
    }

    @Throws(RuntimeException::class)
    abstract suspend fun execute(param: P): Flow<ResultState<R>>

}