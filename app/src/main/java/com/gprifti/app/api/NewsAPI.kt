package com.gprifti.app.api

import com.gprifti.app.models.BaseModel
import com.gprifti.app.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.*

interface NewsAPI {

    @FormUrlEncoded
    @POST("/search")
    suspend fun search(
        @Field("filter") filter: String,
        @Field("response_format") response: String,
        @Header("Authorization") token: String = API_KEY
    ): Response<BaseModel>
}