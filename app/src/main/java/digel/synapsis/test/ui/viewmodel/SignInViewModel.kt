package digel.synapsis.test.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.domain.usecase.SignInUseCase
import digel.synapsis.test.utils.extension.setOnListener
import digel.synapsis.test.utils.state.ResultStateListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(KoinApiExtension::class)
class SignInViewModel(context: Application) : AndroidViewModel(context), KoinComponent {

    private val userUseCase: SignInUseCase by inject()

    private val _loginEvent = MutableLiveData<Boolean>()
    val loginEvent get() = _loginEvent

    fun requestLogin(authRequest: AuthRequest) {
        viewModelScope.launch {
            userUseCase(authRequest).collectLatest { resultState ->
                resultState.setOnListener(object : ResultStateListener<Boolean> {
                    override fun onSuccess(data: Boolean) {
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