package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.AuthFailure
import br.com.lactarehub.domain.entity.TestCredential
import br.com.lactarehub.domain.entity.UserSession
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var obscurePassword by mutableStateOf(true)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var session by mutableStateOf<UserSession?>(null)
        private set

    val testCredentials: List<TestCredential> = ServiceLocator.getTestCredentials()

    fun onEmailChange(value: String) {
        email = value
        if (emailError != null) emailError = null
    }

    fun onPasswordChange(value: String) {
        password = value
        if (passwordError != null) passwordError = null
    }

    fun togglePasswordVisibility() {
        obscurePassword = !obscurePassword
    }

    fun fillWith(credential: TestCredential) {
        email = credential.email
        password = credential.password
        emailError = null
        passwordError = null
    }

    private fun validate(): Boolean {
        val trimmed = email.trim()
        emailError = when {
            trimmed.isEmpty() -> "Informe o seu e-mail."
            !trimmed.contains("@") || !trimmed.contains(".") -> "Informe um e-mail válido."
            else -> null
        }
        passwordError = if (password.isEmpty()) "Informe a sua senha." else null
        return emailError == null && passwordError == null
    }

    fun signIn(onResult: (success: Boolean) -> Unit) {
        if (!validate()) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                session = ServiceLocator.signIn(email = email, password = password)
                onResult(true)
            } catch (failure: AuthFailure) {
                errorMessage = failure.message
                onResult(false)
            } finally {
                isLoading = false
            }
        }
    }
}
