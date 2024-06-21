package com.codeshode.passwordmanager.di

import com.codeshode.passwordmanager.repository.PasswordRepository
import com.codeshode.passwordmanager.repository.PasswordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {
    @Binds
    @Singleton
    abstract fun bindsPasswordRepository(repository: PasswordRepositoryImpl): PasswordRepository
}