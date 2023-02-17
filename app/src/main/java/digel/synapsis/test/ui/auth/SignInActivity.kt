package digel.synapsis.test.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.databinding.ActivitySigninBinding
import digel.synapsis.test.ui.viewmodel.SigninViewModel
import digel.synapsis.test.utils.extension.textChanges
import kotlinx.coroutines.flow.*

class SignInActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySigninBinding
    private val viewModel: SigninViewModel by viewModels()
    private val validParams = mutableListOf("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.run {
            etUsername.textChanges()
                .filterNot { it?.isNotBlank() == true }
                .mapNotNull { it.toString() }
                .debounce(300)
                .distinctUntilChanged()
                .onEach { username ->
                    validParams[0] = username.ifEmpty { "" }
                }.launchIn(lifecycleScope)

            etPassword.textChanges()
                .filterNot { it?.isNotBlank() == true }
                .mapNotNull { it.toString() }
                .debounce(300)
                .distinctUntilChanged()
                .onEach { password ->
                    validParams[1] = password.ifEmpty { "" }
                }.launchIn(lifecycleScope)
            viewBinding.btnSign.isEnabled = validParams.isNotEmpty()

            btnSign.setOnClickListener {
                viewModel.requestLogin(
                    AuthRequest(
                        username = validParams[0],
                        password = validParams[1]
                    )
                )
            }
            txtSignup.setOnClickListener {
                startActivity(Intent(this@SignInActivity,SignupActivity::class.java))
            }
        }
    }


}