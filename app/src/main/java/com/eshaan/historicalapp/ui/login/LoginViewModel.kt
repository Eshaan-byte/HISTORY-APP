package com.eshaan.historicalapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshaan.historicalapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    private val _keypass = MutableLiveData<String>()
    val keypass: LiveData<String> = _keypass

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Username and password are required")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            repository.login(username, password)
                .onSuccess { keypass ->
                    _keypass.value = keypass
                    _loginState.value = LoginState.Success
                }
                .onFailure { throwable ->
                    _loginState.value = LoginState.Error(throwable.message ?: "Login failed")
                }
        }
    }

    sealed class LoginState {
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}