package digel.synapsis.test.utils.mapper

import digel.synapsis.test.data.local.entity.UserEntity
import digel.synapsis.test.domain.uimodel.UserUiModel

object UserMapper {

    fun UserEntity.mapToUserUiModel() : UserUiModel {
        return UserUiModel(id, username, password)
    }
}