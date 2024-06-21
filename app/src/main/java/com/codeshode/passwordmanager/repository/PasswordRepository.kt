package com.codeshode.passwordmanager.repository

import com.codeshode.passwordmanager.data.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun getAllPasswords(): Flow<List<Password>>
    suspend fun upsertPassword(password: Password)
    suspend fun deletePassword(id: Int)

}