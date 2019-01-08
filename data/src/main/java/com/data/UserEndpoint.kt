package com.data

import com.data.response.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserEndpoint {
  @GET("restaurant/")
  fun getUserList(@Query("lat") lat: Double, @Query("lng") lng: Double,
      @Query("offset") offset: Int, @Query("limit") limit: Int): Single<List<UserResponse>>

  @GET("restaurant/{id}")
  fun getUser(@Path("id") id: Int): Single<UserResponse>
}
