package digel.synapsis.test

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import digel.synapsis.test.data.di.DataModule
import digel.synapsis.test.data.di.UseCaseModule
import digel.synapsis.test.data.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
        fun getApplicationContext() = instance.applicationContext
    }

    override fun getApplicationContext(): Context {
        instance = this
        return super.getApplicationContext()
    }
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
                    DataModule.mapRepository,
                    DataModule.service,
                    ViewModelModule.signInViewModel,
                    ViewModelModule.signUpViewModel,
                    UseCaseModule.signInUseCase,
                    UseCaseModule.signUnUseCase,
                    UseCaseModule.mapsUseCase
                )
            )
        }
    }
}