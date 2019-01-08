package com.longle.sofuser.presentation.imagedetail

import android.databinding.ObservableField
import javax.inject.Inject

class ImageDetailViewModel @Inject constructor() {

    val imageUrl = ObservableField<String>()

    fun bound(url: String) {
        imageUrl.set(url)
    }
}
