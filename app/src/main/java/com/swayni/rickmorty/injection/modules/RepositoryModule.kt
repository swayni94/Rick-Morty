package com.swayni.rickmorty.injection.modules

import com.swayni.rickmorty.service.remote.RemoteDataSourceImpl
import com.swayni.rickmorty.service.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository():Repository{
        val remoteDataSource = RemoteDataSourceImpl()
        return Repository(remoteDataSource)
    }
}