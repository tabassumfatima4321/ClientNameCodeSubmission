package com.apex.codeassesment.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.helperClasses.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope


class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    private val _viewState = MutableLiveData<ViewState<User>>()
    val viewState: LiveData<ViewState<User>> get() = _viewState

    private val _viewStateList = MutableLiveData<ViewState<List<User>>>()
    val viewStateList: LiveData<ViewState<List<User>>> get() = _viewStateList


    init {
        viewModelScope.launch {
            refreshRandomUser()
        }
    }

    fun refreshRandomUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getSavedUser()
            viewModelScope.launch(Dispatchers.Main) {
                _viewState.value = ViewState.Success(user)
            }
        }
    }

    fun refreshUser() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = userRepository.getUser(true)
                viewModelScope.launch(Dispatchers.Main) {
                    _viewState.postValue(ViewState.Success(user))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _viewState.postValue(ViewState.Error("Failed to fetch user data"))
                }
            }
        }
    }

    fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val usersList = userRepository.getUsers()
            CoroutineScope(Dispatchers.Main).launch  {
                usersList.results?.let {
                    _viewStateList.value = ViewState.Success(usersList.results.results)

                }
            }
        }
    }
}
