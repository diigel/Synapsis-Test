package digel.synapsis.test.data.di

import digel.synapsis.test.domain.usecase.MapUseCase
import digel.synapsis.test.domain.usecase.SignInUseCase
import digel.synapsis.test.domain.usecase.SignUpUseCase
import digel.synapsis.test.ui.viewmodel.SignInViewModel
import digel.synapsis.test.ui.viewmodel.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val signInViewModel = module {
        viewModel { SignInViewModel(androidApplication()) }
    }

    val signUpViewModel = module {
        viewModel { SignUpViewModel(androidApplication()) }
    }

}