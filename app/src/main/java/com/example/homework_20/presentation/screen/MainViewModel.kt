package com.example.homework_20.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_20.data.common.Resource
import com.example.homework_20.domain.usecase.DeleteUserByEmailUseCase
import com.example.homework_20.domain.usecase.GetUserNumberUseCase
import com.example.homework_20.domain.usecase.InsertUserUseCase
import com.example.homework_20.domain.usecase.UpdateUserByEmailUseCase
import com.example.homework_20.presentation.model.User
import com.example.homework_20.presentation.screen.event.MainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteUserByEmailUseCase: DeleteUserByEmailUseCase,
    private val getUserNumberUseCase: GetUserNumberUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updateUserByEmailUseCase: UpdateUserByEmailUseCase
) : ViewModel() {
    private var _userNumberStateFlow =
        MutableStateFlow<Resource<Int>>(Resource.Loader(loader = false))
    val userNumberStateFlow get() = _userNumberStateFlow.asStateFlow()

    private val _userChangeMessageStateFlow =
        MutableStateFlow<Resource<String>>(Resource.Loader(loader = false))
    val userChangeMessageStateFlow get() = _userChangeMessageStateFlow.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetUserNumber -> getUserNumber()
            is MainEvent.DeleteUserByEmail -> deleteUserByEmail(event.email)
            is MainEvent.InsertUser -> insertUser(event.user)
            is MainEvent.UpdateUserByEmail -> updateUserByEmail(event.user)
        }
    }

    private fun deleteUserByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserByEmailUseCase(email = email).collect {
                when (it) {
                    is Resource.Success -> {
                        _userChangeMessageStateFlow.value =
                            Resource.Success(success = it.successMessage!!)
                    }

                    is Resource.Error -> {
                        _userChangeMessageStateFlow.value = Resource.Error(error = it.error)
                    }

                    is Resource.Loader -> Resource.Loader(loader = it.loader)
                }
            }
        }
    }

    private fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            insertUserUseCase(user = user).collect {
                when (it) {
                    is Resource.Success -> {
                        _userChangeMessageStateFlow.value =
                            Resource.Success(success = it.successMessage!!)
                    }

                    is Resource.Error -> {
                        _userChangeMessageStateFlow.value = Resource.Error(error = it.error)
                    }

                    is Resource.Loader -> {
                        Resource.Loader(loader = it.loader)
                    }
                }
            }
        }
    }

    private fun updateUserByEmail(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserByEmailUseCase(user = user).collect {
                when (it) {
                    is Resource.Success -> {
                        _userChangeMessageStateFlow.value =
                            Resource.Success(success = it.successMessage!!)
                    }

                    is Resource.Error -> {
                        _userChangeMessageStateFlow.value = Resource.Error(error = it.error)
                    }

                    is Resource.Loader -> {
                        _userChangeMessageStateFlow.value = Resource.Loader(loader = it.loader)
                    }
                }
            }
        }
    }

    private fun getUserNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserNumberUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        _userNumberStateFlow.value =
                            Resource.Success(success = it.success)
                    }

                    is Resource.Error -> {
                        _userNumberStateFlow.value = Resource.Error(error = it.error)
                    }

                    is Resource.Loader -> {
                        Resource.Loader(loader = it.loader)
                    }
                }
            }
        }
    }
}