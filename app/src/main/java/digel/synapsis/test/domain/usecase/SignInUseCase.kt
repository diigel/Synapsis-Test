package digel.synapsis.test.domain.usecase

import digel.synapsis.test.base.BaseUseCase
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.data.local.repository.AuthRepository
import digel.synapsis.test.domain.uimodel.UserUiModel
import digel.synapsis.test.utils.extension.fetchState
import digel.synapsis.test.utils.mapper.UserMapper.mapToUserUiModel
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SignInUseCase(private val userRepository: AuthRepository, dispatcher: CoroutineDispatcher) : BaseUseCase<AuthRequest, UserUiModel>(dispatcher) {
    override suspend fun execute(param: AuthRequest): Flow<ResultState<UserUiModel>> {
        return userRepository.userLocalLogin(param).map { state ->
            fetchState {
                when (state){
                    is ResultState.Loading -> {
                        ResultState.Loading()
                    }
                    is ResultState.Success -> {
                        ResultState.Success(state.data.mapToUserUiModel())
                    }

                    is ResultState.Failed -> {
                        ResultState.Failed(Throwable())
                    }
                }
            }
        }
    }
}