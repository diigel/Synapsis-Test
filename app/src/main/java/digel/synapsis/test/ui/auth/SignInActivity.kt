package digel.synapsis.test.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import digel.synapsis.test.data.local.entity.request.AuthRequest
import digel.synapsis.test.databinding.ActivitySigninBinding
import digel.synapsis.test.ui.feature.PageAActivity
import digel.synapsis.test.ui.viewmodel.SignInViewModel
import digel.synapsis.test.utils.extension.hideKeyboard
import digel.synapsis.test.utils.extension.showSnackBar
import digel.synapsis.test.utils.extension.textChanges
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SignInActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySigninBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySigninBinding.inflate(layoutInflater)
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

            btnSign.setOnClickListener {
                hideKeyboard()
                viewModel.requestLogin(
                    AuthRequest(
                        username = etUsername.text.toString(),
                        password = etPassword.text.toString()
                    )
                )
            }

            txtSignup.setOnClickListener {
                startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))
            }

            viewModel.loginEvent.observe(this@SignInActivity){ result ->
                if (result){
                    hideKeyboard()
                    startActivity(Intent(this@SignInActivity, PageAActivity::class.java))
                    finishAffinity()
                }else {
                    viewBinding.root.showSnackBar("User Not Found")
                }
            }

        }
    }

    private fun isEnableButton() = viewBinding.run {
        btnSign.isEnabled = etUsername.text.toString().isNotEmpty() && etPassword.text.toString().isNotEmpty()
    }

}