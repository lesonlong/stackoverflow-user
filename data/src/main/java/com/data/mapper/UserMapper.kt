package com.data.mapper

import com.data.response.UserResponse
import com.domain.model.User
import javax.inject.Inject

/**
 * UserMapper maps the User response objects to the User model object
 */
class UserMapper @Inject constructor() {

  fun map(responseList: List<UserResponse>): List<User> {
    return responseList.map { (map(it)) }
  }

  fun map(response: UserResponse): User {
    return User(
            id = response.id,
            name = response.name,
            description = response.description,
            coverImgUrl = response.coverImgUrl,
            status = response.status,
            deliveryFee = response.deliveryFee
    )
  }
}
