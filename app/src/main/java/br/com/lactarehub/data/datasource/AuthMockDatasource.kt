package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.UserRole

data class MockAccount(
    val password: String,
    val name: String,
    val role: UserRole,
)

data class CredentialHint(
    val role: String,
    val email: String,
    val password: String,
)

object AuthMockDatasource {

    val accounts: Map<String, MockAccount> = mapOf(
        "giovana@email.com" to MockAccount(
            password = "doadora123",
            name = "Giovana",
            role = UserRole.DOADORA,
        ),
    )

    val hints: List<CredentialHint> = listOf(
        CredentialHint(role = "Doadora", email = "giovana@email.com", password = "doadora123"),
    )
}
