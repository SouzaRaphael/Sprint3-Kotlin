package br.com.lactarehub.data.repository

import br.com.lactarehub.data.datasource.AuthMockDatasource
import br.com.lactarehub.data.datasource.SessionMockDatasource
import br.com.lactarehub.domain.entity.AuthFailure
import br.com.lactarehub.domain.entity.RegistrationDraft
import br.com.lactarehub.domain.entity.TestCredential
import br.com.lactarehub.domain.entity.UserRole
import br.com.lactarehub.domain.entity.UserSession
import br.com.lactarehub.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    override suspend fun signIn(email: String, password: String): UserSession {
        delay(LATENCY_MS)

        val normalizedEmail = email.trim().lowercase()
        val account = AuthMockDatasource.accounts[normalizedEmail]
        if (account == null || account.password != password) {
            throw AuthFailure(
                "E-mail ou senha incorretos. Confira as credenciais de teste abaixo.",
            )
        }

        SessionMockDatasource.startDemoSession()

        return UserSession(
            name = account.name,
            email = normalizedEmail,
            role = account.role,
        )
    }

    override suspend fun register(draft: RegistrationDraft): UserSession {
        delay(LATENCY_MS)

        SessionMockDatasource.startRegisteredSession(draft)

        return UserSession(
            name = draft.fullName,
            email = draft.email,
            role = UserRole.DOADORA,
        )
    }

    override suspend fun signOut() {
        delay(300)
        SessionMockDatasource.startDemoSession()
    }

    override fun listTestCredentials(): List<TestCredential> =
        AuthMockDatasource.hints.map { hint ->
            TestCredential(
                roleLabel = hint.role,
                email = hint.email,
                password = hint.password,
            )
        }

    private companion object {
        const val LATENCY_MS = 700L
    }
}
