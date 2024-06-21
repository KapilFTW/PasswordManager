package com.codeshode.passwordmanager.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.codeshode.passwordmanager.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Upsert
    suspend fun upsertPassword(password: Password)

    @Query("DELETE FROM passwords WHERE id=:id ")
    suspend fun deletePassword(id:Int)

    @Query("select*from passwords where id=:id")
    fun getPasswordDataWithId(id: String): Flow<Password>

    @Query("select*from passwords")
    fun getAllPasswords(): Flow<List<Password>>
}