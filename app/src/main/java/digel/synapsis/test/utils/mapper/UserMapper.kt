package digel.synapsis.test.utils.mapper

import digel.synapsis.test.data.local.entity.UserEntity
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.domain.uimodel.UserUiModel

object UserMapper {

    fun AuthRequest.mapToUserEntity() : UserEntity {
        return UserEntity(username = username.orEmpty(), password = password.orEmpty())
    }
}