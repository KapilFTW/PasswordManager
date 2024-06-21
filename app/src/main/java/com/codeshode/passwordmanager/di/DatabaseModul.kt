package com.codeshode.passwordmanager.di

import android.app.Application
import com.codeshode.passwordmanager.data.db.PasswordDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePasswordDB(application: Application): PasswordDB {
        return PasswordDB.getInstance(application)
    }

    @Provides
    fun providePasswordDao(database: PasswordDB) = database.passwordDao
}