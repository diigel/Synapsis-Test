package digel.synapsis.test.domain.usecase

import digel.synapsis.test.data.local.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher

class SignUpUseCase(private val authRepository: AuthRepository, dispatcher: CoroutineDispatcher)  {

}