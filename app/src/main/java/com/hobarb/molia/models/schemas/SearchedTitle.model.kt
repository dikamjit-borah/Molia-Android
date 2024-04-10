package com.hobarb.molia.models.schemas

data class SearchedTitle(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
)