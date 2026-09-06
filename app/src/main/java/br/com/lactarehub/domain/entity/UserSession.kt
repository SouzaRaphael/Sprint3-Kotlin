package br.com.lactarehub.domain.entity

enum class UserRole { DOADORA, ADMINISTRADOR }

data class UserSession(
    val name: String,
    val email: String,
    val role: UserRole,
)

class AuthFailure(message: String) : Exception(message)

data class TestCredential(
    val roleLabel: String,
    val email: String,
    val password: String,
)
