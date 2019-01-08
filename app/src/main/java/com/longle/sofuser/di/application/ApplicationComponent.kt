package com.longle.sofuser.di.application

import com.longle.sofuser.BaseApplication
import com.longle.sofuser.di.screen.ScreenComponent
import com.longle.sofuser.di.screen.ScreenModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, EndpointModule::class])
interface ApplicationComponent {

  fun inject(activity: BaseApplication)

  fun plus(screenModule: ScreenModule): ScreenComponent
}
