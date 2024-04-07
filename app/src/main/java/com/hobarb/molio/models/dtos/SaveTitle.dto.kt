package com.hobarb.molio.models.dtos

import TitleDetails
import java.io.Serializable

data class SaveTitleModel(
    val user_id: String = "",
    val collection: String = "",
    val sub_collection: String? = null,
    val details: TitleDetails
) : Serializable
