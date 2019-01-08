package com.longle.sofuser.presentation

import com.longle.sofuser.rx.RxJavaTestHooksResetRule
import com.domain.model.User
import com.domain.usecase.GetUserUseCase
import com.domain.usecase.GetUserUseCase.Result
import com.longle.sofuser.presentation.userdetail.UserDetailRouter
import com.longle.sofuser.presentation.userdetail.UserDetailViewModel
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest {
  @get:Rule
  var rxJavaTestHooksResetRule = RxJavaTestHooksResetRule()

  @Mock lateinit var getUserUseCase: GetUserUseCase
  @Mock lateinit var userDetailRouter: UserDetailRouter
  private lateinit var sut: UserDetailViewModel

  @Before
  fun setUp() {
    sut = UserDetailViewModel(getUserUseCase, userDetailRouter)
  }

  @Test
  fun `bound retrieves the user from the id`() {
    val id = 1
    given(getUserUseCase.execute(id)).willReturn(Observable.just(mock()))

    sut.bound(id)

    verify(getUserUseCase).execute(eq(id))
  }

  @Test
  fun `bound shows error when retrieving the user fails`() {
    val id = 1
    given(getUserUseCase.execute(id)).willReturn(Observable.just(Result.Failure(Throwable())))

    sut.bound(id)

    sut.showErrorGettingUserDetails.observe().test().assertValue(true)
  }

  @Test
  fun `bound shows user details when retrieving the user`() {
    val id = 1
    val user = User(
            id = 1,
            name = "name",
            description = "description",
            coverImgUrl = "coverImgUrl",
            status = "status",
            deliveryFee = 0
    )
    given(getUserUseCase.execute(id)).willReturn(Observable.just(Result.Success(user)))

    sut.bound(id)

    assertThat(sut.userDescription.get(), equalTo(user.description))
    assertThat(sut.userName.get(), equalTo(user.name))
    assertThat(sut.userStatus.get(), equalTo(user.status))
    assertThat(sut.userImage.get(), equalTo(user.coverImgUrl))
  }
}
