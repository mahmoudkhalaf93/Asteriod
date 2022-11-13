package com.example.asteriod

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =   String.format(context.getString(R.string.potentially_hazardous_asteroid_image))
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription =   String.format(context.getString(R.string.not_hazardous_asteroid_image))
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =   String.format(context.getString(R.string.potentially_hazardous_asteroid_image))
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =   String.format(context.getString(R.string.not_hazardous_asteroid_image))
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("loadImageUrl")
fun loadImageUrl(imageView: ImageView, data: PictureOfDay?) {
    val context = imageView.context
    imageView.contentDescription =
        String.format(context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet))
    data?.let {
        if (data.mediaType.equals("image")) {
            Picasso.get().load(data.url).error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)

            imageView.contentDescription =
                String.format(context.getString(R.string.nasa_picture_of_day_content_description_format), data.title)
        } else

            imageView.setImageResource(R.drawable.ic_baseline_ondemand_video_24)
    }
}