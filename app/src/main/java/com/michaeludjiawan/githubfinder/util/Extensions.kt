package com.michaeludjiawan.githubfinder.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

fun toVisibility(constraint: Boolean): Int = if (constraint) {
    View.VISIBLE
} else {
    View.GONE
}

fun Activity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isActive) {
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}

/**
 *  Set view's visibility to View.VISIBLE and
 *  set siblings's visibility with the same parent to View.GONE
 */
fun View.showSingle() {
    this.visibility = View.VISIBLE

    (parent as? ViewGroup)?.let { parent ->
        for (index in 0 until parent.childCount) {
            if (parent.getChildAt(index) != this) {
                parent.getChildAt(index).visibility = View.GONE
            }
        }
    }
}