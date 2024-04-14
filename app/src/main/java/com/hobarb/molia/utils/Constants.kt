package com.hobarb.molia.utils

object Constants {

    val COLLECTIONS = arrayOf("watched", "watching", "favorites", "wishlist", "custom")

    object MESSAGE {
        const val NETWORK_ERROR = "Some network error occurred while fetching data"
        const val PARSE_ERROR = "Error parsing response data"
        const val SOMETHING_WRONG = "Something went wrong. Please try after some time"
        const val NO_RESPONSE = "Response does not contain data"
        const val INPUT_NOT_BE_EMPTY = "Input field cannot be empty"
        //const val NO_RESPONSE = "Response does not contain data"
    }

    const val USER_ID = "superuser-hobarb-01"
}