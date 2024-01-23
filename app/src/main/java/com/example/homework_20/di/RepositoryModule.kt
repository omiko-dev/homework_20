package com.example.homework_20.di

import com.example.homework_20.data.common.HandleResource
import com.example.homework_20.data.local.dao.UserDao
import com.example.homework_20.data.local.repository.UserDbRepositoryImpl
import com.example.homework_20.domain.repository.UserDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideHandleResource() = HandleResource()

    @Provides
    @Singleton
    fun provideUserDbRepository(userDao: UserDao, handleResource: HandleResource): UserDbRepository =
        UserDbRepositoryImpl(userDao = userDao, handleResource = handleResource)
}