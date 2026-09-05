package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.UserRole

/** Conta aceita pelo login mockado. */
data class MockAccount(
    val password: String,
    val name: String,
    val role: UserRole,
)

/** Dica exibida na caixa "Credenciais de teste" da tela de login. */
data class CredentialHint(
    val role: String,
    val email: String,
    val password: String,
)

/**
 * Credenciais aceitas pelo login mockado.
 *
 * São exatamente as exibidas na caixa "Credenciais de teste" da tela de
 * login, para que o avaliador consiga entrar sem consultar o código.
 */
object AuthMockDatasource {

    val accounts: Map<String, MockAccount> = mapOf(
        // "admin@lactare.com.br" to MockAccount(
        //     password = "admin123",
        //     name = "Equipe Lactare",
        //     role = UserRole.ADMINISTRADOR,
        // ),
        "giovana@email.com" to MockAccount(
            password = "doadora123",
            name = "Giovana",
            role = UserRole.DOADORA,
        ),
    )

    /** Rótulos da caixa de credenciais de teste da tela de login. */
    val hints: List<CredentialHint> = listOf(
        // CredentialHint(role = "Admin", email = "admin@lactare.com.br", password = "admin123"),
        CredentialHint(role = "Doadora", email = "giovana@email.com", password = "doadora123"),
    )
}
