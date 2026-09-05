package br.com.lactarehub.domain.entity

/** Perfis atendidos pelo login mockado. */
enum class UserRole { DOADORA, ADMINISTRADOR }

/** Sessão autenticada em memória. */
data class UserSession(
    val name: String,
    val email: String,
    val role: UserRole,
)

/** Falha de autenticação com mensagem já pronta para a interface. */
class AuthFailure(message: String) : Exception(message)

/** Credencial de demonstração exibida na tela de login. */
data class TestCredential(
    val roleLabel: String,
    val email: String,
    val password: String,
)
