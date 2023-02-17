package digel.synapsis.test.data.di

import android.app.Application
import androidx.room.Room
import digel.synapsis.test.domain.dao.UserDao
import digel.synapsis.test.data.local.database.UserDatabase
import digel.synapsis.test.data.local.repository.AuthRepository
import digel.synapsis.test.data.local.repository.AuthRepositoryImp
import digel.synapsis.test.ui.viewmodel.SignInViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DataModule {

    val userDatabase = module {

        fun provideDatabase(application: Application) : UserDatabase {
            return Room.databaseBuilder(
                application,
                UserDatabase::class.java,"user.db"
            ).fallbackToDestructiveMigration().build()
        }

        fun provideUserDao(database: UserDatabase) : UserDao {
            return database.userDao()
        }

        single {
            provideDatabase(androidApplication())
        }
        single {   provideUserDao(get()) }
    }

    val authRepository = module {
        single<AuthRepository>{ AuthRepositoryImp(get()) }
    }
}
