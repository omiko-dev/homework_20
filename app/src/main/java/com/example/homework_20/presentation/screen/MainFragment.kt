package com.example.homework_20.presentation.screen

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_20.data.common.Resource
import com.example.homework_20.databinding.FragmentMainBinding
import com.example.homework_20.presentation.base.BaseFragment
import com.example.homework_20.presentation.model.User
import com.example.homework_20.presentation.screen.event.MainEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    private val emailRegex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")

    override fun listener() {
        addUserListener()
        deleteUserListener()
        updateUserListener()
    }

    override fun observe() {
        userChangeMessageObserve()
        userNumberMessageObserve()
    }

    override fun event() {
        viewModel.onEvent(MainEvent.GetUserNumber)
    }

    private fun addUserListener() {
        binding.btnAddUser.setOnClickListener {
            if (checkFields() && checkEmail()) {
                viewModel.onEvent(
                    MainEvent.InsertUser(getUser())
                )
            }
        }
    }

    private fun deleteUserListener() {
        binding.btnRemoveUser.setOnClickListener {
            viewModel.onEvent(
                MainEvent.DeleteUserByEmail(getUser().email)
            )
        }

    }

    private fun updateUserListener() {
        binding.btnUpdateUser.setOnClickListener {
            if (checkFields() && checkEmail()) {
                viewModel.onEvent(
                    MainEvent.UpdateUserByEmail(getUser())
                )
            }
        }
    }

    private fun userChangeMessageObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userChangeMessageStateFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            viewModel.onEvent(MainEvent.GetUserNumber)
                            clearFields()
                            Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Loader -> {
                            with(binding) {
                                if (it.loader) {
                                    loader.visibility = View.VISIBLE
                                } else {
                                    loader.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun userNumberMessageObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userNumberStateFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.tvUserCount.text = it.success.toString()
                        }

                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Loader -> {
                            with(binding) {
                                if (it.loader) {
                                    loader.visibility = View.VISIBLE
                                } else {
                                    loader.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getUser(): User {
        with(binding) {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val strAge = etAge.text.toString()
            val age = if (strAge.isNotEmpty()) strAge.toInt() else 0
            return User(0, firstName, lastName, email, age)
        }
    }

    private fun checkFields(): Boolean {
        with(binding) {
            if (etFirstName.text.isNullOrBlank() ||
                etLastName.text.isNullOrBlank() ||
                etEmail.text.isNullOrBlank() ||
                etAge.text.isNullOrBlank()
            ) {
                tvError.visibility = View.VISIBLE
                return false
            } else {
                tvError.visibility = View.GONE
                return true
            }
        }
    }

    private fun checkEmail(): Boolean {
        with(binding) {
            if (emailRegex.containsMatchIn(etEmail.text.toString())) {
                tvEmailError.visibility = View.GONE
                return true
            } else {
                tvEmailError.visibility = View.VISIBLE
                return false
            }
        }
    }

    private fun clearFields() {
        with(binding) {
            etFirstName.text?.clear()
            etLastName.text?.clear()
            etEmail.text?.clear()
            etAge.text?.clear()
        }
    }
}