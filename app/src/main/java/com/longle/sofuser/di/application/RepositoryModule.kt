package com.longle.sofuser.di.application

import com.data.UserApi
import com.data.mapper.UserMapper
import com.data.repository.UserRepositoryImpl
import com.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun provideUserRepository(userApi: UserApi, userMapper: UserMapper): UserRepository {
    return UserRepositoryImpl(userApi, userMapper)
  }

}
