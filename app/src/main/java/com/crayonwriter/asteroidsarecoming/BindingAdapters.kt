package com.crayonwriter.asteroidsarecoming

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.work.Logger.get
import com.crayonwriter.asteroidsarecoming.Constants.BASE_URL
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.squareup.picasso.Picasso
import okhttp3.HttpUrl.get
import okhttp3.internal.http.StatusLine.get

//Create BindingAdapter for the date and the name

@BindingAdapter("closeApproachDateString")
fun TextView.setCloseApproachDateString(item: Asteroid?) {
    item?.let {
        text = item.closeApproachDate
    }
}

@BindingAdapter("codenameString")
fun TextView.setCodenameString(item: Asteroid?) {
    item?.let {
        text = item.codename
    }
}


@BindingAdapter("statusIcon")
fun ImageView.setStatusImage(item: Asteroid?) {
    item?.let {
        setImageResource(
            when (item.isPotentiallyHazardous) {
                true -> R.drawable.ic_status_potentially_hazardous
                false -> R.drawable.ic_status_normal
            }
        )
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.rootView.contentDescription = "Potentially hazardous asteroid cartoon image."
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.rootView.contentDescription = "Safe asteroid cartoon image."
    }
}

@BindingAdapter("astronomicalUnitText")
//absolute magnitude or miss_distance in close approach data
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
    }

@BindingAdapter("kmUnitText")
//estimated diameter
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
             Picasso.get()
            .load(imgUrl)
            .into(imgView);
    }
}