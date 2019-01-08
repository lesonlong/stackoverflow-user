package com.longle.sofuser.di.application

import com.data.UserEndpoint
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EndpointModule {

  @Provides
  @Singleton
  fun provideUserEndpoint(retrofit: Retrofit): UserEndpoint {
    return retrofit
        .create(UserEndpoint::class.java)
  }
}
