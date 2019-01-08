package com.longle.sofuser.di.screen

import com.longle.sofuser.di.scope.PerScreen
import com.longle.sofuser.presentation.imagedetail.ImageDetailActivity
import com.longle.sofuser.presentation.main.MainActivity
import com.longle.sofuser.presentation.userdetail.UserDetailActivity
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

  fun inject(mainActivity: MainActivity)

  fun inject(userDetailActivity: UserDetailActivity)

  fun inject(imageDetailActivity: ImageDetailActivity)
}
