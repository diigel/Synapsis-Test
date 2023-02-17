package digel.synapsis.test.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.domain.uimodel.UserUiModel
import digel.synapsis.test.domain.usecase.SignInUseCase
import digel.synapsis.test.utils.extension.setOnListener
import digel.synapsis.test.utils.state.ResultStateListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SigninViewModel(private val userUseCase: SignInUseCase) : ViewModel() {

    private val _loginEvent = MutableLiveData<UserUiModel>()
    val loginEvent get() = _loginEvent

    fun requestLogin(authRequest: AuthRequest) {
        viewModelScope.launch {
            userUseCase(authRequest).collectLatest { resultState ->
                resultState.setOnListener(object : ResultStateListener<UserUiModel> {
                    override fun onSuccess(data: UserUiModel) {
                        _loginEvent.value = data
                    }

                    override fun onFailed(t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }
        }
    }
}