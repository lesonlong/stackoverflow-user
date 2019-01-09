package com.longle.sofuser.presentation

import androidx.appcompat.app.AppCompatActivity
import com.longle.sofuser.BaseApplication
import com.longle.sofuser.di.application.ApplicationComponent
import com.longle.sofuser.di.screen.ScreenModule

open class BaseActivity : AppCompatActivity() {

  val screenComponent by lazy {
    getApplicationComponent().plus(ScreenModule(this))
  }

  private fun getApplicationComponent(): ApplicationComponent {
    return (application as BaseApplication).component
  }
}