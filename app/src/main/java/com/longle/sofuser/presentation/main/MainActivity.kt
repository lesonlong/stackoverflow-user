package com.longle.sofuser.presentation.main

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.longle.sofuser.R
import com.longle.sofuser.databinding.ActivityMainBinding
import com.longle.sofuser.ext.addTo
import com.longle.sofuser.presentation.BaseActivity
import com.longle.sofuser.presentation.adapter.UserListAdapter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : BaseActivity() {

  @Inject lateinit var viewModel: MainViewModel
  private val disposables = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    screenComponent.inject(this)

    binding.viewModel = viewModel
    viewModel.bound()
  }

  // onResume we need to subscribe to our viewModel actions
  override fun onResume() {
    super.onResume()
    viewModel.showErrorGettingUsers.observe()
        .subscribe {
          AlertDialog.Builder(this)
              .setTitle(getString(R.string.error_title))
              .setMessage(getString(R.string.user_list_error_message))
              .setNeutralButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
        }.addTo(disposables)
  }

  // onPause we need to unsubscribe from viewModel actions since the view is not backgrounded
  override fun onPause() {
    disposables.clear()
    super.onPause()
  }

  override fun onDestroy() {
    viewModel.unbound()
    super.onDestroy()
  }

  companion object {
    /**
     * bindList uses Databinding to initialize the recyclerView using an ObservableList from the MainViewModel
     * this is referenced in activity_main.xml as 'app:adapter={@viewModel}'
     */
    @JvmStatic
    @BindingAdapter("adapter")
    fun bindList(recyclerView: RecyclerView, viewModel: MainViewModel) {
      val adapter = UserListAdapter(viewModel.userList)
      adapter.onItemClickListener = { viewModel.onUserClicked(it) }
      adapter.onImageClickedListener = { viewModel.onUserImageClicked(it) }
      recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
      recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
      recyclerView.adapter = adapter
    }
  }
}
