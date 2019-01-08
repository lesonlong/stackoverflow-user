package com.longle.sofuser

import android.app.Application
import com.longle.sofuser.di.application.ApplicationComponent
import com.longle.sofuser.di.application.ApplicationModule
import com.longle.sofuser.di.application.DaggerApplicationComponent

class BaseApplication : Application() {

  lateinit var component: ApplicationComponent

  override fun onCreate() {
    super.onCreate()

    inject()
  }

  fun inject() {
    component = DaggerApplicationComponent.builder().applicationModule(
        ApplicationModule(this)
    ).build()
    component.inject(this)
  }
}