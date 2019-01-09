package com.longle.sofuser.presentation.userdetail

import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.domain.usecase.GetUserUseCase
import com.domain.usecase.GetUserUseCase.Result
import com.longle.sofuser.ext.addTo
import com.longle.sofuser.presentation.imagedetail.ImageDetailActivity
import com.longle.sofuser.rx.StickyAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
        private val getUserUseCase: GetUserUseCase,
        private val userDetailRouter: UserDetailRouter
) {

  private val disposables = CompositeDisposable()
  val progressVisible = ObservableBoolean()
  val showErrorGettingUserDetails = StickyAction<Boolean>()
  var userName = ObservableField<String>()
  var userDescription = ObservableField<String>()
  var userStatus = ObservableField<String>()
  var userImage = ObservableField<String>()

  // Called onCreate. Retrieves the user details
  fun bound(id: Int) {
    getUserUseCase.execute(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { handleGetUserListResult(it) }
        .addTo(disposables)
  }

  // Called onDestroy. Clean up method
  fun unbound() {
    disposables.clear()
  }

  // Handles Result from getUserUseCase
  private fun handleGetUserListResult(result: Result) {
    progressVisible.set(result == Result.Loading)
    when (result) {
      is Result.Success -> {
        userName.set(result.user.name)
        userDescription.set(result.user.description)
        userStatus.set(result.user.status)
        userImage.set(result.user.coverImgUrl)
      }
      is Result.Failure -> {
        showErrorGettingUserDetails.trigger(true)
      }
    }
  }

  fun onImageClicked() {
    userDetailRouter.navigate(UserDetailRouter.Route.IMAGE_DETAIL, Bundle().apply {
      putString(ImageDetailActivity.EXTRA_URL, userImage.get())
    })
  }
}
