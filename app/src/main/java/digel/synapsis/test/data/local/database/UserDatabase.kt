package digel.synapsis.test.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import digel.synapsis.test.data.local.entity.UserEntity
import digel.synapsis.test.domain.dao.UserDao

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}