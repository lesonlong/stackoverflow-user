package com.domain.usecase

import com.domain.model.User
import com.domain.repository.UserRepository
import com.domain.usecase.GetUserListUseCase.Result.Failure
import com.domain.usecase.GetUserListUseCase.Result.Loading
import com.domain.usecase.GetUserListUseCase.Result.Success
import io.reactivex.Observable
import javax.inject.Inject

/**
 * GetUserListUseCase
 *
 * Used to retrieve the list of Users and return the values in a Result object
 * Additional business logic related to the list can be performed here
 * @param UserRepository repository to get the list from
 */
class GetUserListUseCase @Inject constructor(private val userRepository: UserRepository) {

  sealed class Result {
    object Loading : Result()
    data class Success(val users: List<User>) : Result()
    data class Failure(val throwable: Throwable) : Result()
  }

  fun execute(lat: Double, lng: Double, offset: Int, limit: Int): Observable<Result> {
    return userRepository.getUserList(lat, lng, offset, limit)
        .toObservable()
        .map { Success(it) as Result }
        .onErrorReturn { Failure(it) }
        .startWith(Loading)
  }
}
