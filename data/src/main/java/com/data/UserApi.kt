package com.data

import com.data.response.UserResponse
import io.reactivex.Single
import javax.inject.Inject

class UserApi @Inject constructor(private val userEndpoint: UserEndpoint) {

  fun getUserList(lat: Double, lng: Double, offset: Int, limit: Int): Single<List<UserResponse>> {
    return userEndpoint.getUserList(lat, lng, offset, limit)
  }

  fun getUser(id: Int): Single<UserResponse> {
    return userEndpoint.getUser(id)
  }
}
