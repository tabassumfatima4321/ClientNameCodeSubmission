package com.apex.codeassesment.extensions

import android.content.Intent
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.ui.main.MainActivity


fun MainActivity.navigateDetails(user: User) {
    val intent = Intent(this, DetailsActivity::class.java).apply {
        putExtra("saved-user-key", user)
    }
    startActivity(intent)
}