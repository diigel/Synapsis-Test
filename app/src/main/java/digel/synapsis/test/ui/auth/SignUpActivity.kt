package digel.synapsis.test.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.databinding.ActivitySignupBinding
import digel.synapsis.test.ui.viewmodel.SignUpViewModel
import digel.synapsis.test.utils.extension.hideKeyboard
import digel.synapsis.test.utils.extension.showSnackBar
import digel.synapsis.test.utils.extension.textChanges
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivitySignupBinding
    private val viewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.run {

            etUsername.textChanges()
                .onEach{
                    isEnableButton()
                }.launchIn(lifecycleScope)

            etPassword.textChanges()
                .onEach{
                    isEnableButton()
                }.launchIn(lifecycleScope)

            btnSignUp.setOnClickListener {
                hideKeyboard()
                viewModel.requestSignUp(
                    AuthRequest(
                        username = etUsername.text.toString(),
                        password = etPassword.text.toString()
                    )
                )
            }

            txtSignup.setOnClickListener {
                hideKeyboard()
                finish()
            }

            viewModel.signUpEvent.observe(this@SignUpActivity){ result ->
                if (result){
                    viewBinding.root.showSnackBar("Success, SignUp Account")
                    lifecycleScope.launch {
                        hideKeyboard()
                        delay(1000)
                        finish()
                    }
                }else {
                    viewBinding.root.showSnackBar("Failed")
                }
            }
        }
    }

    private fun isEnableButton() = viewBinding.run {
        btnSignUp.isEnabled = etUsername.text.toString().isNotEmpty() && etPassword.text.toString().isNotEmpty()
    }
}