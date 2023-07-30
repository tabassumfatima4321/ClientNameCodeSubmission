package com.apex.codeassesment.interfaces

import com.apex.codeassesment.ApiResponse
import com.apex.codeassesment.data.model.LoadUserApiResponse
import com.apex.codeassesment.data.model.User

interface UserRepositoryInterface {
    suspend fun getSavedUser(): User
    suspend fun getUser(forceUpdate: Boolean): User
    suspend fun getUsers(): ApiResponse<LoadUserApiResponse>
}
