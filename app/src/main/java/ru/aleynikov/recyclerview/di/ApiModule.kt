package ru.aleynikov.recyclerview.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aleynikov.recyclerview.api.RnMAPI

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    fun provideRnMAPI(): RnMAPI {
        return RnMAPI.getInstance()
    }
}