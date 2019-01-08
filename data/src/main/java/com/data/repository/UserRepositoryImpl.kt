package com.data.repository

import com.data.UserApi
import com.data.mapper.UserMapper
import com.domain.model.User
import com.domain.repository.UserRepository
import io.reactivex.Single

/**
 * UserRepositoryImpl implements UserRepository
 *
 * UserRepository is responsible for retrieving the User info from the api layer
 */
class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userMapper: UserMapper
) : UserRepository {

  /**
   * getUserList
   * @return Single<List<User>> list of nearby Users
   * @param lat Latitude of the user
   * @param lng Longitude of the user
   * @param offset the offset of the list to be retrieved
   * @param limit the number of Users to be retrieved
   */
  override fun getUserList(lat: Double, lng: Double, offset: Int, limit: Int): Single<List<User>> {
    return userApi.getUserList(lat, lng, offset, limit)
        .map { userMapper.map(it) }
  }

  /**
   * getUser
   * @return Single<User> User details
   * @param id the id of User to be retrieved
   */
  override fun getUser(id: Int): Single<User> {
    return userApi.getUser(id)
        .map { userMapper.map(it) }
  }
}