package digel.synapsis.test

import android.app.Application
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import digel.synapsis.test.data.di.DataModule
import digel.synapsis.test.data.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            koin.loadModules(
                listOf(
                    DataModule.userDatabase,
                    DataModule.authRepository,
                    ViewModelModule.signInViewModel,
                    ViewModelModule.signUpViewModel,
                    ViewModelModule.signInUseCase,
                    ViewModelModule.signUnUseCase
                )
            )
        }
    }
}