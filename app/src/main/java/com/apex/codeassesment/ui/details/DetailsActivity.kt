package com.apex.codeassesment.ui.details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityDetailsBinding
import com.apex.codeassesment.ui.location.LocationActivity
import com.bumptech.glide.Glide


// TODO (3 points): Convert to Kotlin
// TODO (3 points): Remove bugs or crashes if any
// TODO (1 point): Add content description to images
// TODO (2 points): Add tests
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>("saved-user-key")

        if (user != null) {
            // TODO (1 point): Use Glide to load images
            Glide.with(this)
                .load(user.picture?.large)
                .into(binding.detailsImage)

            val name = user.name
            val firstName = name?.first ?: "N/A"
            val lastName = name?.last ?: "N/A"
            binding.detailsName.text = getString(R.string.details_name, firstName, lastName)

            binding.detailsEmail.text = getString(R.string.details_email, user.gender ?: "N/A")
            binding.detailsAge.text = user.dob?.age?.toString() ?: "N/A"

            val coordinates = user.location?.coordinates
            val latitude = coordinates?.latitude?.toString() ?: "N/A"
            val longitude = coordinates?.longitude?.toString() ?: "N/A"
            binding.detailsLocation.text = getString(R.string.details_location, latitude, longitude)

            binding.detailsLocationButton.setOnClickListener {
                navigateLocation(coordinates)
            }

            // Add content description to the image for accessibility
            binding.detailsImage.contentDescription = getString(R.string.user_profile_image)
        } else {
            showErrorAndFinish("User data not available")
        }
    }

    private fun navigateLocation(coordinates: Coordinates?) {
        val intent = Intent(this, LocationActivity::class.java)
            .putExtra("user-latitude-key", coordinates?.latitude)
            .putExtra("user-longitude-key", coordinates?.longitude)
        startActivity(intent)
    }

    private fun showErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}

