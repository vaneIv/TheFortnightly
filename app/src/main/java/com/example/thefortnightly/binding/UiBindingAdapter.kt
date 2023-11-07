package com.example.thefortnightly.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.thefortnightly.R

@BindingAdapter("imgUrl")
fun bindImgUrlToImageView(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imageView.context)
            .load(imgUrl)
            .error(R.drawable.image_placeholder)
            .into(imageView)
    }
}