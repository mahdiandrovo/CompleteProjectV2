package com.drovo.completeprojectv2.network

import com.drovo.completeprojectv2.data.Dog
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object{
        const val BASE_URL = "https://api.thedogapi.com"
    }
    //https://api.thedogapi.com/v1/images/search?page=2&limit=5
    @GET("/v1/images/search")
    suspend fun getAllDogs(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ) : List<Dog>
}