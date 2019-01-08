package com.longle.sofuser.presentation

import com.domain.model.User
import com.domain.usecase.GetUserListUseCase
import com.domain.usecase.GetUserListUseCase.Result
import com.longle.sofuser.presentation.main.MainRouter
import com.longle.sofuser.presentation.main.MainViewModel
import com.longle.sofuser.rx.RxJavaTestHooksResetRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var rxJavaTestHooksResetRule = RxJavaTestHooksResetRule()

    @Mock lateinit var getUserListUseCase: GetUserListUseCase
    @Mock lateinit var mainRouter: MainRouter
    private lateinit var sut: MainViewModel

    @Before
    fun setUp() {
        sut = MainViewModel(getUserListUseCase, mainRouter)
    }

    @Test
    fun `bound retrieves the user list`() {
        given(getUserListUseCase.execute(any(), any(), any(), any()))
                .willReturn(Observable.just(mock()))

        sut.bound()

        verify(getUserListUseCase).execute(any(), any(), any(), any())
    }

    @Test
    fun `bound shows error when retrieving the users fail`() {
        given(getUserListUseCase.execute(any(), any(), any(), any()))
                .willReturn(Observable.just(Result.Failure(Throwable())))

        sut.bound()

        sut.showErrorGettingUsers.observe().test().assertValue(true)
    }

    @Test
    fun `bound adds users to list when successful`() {
        val users = arrayListOf<User>(mock(), mock())
        given(getUserListUseCase.execute(any(), any(), any(), any()))
                .willReturn(Observable.just(Result.Success(users)))

        sut.bound()

        assertThat(sut.userList.size, equalTo(2))
    }

    @Test
    fun `unbound clears disposables`() {
        val users = arrayListOf<User>(mock(), mock())
        given(getUserListUseCase.execute(any(), any(), any(), any()))
                .willReturn(Observable.just(Result.Success(users)))

        sut.bound()
        assertThat(sut.disposables.size(), equalTo(1))

        sut.unbound()
        assertThat(sut.disposables.size(), equalTo(0))
    }

    @Test
    fun `onUserClicked shows user detail screen`() {
        val userId = 1
        val user = mock<User> { on { id } doReturn userId }

        sut.onUserClicked(user)

        verify(mainRouter).navigate(eq(MainRouter.Route.USER_DETAIL), any())
    }

    @Test
    fun `onImageClicked shows image detail screen`() {
        val user = mock<User>()

        sut.onUserImageClicked(user)

        verify(mainRouter).navigate(eq(MainRouter.Route.IMAGE_DETAIL), any())
    }
}