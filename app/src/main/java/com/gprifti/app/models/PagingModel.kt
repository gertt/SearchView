package com.gprifti.app.models

import com.google.gson.annotations.SerializedName

data class PagingModel(

    @field:SerializedName("results_per_page")
    val resultsPerPage: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)