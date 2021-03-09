package com.crayonwriter.asteroidsarecoming

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder that holds a single [TextView].
 *
 * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
 * to the RecyclerView such as where on the screen it was last drawn during scrolling.
 */
class TextItemViewHolder(private val asteroidView: View): RecyclerView.ViewHolder(asteroidView) {
    private val textView: TextView = asteroidView.findViewById(R.id.codename_text_view)

    fun bind(item: Asteroid) {
        textView.text = item.codename
    }
}