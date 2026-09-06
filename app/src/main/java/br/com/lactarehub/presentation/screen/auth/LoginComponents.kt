package br.com.lactarehub.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.domain.entity.TestCredential

@Composable
fun GoogleSignInButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        shape = AppRadius.pillShape,
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = AppColors.Surface),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        GoogleGlyph()
        Spacer(Modifier.width(12.dp))
        Text(
            text = "Entrar com Google",
            style = AppTextStyles.button.copy(color = AppColors.PrimaryDark),
        )
    }
}

@Composable
private fun GoogleGlyph() {
    Box(modifier = Modifier.size(22.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(
                    brush = Brush.sweepGradient(
                        listOf(
                            Color(0xFF4285F4),
                            Color(0xFF34A853),
                            Color(0xFFFBBC05),
                            Color(0xFFEA4335),
                            Color(0xFF4285F4),
                        ),
                    ),
                    shape = CircleShape,
                ),
        )
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(AppColors.Surface, CircleShape),
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 0.dp)
                .width(11.dp)
                .height(5.dp)
                .background(Color(0xFF4285F4)),
        )
    }
}

@Composable
fun TestCredentialsBox(
    credentials: List<TestCredential>,
    onUseCredential: (TestCredential) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.TintBlue, AppRadius.card)
            .border(1.dp, AppColors.Accent.copy(alpha = 0.35f), AppRadius.card)
            .padding(AppSpacing.lg),
    ) {
        Text(
            text = "CREDENCIAIS DE TESTE",
            style = AppTextStyles.overline.copy(color = AppColors.Primary),
        )
        Spacer(Modifier.height(AppSpacing.md))
        credentials.forEach { credential ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onUseCredential(credential) }
                    .padding(bottom = 6.dp),
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = AppColors.PrimaryDark,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            ),
                        ) {
                            append("${credential.roleLabel}: ")
                        }
                        append("${credential.email} / ${credential.password}")
                    },
                    style = AppTextStyles.bodySmall.copy(color = AppColors.PrimaryDark),
                )
            }
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = "Toque em uma linha para preencher o formulário.",
            style = AppTextStyles.caption.copy(color = AppColors.Primary),
        )
    }
}

@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Spacer(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(AppColors.Border),
        )
        Text(
            text = "ou",
            style = AppTextStyles.caption,
            modifier = Modifier.padding(horizontal = AppSpacing.lg),
        )
        Spacer(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(AppColors.Border),
        )
    }
}
