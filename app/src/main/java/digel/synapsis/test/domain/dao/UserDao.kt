package digel.synapsis.test.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import digel.synapsis.test.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(entity: UserEntity)

    @Query("select * from user where password = :password")
    suspend fun getUserByPassword(password: String) : UserEntity

}