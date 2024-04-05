package com.hobarb.molio.models

import java.io.Serializable

data class SaveTitleModel(
    val user_id: String = "",
    val collection: String = "",
    val sub_collection: String? = null,
    val details: TitleModel
) : Serializable
