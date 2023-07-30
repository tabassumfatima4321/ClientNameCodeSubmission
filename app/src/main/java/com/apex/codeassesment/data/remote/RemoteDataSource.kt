package com.apex.codeassesment.data.remote

import com.apex.codeassesment.ApiClient
import com.apex.codeassesment.ApiResponse
import com.apex.codeassesment.data.model.LoadUserApiResponse
import com.apex.codeassesment.data.model.User
import javax.inject.Inject

//// TODO (2 points): Add tests
//class RemoteDataSource @Inject constructor() {
//
//  // TODO (5 points): Load data from endpoint https://randomuser.me/api
//  fun LoadUser() = User.createRandom()
//
//  // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
//  // TODO (Optional Bonus: 3 points): Handle succes and failure from endpoints
//  fun loadUsers() = (0..10).map { User.createRandom() }
//}

// TODO (2 points): Add tests
// TODO (5 points): Load data from endpoint https://randomuser.me/api
// TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
// TODO (Optional Bonus: 3 points): Handle succes and failure from endpoints

class RemoteDataSource @Inject constructor(private val apiService: ApiClient) {


  suspend fun loadUser(): ApiResponse<LoadUserApiResponse> {
    return try {
      val response = apiService.service.loadUser()
      if (response.isSuccessful) {
        ApiResponse(response.body())
      } else {
        ApiResponse(results = null , error = "User data not available")
      }
    } catch (e: Exception) {
      ApiResponse(results = null ,error = e.message)
    }
  }

  suspend fun loadUsers(): ApiResponse<LoadUserApiResponse> {
    return try {
      val response = apiService.service.loadUsers()
      if (response.isSuccessful) {
        ApiResponse(response.body())
      } else {
        ApiResponse(results = null ,error = "Users data not available")
      }
    } catch (e: Exception) {
      ApiResponse(results = null , error = e.message)
    }
  }
}

