package com.data.repository

import com.data.UserApi
import com.data.mapper.UserMapper
import com.data.response.UserResponse
import com.domain.model.User
import com.domain.repository.UserRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

  @Mock lateinit var userApi: UserApi
  @Mock lateinit var userMapper: UserMapper
  private lateinit var sut: UserRepository

  @Before
  fun setUp() {
    sut = UserRepositoryImpl(userApi, userMapper)
  }

  @Test
  fun `getUserList gets the userList`() {
    given(userApi.getUserList(any(), any(), any(), any())).willReturn(Single.just(mock()))
    given(userMapper.map(any<List<UserResponse>>())).willReturn(mock())

    sut.getUserList(1.1, 1.1, 0, 50).test()

    verify(userApi).getUserList(any(), any(), any(), any())
  }

  @Test
  fun `getUserList maps the userList`() {
    val userList = arrayListOf<UserResponse>(mock())
    given(userApi.getUserList(any(), any(), any(), any())).willReturn(Single.just(userList))
    given(userMapper.map(any<List<UserResponse>>())).willReturn(mock())

    sut.getUserList(1.1, 1.1, 0, 50).test()

    verify(userMapper).map(eq(userList))
  }

  @Test
  fun `getUserList returns mapped list`() {
    val userList = arrayListOf<UserResponse>(mock())
    val mappedUserList = arrayListOf<User>(mock())
    given(userApi.getUserList(any(), any(), any(), any())).willReturn(Single.just(userList))
    given(userMapper.map(any<List<UserResponse>>())).willReturn(mappedUserList)

    sut.getUserList(1.1, 1.1, 0, 50).test().assertValue(mappedUserList)
  }

  @Test
  fun `getUserList returns error on failure`() {
    val error = Throwable()
    given(userApi.getUserList(any(), any(), any(), any())).willReturn(Single.error(error))

    sut.getUserList(1.1, 1.1, 0, 50).test().assertError(error)
  }

  @Test
  fun `getUser gets the User`() {
    given(userApi.getUser(any())).willReturn(Single.just(mock()))
    given(userMapper.map(any<UserResponse>())).willReturn(mock())

    sut.getUser(1).test()

    verify(userApi).getUser(any())
  }

  @Test
  fun `getUser maps the User`() {
    val User = mock<UserResponse>()
    given(userApi.getUser(any())).willReturn(Single.just(User))

    sut.getUser(1).test()

    verify(userMapper).map(eq(User))
  }

  @Test
  fun `getUser returns mapped User`() {
    val mappedUser= mock<User>()
    val User = mock<UserResponse>()
    given(userApi.getUser(any())).willReturn(Single.just(User))
    given(userMapper.map(any<UserResponse>())).willReturn(mappedUser)

    sut.getUser(1).test().assertValue(mappedUser)
  }

  @Test
  fun `getUser returns error on failure`() {
    val error = Throwable()
    given(userApi.getUser(any())).willReturn(Single.error(error))

    sut.getUser(1).test().assertError(error)
  }
}