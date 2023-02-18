package digel.synapsis.test.data.local.repository

import digel.synapsis.test.data.local.entity.UserEntity
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.domain.dao.UserDao
import digel.synapsis.test.utils.extension.fetch
import digel.synapsis.test.utils.state.ResultState
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImp (private val userDao: UserDao) : AuthRepository {
    override suspend fun userLocalLogin(authRequest: AuthRequest): Flow<ResultState<Boolean>> {
        return fetch {
            val data = userDao.checkUser(authRequest.username.orEmpty(),authRequest.password.orEmpty())
           return@fetch data != null
        }
    }

    override suspend fun userLocalRegister(userEntity: UserEntity): Flow<ResultState<Boolean>> {
        return fetch {
            userDao.insertUser(userEntity)
            val checkIsInsert = userDao.checkUser(userEntity.username , userEntity.password)
            return@fetch checkIsInsert != null
        }
    }
}