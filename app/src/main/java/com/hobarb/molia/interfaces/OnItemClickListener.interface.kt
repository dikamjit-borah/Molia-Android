package com.hobarb.molia.interfaces

import com.google.gson.JsonElement
import com.hobarb.molia.models.schemas.SearchedTitle

interface OnItemClickListener {
    fun onSearchedTitleItemClick(item: SearchedTitle)
    fun onSubCollectionItemClick(item: JsonElement)

}
