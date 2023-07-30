package com.apex.codeassesment.data

import com.apex.codeassesment.ApiResponse
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.LoadUserApiResponse
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.interfaces.UserRepositoryInterface
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject



// TODO (2 points) : Add tests
// TODO (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings
class UserRepository @Inject constructor(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
) : UserRepositoryInterface {

  private val savedUser = AtomicReference<User>()

  override suspend fun getSavedUser(): User = localDataSource.loadUser()

  override suspend fun getUser(forceUpdate: Boolean): User {
    if (forceUpdate) {
      val apiResponse = remoteDataSource.loadUser()
      val user = apiResponse.results?.results?.get(0)
      if (user != null) {
        localDataSource.saveUser(user)
        savedUser.set(user)
      }
    }
    return savedUser.get()
  }

  override suspend fun getUsers(): ApiResponse<LoadUserApiResponse> {
   return remoteDataSource.loadUsers()

  }
}


