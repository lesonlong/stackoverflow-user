package com.domain.repository

import com.domain.model.User
import io.reactivex.Single

interface UserRepository {

  fun getUserList(lat: Double, lng: Double, offset: Int, limit: Int): Single<List<User>>

  fun getUser(id: Int): Single<User>

}
