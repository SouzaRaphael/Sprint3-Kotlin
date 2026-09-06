package br.com.lactarehub.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppTextStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class FeedbackKind { SUCCESS, INFO, ERROR }

private class AppSnackbarVisuals(
    override val message: String,
    val kind: FeedbackKind,
) : SnackbarVisuals {
    override val actionLabel: String? = null
    override val withDismissAction: Boolean = false
    override val duration: SnackbarDuration = SnackbarDuration.Short
}

class AppFeedbackController(
    private val hostState: SnackbarHostState,
    private val scope: CoroutineScope,
) {
    fun success(message: String) = show(message, FeedbackKind.SUCCESS)

    fun info(message: String) = show(message, FeedbackKind.INFO)

    fun error(message: String) = show(message, FeedbackKind.ERROR)

    private fun show(message: String, kind: FeedbackKind) {
        scope.launch {
            hostState.currentSnackbarData?.dismiss()
            hostState.showSnackbar(AppSnackbarVisuals(message, kind))
        }
    }
}

val LocalAppFeedback = staticCompositionLocalOf<AppFeedbackController> {
    error("Nenhum AppFeedbackController disponível nesta árvore.")
}

@Composable
fun AppSnackbar(data: SnackbarData) {
    val visuals = data.visuals
    val kind = (visuals as? AppSnackbarVisuals)?.kind ?: FeedbackKind.INFO

    val accent: Color = when (kind) {
        FeedbackKind.SUCCESS -> AppColors.SuccessFg
        FeedbackKind.INFO -> AppColors.Accent
        FeedbackKind.ERROR -> AppColors.Error
    }
    val icon = when (kind) {
        FeedbackKind.SUCCESS -> AppIcons.CheckCircleFilled
        FeedbackKind.INFO -> AppIcons.InfoFilled
        FeedbackKind.ERROR -> AppIcons.ErrorFilled
    }

    Snackbar(
        modifier = Modifier.padding(16.dp),
        containerColor = AppColors.PrimaryDark,
        contentColor = AppColors.Surface,
        shape = AppRadius.card,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.width(20.dp),
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = visuals.message,
                style = AppTextStyles.bodySmall.copy(
                    color = AppColors.Surface,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }
    }
}

@Composable
fun rememberAppFeedbackController(
    hostState: SnackbarHostState,
    scope: CoroutineScope,
): AppFeedbackController = remember(hostState, scope) { AppFeedbackController(hostState, scope) }
