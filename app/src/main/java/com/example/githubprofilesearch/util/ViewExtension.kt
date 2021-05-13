package com.example.githubprofilesearch.util

import android.view.View

/**
* Set view visible
*/
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * Set view invisible
 */
fun View.hide() {
    this.visibility = View.INVISIBLE
}

/**
 * Set view gone
 */
fun View.remove() {
    this.visibility = View.GONE
}

