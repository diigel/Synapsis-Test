package digel.synapsis.test

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import digel.synapsis.test.data.di.DataModule
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
                    DataModule.signinViewModel
                )
            )
        }
    }
}