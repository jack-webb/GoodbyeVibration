package me.jackwebb.goodbyevibration.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.jackwebb.goodbyevibration.database.AppDao
import me.jackwebb.goodbyevibration.repo.AppRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAppRepository(appDao: AppDao) = AppRepository(appDao)
}