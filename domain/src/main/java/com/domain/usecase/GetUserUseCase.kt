package com.domain.usecase

import com.domain.model.User
import com.domain.repository.UserRepository
import com.domain.usecase.GetUserUseCase.Result.Failure
import com.domain.usecase.GetUserUseCase.Result.Loading
import com.domain.usecase.GetUserUseCase.Result.Success
import io.reactivex.Observable
import javax.inject.Inject

/**
 * GetUserUseCase
 *
 * Used to retrieve the User from the id provided and return it in a Result object
 * Additional business logic related to the user can be performed here
 * @param userRepository repository to get the user from
 */
class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

  sealed class Result {
    object Loading : Result()
    data class Success(val user: User) : Result()
    data class Failure(val throwable: Throwable) : Result()
  }

  fun execute(id: Int): Observable<Result> {
    return userRepository.getUser(id)
        .toObservable()
        .map { Success(it) as Result }
        .onErrorReturn { Failure(it) }
        .startWith(Loading)
  }
}
