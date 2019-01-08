package com.domain.usecase

import com.domain.model.User
import com.domain.repository.UserRepository
import com.domain.usecase.GetUserUseCase.Result.Failure
import com.domain.usecase.GetUserUseCase.Result.Loading
import com.domain.usecase.GetUserUseCase.Result.Success
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.domain.rx.RxJavaTestHooksResetRule

@RunWith(MockitoJUnitRunner::class)
class GetUserUseCaseTest {
  @get:Rule
  var rxJavaTestHooksResetRule = RxJavaTestHooksResetRule()

  @Mock lateinit var userRepository: UserRepository

  private lateinit var sut: GetUserUseCase

  @Before
  fun setUp() {
    sut = GetUserUseCase(userRepository)
  }

  @Test
  fun `retrieves the User`() {
    given(userRepository.getUser(any())).willReturn(Single.error(Throwable()))

    sut.execute(1).test()

    verify(userRepository).getUser(any())
  }

  @Test
  fun `shows loading to start`() {
    given(userRepository.getUser(any())).willReturn(Single.just(mock()))

    sut.execute(1).test().assertValueAt(0) { (it == Loading) }
  }

  @Test
  fun `returns the success result when success retrieving the User`() {
    val User = mock<User>()
    given(userRepository.getUser(any())).willReturn(Single.just(User))

    sut.execute(1).test()
        .assertValueAt(1) { (it as Success).user == User }
  }

  @Test
  fun `returns the failure result when error retrieving the User`() {
    val throwable = Throwable()
    given(userRepository.getUser(any())).willReturn(Single.error(throwable))

    sut.execute(1).test()
        .assertValueAt(1) { (it as Failure).throwable == throwable }
  }
}