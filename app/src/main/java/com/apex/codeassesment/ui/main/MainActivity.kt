package com.apex.codeassesment.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.extensions.ViewModelFactory
import com.apex.codeassesment.extensions.navigateDetails
import com.apex.codeassesment.helperClasses.ViewState
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.


class MainActivity : AppCompatActivity() {

    // TODO (2 points): Convert to view binding
    private lateinit var binding: ActivityMainBinding


//    private val viewModel: MainViewModel by viewModels()
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as MainComponent.Injector).mainComponent.inject(this)



        val arrayAdapter = ArrayAdapter<User>(this, android.R.layout.simple_list_item_1)
        binding.mainUserList.adapter = arrayAdapter


        viewModel.viewState.observe(this) { state ->
            when (state) {
                is ViewState.Success -> showUserData(state.data)
                is ViewState.Error -> showError(state.message)
                ViewState.Loading -> showLoading()
            }
        }

//    viewModel.randomUser.observe(this) { user ->
//      user?.let {
//        showUserData(it)
//      }
//    }

        viewModel.viewStateList.observe(this) { state ->
            when (state) {
                is ViewState.Success -> showUserList(state.data)
                is ViewState.Error -> showError(state.message)
                ViewState.Loading -> showLoading()
            }
        }

//    viewModel.users.observe(this) { users ->
//      arrayAdapter.clear()
//      arrayAdapter.addAll(users)
//    }


        binding.mainSeeDetailsButton.setOnClickListener {
            viewModel.viewState.value?.let { state ->
                if (state is ViewState.Success) {
                    navigateDetails(state.data)
                }
            }
        }

//    binding.mainSeeDetailsButton.setOnClickListener {
//      viewModel.randomUser.value?.let {
//       // TODO (2 points): Convert to extenstion function.
//        navigateDetails(it)
//      }
//    }

        binding.mainRefreshButton.setOnClickListener {
            viewModel.refreshUser()

        }

        binding.mainUserListButton.setOnClickListener {
            viewModel.loadUsers()
        }

    }

    // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
    private fun showUserData(user: User) {
        Glide.with(this)
            .load(user.picture)
            .into(binding.mainImage)

        binding.mainImage.contentDescription = getString(R.string.user_profile_image)
        binding.mainName.text = user.name?.first
        binding.mainEmail.text = user.email
    }

    private fun showUserList(users: List<User>) {
        val arrayAdapter = ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users)
        binding.mainUserList.adapter = arrayAdapter
    }

    private fun showError(message: String) {
        // Handle error state and show an error message to the user.
    }

    private fun showLoading() {
        // Handle loading state, e.g., show a progress bar.
    }

    companion object {
        var sharedContext: Context? = null
    }

}
