package com.apex.codeassesment.data.local

import com.apex.codeassesment.data.model.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

// TODO (3 points): Convert to Kotlin
// TODO (2 point): Add tests
// TODO (1 point): Use the correct naming conventions.
// TODO (3 points): Inject all dependencies instead of instantiating them.

class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val moshi: Moshi
) {


    @NotNull
    fun loadUser(): User {
        val serializedUser = preferencesManager.loadUser()
        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
        return try {
            val user = serializedUser?.let { jsonAdapter.fromJson(it) }
            user ?: User.createRandom()
        } catch (e: Exception) {
            e.printStackTrace()
            User.createRandom()
        }
    }

    fun saveUser(@NotNull user: User) {
        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
        val serializedUser = jsonAdapter.toJson(user)
        preferencesManager.saveUser(serializedUser)
    }
}
