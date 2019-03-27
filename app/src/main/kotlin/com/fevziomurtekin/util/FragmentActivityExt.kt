package com.fevziomurtekin.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


/**
 * Retrieve argument from Activity intent
 */
fun <T : Any> FragmentActivity.argument(key: String) =
    lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }