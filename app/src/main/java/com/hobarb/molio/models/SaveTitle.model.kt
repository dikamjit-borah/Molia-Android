package com.hobarb.molio.models

import java.io.Serializable

data class SaveTitleModel(
    val user_id: String = "",
    val entry_type: String = "",
    val details: String = ""
) : Serializable
