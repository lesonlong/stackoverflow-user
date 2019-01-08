package com.example.anthonyliberatore.userdelivery.app.presentation.main

import android.app.Activity
import com.longle.sofuser.presentation.main.MainRouter
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.ref.WeakReference

@RunWith(MockitoJUnitRunner::class)
class MainRouterTest {

    @Mock lateinit var activity: Activity

    private lateinit var sut: MainRouter

    @Before
    fun setUp() {
        sut = MainRouter(WeakReference(activity))
    }

    @Test
    fun `navigate shows image detail screen when route is image detail`() {
        sut.navigate(MainRouter.Route.IMAGE_DETAIL)

        verify(activity).startActivity(anyOrNull())
    }

    @Test
    fun `navigate shows user detail screen when route is user detail`() {
        sut.navigate(MainRouter.Route.USER_DETAIL)

        verify(activity).startActivity(anyOrNull())
    }
}