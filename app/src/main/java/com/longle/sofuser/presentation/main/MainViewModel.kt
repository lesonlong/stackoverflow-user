package com.longle.sofuser.presentation.main

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.domain.model.User
import com.domain.usecase.GetUserListUseCase
import com.domain.usecase.GetUserListUseCase.Result
import com.longle.sofuser.ext.addTo
import com.longle.sofuser.presentation.imagedetail.ImageDetailActivity
import com.longle.sofuser.presentation.userdetail.UserDetailActivity
import com.longle.sofuser.rx.StickyAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val getUserListUseCase: GetUserListUseCase,
        private val mainRouter: MainRouter
) {

    val disposables = CompositeDisposable()
    val progressVisible = ObservableBoolean()
    val userList = ObservableArrayList<User>()
    val showErrorGettingUsers = StickyAction<Boolean>()

    companion object {
        // Hardcoded LatLng for simplicity here
        const val LAT = 37.422740
        const val LNG = -122.139956
    }

    // Called onCreate. Retrieves the list of users
    fun bound() {
        //offset and limit can be used for pagination in the future. This is static for now.
        getUserListUseCase.execute(LAT, LNG, 0, 50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleGetUserListResult(it) }
                .addTo(disposables)
    }

    // Called onDestroy. Clean up method.
    fun unbound() {
        disposables.clear()
    }

    // Handles Result from getUserListUseCase
    private fun handleGetUserListResult(result: Result) {
        progressVisible.set(result == Result.Loading)
        when (result) {
            is Result.Success -> {
                userList.addAll(result.users)
            }
            is Result.Failure -> {
                showErrorGettingUsers.trigger(true)
            }
        }
    }

    // Shows user detail screen based on user clicked
    fun onUserClicked(user: Any) {
        mainRouter.navigate(MainRouter.Route.USER_DETAIL, Bundle().apply {
                    putInt(UserDetailActivity.EXTRA_USER_ID, (user as User).id)
                })
    }

    // Shows image detail screen based on user clicked
    fun onUserImageClicked(user: Any) {
        mainRouter.navigate(MainRouter.Route.IMAGE_DETAIL, Bundle().apply {
            putString(ImageDetailActivity.EXTRA_URL, (user as User).coverImgUrl)
        })
    }
}
