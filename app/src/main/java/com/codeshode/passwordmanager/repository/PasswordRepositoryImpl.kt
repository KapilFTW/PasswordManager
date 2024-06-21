package com.codeshode.passwordmanager.repository

import com.codeshode.passwordmanager.data.db.PasswordDB
import com.codeshode.passwordmanager.data.db.PasswordDao
import com.codeshode.passwordmanager.data.model.Password
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(private val passwordDao: PasswordDao): PasswordRepository {
    override fun getAllPasswords(): Flow<List<Password>> {
        return passwordDao.getAllPasswords()
    }

    override suspend fun upsertPassword(password: Password) {
        passwordDao.upsertPassword(password)
    }

    override suspend fun deletePassword(id: Int) {
        passwordDao.deletePassword(id)
    }
}