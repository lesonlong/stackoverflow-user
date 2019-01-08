package com.longle.sofuser.presentation

import android.support.v7.app.AppCompatActivity
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