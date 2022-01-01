package com.example.task_suitmedia.api

import com.example.task_suitmedia.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api/users")
    fun mSearch(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UserData>
}