package com.example.anthonyliberatore.userdelivery.app.presentation.userdetail

import android.app.Activity
import com.longle.sofuser.presentation.userdetail.UserDetailRouter
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.ref.WeakReference

@RunWith(MockitoJUnitRunner::class)
class UserDetailRouterTest {

    @Mock
    lateinit var activity: Activity

    private lateinit var sut: UserDetailRouter

    @Before
    fun setUp() {
        sut = UserDetailRouter(WeakReference(activity))
    }

    @Test
    fun `navigate shows image detail screen when route is image detail`() {
        sut.navigate(UserDetailRouter.Route.IMAGE_DETAIL)

        verify(activity).startActivity(anyOrNull())
    }
}
