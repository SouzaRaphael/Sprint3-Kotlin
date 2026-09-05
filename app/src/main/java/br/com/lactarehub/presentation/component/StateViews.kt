package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.buttonShadow
import br.com.lactarehub.core.theme.cardShadow
import br.com.lactarehub.core.theme.withSize

/** Indicador de carregamento centralizado, com altura reservada. */
@Composable
fun LoadingBox(modifier: Modifier = Modifier, height: Dp = 220.dp) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = AppColors.Primary)
    }
}

/** Mensagem exibida quando um filtro não devolve resultados. */
@Composable
fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            AppIcons.SearchOff,
            contentDescription = null,
            tint = AppColors.NavInactive,
            modifier = Modifier.size(34.dp),
        )
        Spacer(Modifier.height(12.dp))
        Text(text = message, textAlign = TextAlign.Center, style = AppTextStyles.bodySmall)
    }
}

/**
 * Card que ocupa o lugar de um conteúdo que ainda não existe.
 *
 * Quem acaba de se cadastrar não tem coleta agendada nem doação a rastrear;
 * em vez de esconder as seções, elas explicam o que fará o conteúdo aparecer.
 */
@Composable
fun EmptyStateCard(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    /**
     * Usa o gradiente azul dos cards de destaque, para substituir o card da
     * próxima coleta sem abrir um buraco visual no topo da tela.
     */
    onDarkBackground: Boolean = false,
) {
    val foreground = if (onDarkBackground) AppColors.Surface else AppColors.PrimaryDark
    val secondary = if (onDarkBackground) {
        AppColors.Surface.copy(alpha = 0.85f)
    } else {
        AppColors.InkMuted
    }

    var widthPx by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .onSizeChanged { widthPx = it.width }
            .then(
                if (onDarkBackground) {
                    Modifier.buttonShadow(AppRadius.largeCard)
                } else {
                    Modifier.cardShadow(AppRadius.largeCard)
                },
            )
            .clip(AppRadius.largeCard)
            .then(
                if (onDarkBackground) {
                    Modifier.background(AppColors.heroCard(widthPx.toFloat()))
                } else {
                    Modifier
                        .background(AppColors.Surface)
                        .border(1.dp, AppColors.Border, AppRadius.largeCard)
                },
            )
            .padding(AppSpacing.xl),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = if (onDarkBackground) {
                        AppColors.Surface.copy(alpha = 0.18f)
                    } else {
                        AppColors.TintBlue
                    },
                    shape = RoundedCornerShape(13.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (onDarkBackground) AppColors.Surface else AppColors.Primary,
                modifier = Modifier.size(22.dp),
            )
        }
        Spacer(Modifier.height(AppSpacing.lg))
        Text(
            text = title,
            style = AppTextStyles.cardTitleBlue.withSize(17.0).copy(color = foreground),
        )
        Spacer(Modifier.height(AppSpacing.sm))
        Text(text = message, style = AppTextStyles.bodySmall.copy(color = secondary))

        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(AppSpacing.lg))
            CardActionPill(
                label = actionLabel,
                onClick = onAction,
                onDarkBackground = onDarkBackground,
            )
        }
    }
}

/** Botão em pílula que se adapta ao fundo claro ou ao gradiente azul. */
@Composable
private fun CardActionPill(
    label: String,
    onClick: () -> Unit,
    onDarkBackground: Boolean,
) {
    val background = if (onDarkBackground) AppColors.Surface else AppColors.Primary
    val foreground = if (onDarkBackground) AppColors.PrimaryDark else AppColors.Surface

    Row(
        modifier = Modifier
            .clip(AppRadius.pillShape)
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = AppSpacing.xl, vertical = AppSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = AppTextStyles.label.withSize(14.5).copy(color = foreground),
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            AppIcons.Forward,
            contentDescription = null,
            tint = foreground,
            modifier = Modifier.size(17.dp),
        )
    }
}

/** Bloco azul-claro com ícone e texto — usado como nota de rodapé nas telas. */
@Composable
fun InfoNoteCard(
    icon: ImageVector,
    message: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.TintBlue, AppRadius.card)
            .padding(AppSpacing.lg),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(icon, contentDescription = null, tint = AppColors.Primary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(AppSpacing.md))
        Text(
            text = message,
            style = AppTextStyles.bodySmall.copy(color = AppColors.PrimaryDark),
        )
    }
}

/** Gradiente azul dos cards de destaque, medindo a própria largura. */
@Composable
fun HeroGradientBox(
    modifier: Modifier = Modifier,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    var widthPx by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .onSizeChanged { widthPx = it.width }
            .buttonShadow(AppRadius.largeCard)
            .clip(AppRadius.largeCard)
            .background(AppColors.heroCard(widthPx.toFloat()))
            .padding(AppSpacing.xl),
        content = content,
    )
}

/** Card branco com borda — o contêiner mais comum do design. */
@Composable
fun SurfaceCard(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = AppRadius.largeCard,
    padding: Dp = AppSpacing.lg,
    withShadow: Boolean = false,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (withShadow) Modifier.cardShadow(shape) else Modifier)
            .clip(shape)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, shape)
            .padding(padding),
        content = content,
    )
}

/** Pincel do gradiente dos avatares e conquistas. */
fun avatarBrush(gradientIndex: Int): Brush =
    Brush.linearGradient(AppColors.avatarGradient(gradientIndex))
