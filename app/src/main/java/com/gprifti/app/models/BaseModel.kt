package com.gprifti.app.models

import com.google.gson.annotations.SerializedName

data class BaseModel(

    @field:SerializedName("paging")
    val paging: PagingModel? = null,

    @field:SerializedName("results")
    var results: ResultModel? = null
)
