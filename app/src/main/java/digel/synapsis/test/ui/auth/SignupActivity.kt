package digel.synapsis.test.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import digel.synapsis.test.databinding.ActivitySignupBinding
import digel.synapsis.test.ui.viewmodel.SigninViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivitySignupBinding
    private val viewModel : SigninViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.run {

        }
    }
}