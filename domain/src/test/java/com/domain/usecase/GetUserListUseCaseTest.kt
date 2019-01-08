package com.domain.usecase

import com.domain.model.User
import com.domain.repository.UserRepository
import com.domain.usecase.GetUserListUseCase.Result.Failure
import com.domain.usecase.GetUserListUseCase.Result.Loading
import com.domain.usecase.GetUserListUseCase.Result.Success
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
class GetUserListUseCaseTest {
  @get:Rule
  var rxJavaTestHooksResetRule = RxJavaTestHooksResetRule()

  @Mock lateinit var userRepository: UserRepository

  private lateinit var sut: GetUserListUseCase

  @Before
  fun setUp() {
    sut = GetUserListUseCase(userRepository)
  }

  @Test
  fun `retrieves the User list`() {
    given(userRepository.getUserList(any(), any(), any(), any())).willReturn(Single.error(Throwable()))

    sut.execute(1.1, 1.1, 0, 50).test()

    verify(userRepository).getUserList(any(), any(), any(), any())
  }

  @Test
  fun `shows loading to start`() {
    given(userRepository.getUserList(any(), any(), any(), any())).willReturn(Single.just(mock()))

    sut.execute(1.1, 1.1, 0, 50).test().assertValueAt(0) { (it == Loading) }
  }

  @Test
  fun `returns the success result when success retrieving the User list`() {
    val UserList = arrayListOf<User>()
    given(userRepository.getUserList(any(), any(), any(), any())).willReturn(Single.just(UserList))

    sut.execute(1.1, 1.1, 0, 50).test()
        .assertValueAt(1) { (it as Success).users == UserList }
  }

  @Test
  fun `returns the failure result when error retrieving the User list`() {
    val throwable = Throwable()
    given(userRepository.getUserList(any(), any(), any(), any())).willReturn(Single.error(throwable))

    sut.execute(1.1, 1.1, 0, 50).test()
        .assertValueAt(1) { (it as Failure).throwable == throwable }
  }
}