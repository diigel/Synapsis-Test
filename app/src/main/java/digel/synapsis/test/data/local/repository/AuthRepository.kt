package digel.synapsis.test.data.local.repository

import digel.synapsis.test.data.local.entity.UserEntity
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun userLocalLogin(authRequest: AuthRequest) : Flow<ResultState<Boolean>>

    suspend fun userLocalRegister(userEntity: UserEntity) : Flow<ResultState<Boolean>>
}