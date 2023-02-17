package digel.synapsis.test.data.local.repository

import digel.synapsis.test.data.local.entity.UserEntity
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.domain.dao.UserDao
import digel.synapsis.test.utils.extension.fetch
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepositoryImp (private val userDao: UserDao) : AuthRepository {
    override suspend fun userLocalLogin(authRequest: AuthRequest): Flow<ResultState<UserEntity>> {
        return fetch {
            userDao.getUserByPassword(authRequest.password.orEmpty())
        }
    }

    override suspend fun userLocalRegister(userEntity: UserEntity): Flow<Boolean> {
        return flow {
            userDao.insertUser(userEntity)
            emit(true)
        }.catch { emit(false) }
    }
}