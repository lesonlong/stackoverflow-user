package com.longle.sofuser.presentation.userdetail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.longle.sofuser.R
import com.longle.sofuser.presentation.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import com.longle.sofuser.databinding.ActivityUserDetailBinding
import com.longle.sofuser.ext.addTo
import javax.inject.Inject

class UserDetailActivity : BaseActivity() {

  @Inject lateinit var viewModel: UserDetailViewModel
  private val disposables = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivityUserDetailBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_user_detail)

    screenComponent.inject(this)

    binding.viewModel = viewModel
    viewModel.bound(intent.getIntExtra(EXTRA_USER_ID, -1))
  }

  override fun onResume() {
    super.onResume()
    viewModel.showErrorGettingUserDetails.observe()
        .subscribe {
          AlertDialog.Builder(this)
              .setTitle(getString(R.string.error_title))
              .setMessage(getString(R.string.user_detail_message))
              .setNeutralButton(getString(R.string.ok), { dialog, _ -> dialog.dismiss() })
        }.addTo(disposables)
  }

  override fun onPause() {
    disposables.clear()
    super.onPause()
  }

  override fun onDestroy() {
    viewModel.unbound()
    super.onDestroy()
  }

  companion object {
    const val EXTRA_USER_ID = "user_id"
  }
}
