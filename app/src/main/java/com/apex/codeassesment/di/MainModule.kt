package com.apex.codeassesment.di

import android.content.Context
import android.content.SharedPreferences
import com.apex.codeassesment.ApiClient
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.interfaces.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
object MainModule {

  @Provides
  fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
  }

  @Provides
  fun providePreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager = PreferencesManager(sharedPreferences)

  @Provides
  fun provideMoshi(): Moshi = Moshi.Builder().build()

  @Provides
  fun provideRemoteDataSource(apiClient : ApiClient) : RemoteDataSource = RemoteDataSource(apiClient)

  @Provides
  fun provideLocalDataSource(preferencesManager: PreferencesManager , moshi: Moshi) : LocalDataSource = LocalDataSource(preferencesManager , moshi)

  @Provides
  fun provideApiClient() : ApiClient = ApiClient

}