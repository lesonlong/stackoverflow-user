package com.longle.sofuser.presentation.userdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.longle.sofuser.presentation.imagedetail.ImageDetailActivity
import java.lang.ref.WeakReference

/**
 * UserDetailRouter handles navigation for the UserDetail Screen
 */
class UserDetailRouter(private val activityRef: WeakReference<Activity>) {

    enum class Route {
        IMAGE_DETAIL,
    }

    fun navigate(route: Route, bundle: Bundle = Bundle()) {
        when (route) {
            Route.IMAGE_DETAIL -> { showNextScreen(ImageDetailActivity::class.java, bundle) }
        }
    }

    private fun showNextScreen(clazz: Class<*>, bundle: Bundle?) {
        activityRef.get()?.startActivity(Intent(activityRef.get(), clazz).putExtras(bundle))
    }
}
