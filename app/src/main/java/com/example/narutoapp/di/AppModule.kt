package com.example.narutoapp.di

import com.example.narutoapp.data.NarutoApi
import com.example.narutoapp.repository.NarutoRepository
import com.example.narutoapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNarutoRepository(
        api: NarutoApi
    ) = NarutoRepository(api)

    @Singleton
    @Provides
    fun provideNarutoApi(): NarutoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NarutoApi::class.java)
    }
}