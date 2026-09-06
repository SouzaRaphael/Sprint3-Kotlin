package br.com.lactarehub.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTextField
import br.com.lactarehub.presentation.component.LactareLogo
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onSignedIn: () -> Unit,
    onRegister: () -> Unit,
    goBack: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
) {
    val feedback = LocalAppFeedback.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                start = AppSpacing.page,
                top = AppSpacing.lg,
                end = AppSpacing.page,
                bottom = AppSpacing.section,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LactareLogo()
            IconButton(onClick = goBack) {
                Icon(AppIcons.Close, contentDescription = "Voltar", tint = AppColors.NavInactive)
            }
        }

        Spacer(Modifier.height(AppSpacing.xxl))
        Text(text = "Bem-vinda de volta", style = AppTextStyles.heroTitle.withSize(27.0))
        Spacer(Modifier.height(AppSpacing.sm))
        Text(
            text = "Entre na sua conta para continuar fazendo a diferença.",
            style = AppTextStyles.body,
        )

        Spacer(Modifier.height(AppSpacing.xxl))
        AppTextField(
            label = "E-mail",
            hint = "seu@email.com",
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            keyboardType = KeyboardType.Email,
            errorMessage = viewModel.emailError,
        )

        Spacer(Modifier.height(AppSpacing.lg))
        AppTextField(
            label = "Senha",
            hint = "Sua senha",
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            keyboardType = KeyboardType.Password,
            obscureText = viewModel.obscurePassword,
            errorMessage = viewModel.passwordError,
            trailingLabel = "Esqueceu a senha?",
            onTrailingLabelClick = {
                feedback.info("Enviamos um link de redefinição para o seu e-mail.")
            },
            suffix = {
                IconButton(onClick = viewModel::togglePasswordVisibility) {
                    Icon(
                        imageVector = if (viewModel.obscurePassword) {
                            AppIcons.Visibility
                        } else {
                            AppIcons.VisibilityOff
                        },
                        contentDescription = if (viewModel.obscurePassword) {
                            "Mostrar senha"
                        } else {
                            "Ocultar senha"
                        },
                        tint = AppColors.NavInactive,
                        modifier = Modifier.size(20.dp),
                    )
                }
            },
        )

        Spacer(Modifier.height(AppSpacing.xl))
        PrimaryButton(
            label = "Entrar",
            isLoading = viewModel.isLoading,
            onClick = {
                viewModel.signIn { success ->
                    if (success) {
                        feedback.success("Bem-vinda de volta, ${viewModel.session?.name.orEmpty()}!")
                        onSignedIn()
                    } else {
                        feedback.error(viewModel.errorMessage ?: "Não foi possível entrar.")
                    }
                }
            },
        )

        Spacer(Modifier.height(AppSpacing.xl))
        OrDivider()

        Spacer(Modifier.height(AppSpacing.xl))
        GoogleSignInButton(
            onClick = { feedback.info("Acesso com Google disponível na próxima entrega.") },
        )

        Spacer(Modifier.height(AppSpacing.xl))
        Text(
            text = buildAnnotatedString {
                append("Ainda não tem conta? ")
                withStyle(SpanStyle(color = AppColors.Primary, fontWeight = FontWeight.Bold)) {
                    append("Cadastre-se")
                }
            },
            style = AppTextStyles.bodySmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onRegister)
                .padding(8.dp),
        )

        Spacer(Modifier.height(AppSpacing.xl))
        TestCredentialsBox(
            credentials = viewModel.testCredentials,
            onUseCredential = { credential ->
                viewModel.fillWith(credential)
                feedback.info(
                    "Credenciais de ${credential.roleLabel} preenchidas. Toque em Entrar.",
                )
            },
        )

        Spacer(Modifier.height(AppSpacing.xxl))
        Text(
            text = "© 2026 Lactare. Todos os direitos reservados.",
            style = AppTextStyles.caption,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
