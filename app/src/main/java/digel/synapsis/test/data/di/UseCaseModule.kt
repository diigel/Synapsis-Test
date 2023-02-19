package digel.synapsis.test.data.di

import digel.synapsis.test.domain.usecase.MapUseCase
import digel.synapsis.test.domain.usecase.SignInUseCase
import digel.synapsis.test.domain.usecase.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object UseCaseModule {


    val signInUseCase = module {
        factory { SignInUseCase(get(), Dispatchers.IO) }
    }

    val signUnUseCase = module {
        factory { SignUpUseCase(get(), Dispatchers.IO) }
    }

    val mapsUseCase = module {
        factory { MapUseCase(get(), Dispatchers.IO) }
    }
}