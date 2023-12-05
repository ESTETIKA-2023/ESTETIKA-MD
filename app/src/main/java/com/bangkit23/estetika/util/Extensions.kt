package com.bangkit23.estetika.util

import android.animation.ObjectAnimator
import android.view.View

/**
 * Extension function for set the ImageView's src with Glide library
 *
 * @param context Context
 * @param url Image's URL
 */

//fun ImageView.setImageFromUrl(context: Context, url: String) {
//    Glide
//        .with(context)
//        .load(url)
//        .placeholder(R.drawable.image_loading_placeholder)
//        .error(R.drawable.image_load_error)
//        .into(this)
//}

/**
 * Set TextView text attribute to locale date format
 *
 * @param timestamp Timestamp
 */

//fun TextView.setLocalDateFormat(timestamp: String) {
//    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
//    val date = sdf.parse(timestamp) as Date
//
//    val formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date)
//    this.text = formattedDate
//}

/**
 * Animate visibility by transforming alpha value of a view
 *
 * @param isVisible View visibility
 * @param duration Animation duration, default 400
 */
fun View.animateVisibility(isVisible: Boolean, duration: Long = 400) {
    ObjectAnimator
        .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
        .setDuration(duration)
        .start()
}