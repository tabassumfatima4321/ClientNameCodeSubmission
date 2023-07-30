package com.apex.codeassesment.data.local

import android.content.Context
import android.content.SharedPreferences
import com.apex.codeassesment.ui.main.MainActivity
import javax.inject.Inject

// TODO (2 point): Add tests
//class PreferencesManager {
//
//  fun saveUser(user: String) {
//    val sharedPreferences = MainActivity.sharedContext?.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
//    sharedPreferences?.edit()?.putString("saved-user", user)?.apply()
//  }
//
//  fun loadUser(): String? {
//    val sharedPreferences = MainActivity.sharedContext?.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
//    return sharedPreferences?.getString("saved-user", null)
//  }
//
//}

class PreferencesManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

  fun saveUser(user: String) {
    sharedPreferences.edit().putString("saved-user", user).apply()
  }

  fun loadUser(): String? {
    return sharedPreferences.getString("saved-user", null)
  }
}
