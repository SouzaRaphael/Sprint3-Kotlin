package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.RegistrationDraft
import br.com.lactarehub.domain.entity.TestCredential
import br.com.lactarehub.domain.entity.UserSession
import br.com.lactarehub.domain.repository.AuthRepository

class SignIn(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): UserSession =
        repository.signIn(email = email, password = password)
}

class RegisterDonor(private val repository: AuthRepository) {
    suspend operator fun invoke(draft: RegistrationDraft): UserSession = repository.register(draft)
}

class SignOut(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.signOut()
}

class GetTestCredentials(private val repository: AuthRepository) {
    operator fun invoke(): List<TestCredential> = repository.listTestCredentials()
}
