package com.longle.sofuser.di.screen

import com.longle.sofuser.di.scope.PerScreen
import com.longle.sofuser.presentation.BaseActivity
import com.longle.sofuser.presentation.main.MainRouter
import com.longle.sofuser.presentation.userdetail.UserDetailRouter
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

@Module
class ScreenModule(private val activity: BaseActivity) {

  @PerScreen
  @Provides
  fun providesActivity(): BaseActivity {
    return activity
  }

  @PerScreen
  @Provides
  fun providesMainRouter(): MainRouter {
    return MainRouter(WeakReference(activity))
  }

  @PerScreen
  @Provides
  fun providesUserDetailRouter(): UserDetailRouter {
    return UserDetailRouter(WeakReference(activity))
  }
}
