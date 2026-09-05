package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.RegistrationDraft
import br.com.lactarehub.domain.entity.TestCredential
import br.com.lactarehub.domain.entity.UserSession
import br.com.lactarehub.domain.repository.AuthRepository

/** Autentica uma pessoa com e-mail e senha. */
class SignIn(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): UserSession =
        repository.signIn(email = email, password = password)
}

/** Conclui o cadastro de uma nova doadora. */
class RegisterDonor(private val repository: AuthRepository) {
    suspend operator fun invoke(draft: RegistrationDraft): UserSession = repository.register(draft)
}

/** Encerra a sessão da doadora. */
class SignOut(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.signOut()
}

/** Contas de demonstração exibidas na tela de login. */
class GetTestCredentials(private val repository: AuthRepository) {
    operator fun invoke(): List<TestCredential> = repository.listTestCredentials()
}
