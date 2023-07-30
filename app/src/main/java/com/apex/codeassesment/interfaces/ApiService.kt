package com.apex.codeassesment.interfaces

import com.apex.codeassesment.ApiResponse
import com.apex.codeassesment.data.model.LoadUserApiResponse
import com.apex.codeassesment.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(".")
    suspend fun loadUser(): Response<LoadUserApiResponse>

    @GET("?results=10")
    suspend fun loadUsers(): Response<LoadUserApiResponse>
}
