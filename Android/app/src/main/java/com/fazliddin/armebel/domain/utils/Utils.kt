package com.fazliddin.armebel.domain.utils

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

fun setViewDrawableTint(view: TextView, color: Int) {

    TextViewCompat.setCompoundDrawableTintList(
        view, ColorStateList.valueOf(
            ContextCompat.getColor(
                view.context,
                color
            )
        )
    )
}