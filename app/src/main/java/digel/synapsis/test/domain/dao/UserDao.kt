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

    @Query("select * from user where username = :username AND password = :password")
    suspend fun checkUser(username : String, password: String) : UserEntity?

}