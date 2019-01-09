package com.longle.sofuser.presentation.imagedetail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.longle.sofuser.R
import com.longle.sofuser.presentation.BaseActivity
import com.longle.sofuser.databinding.ActivityImageDetailBinding
import javax.inject.Inject

class ImageDetailActivity: BaseActivity() {

    companion object {
        const val EXTRA_URL = "URL"
    }

    @Inject lateinit var viewModel: ImageDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenComponent.inject(this)

        val binding: ActivityImageDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail)
        binding.viewModel = viewModel

        viewModel.bound(intent.extras.getString(EXTRA_URL, ""))
    }
}
