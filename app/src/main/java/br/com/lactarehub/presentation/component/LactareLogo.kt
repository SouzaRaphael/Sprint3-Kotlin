package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppTextStyles

/** Formatos da marca usados no design. */
enum class LactareLogoVariant {
    /** Círculo azul com a letra L — landing, login e splash. */
    CIRCLE,

    /** Quadrado arredondado com anel interno — cabeçalho da área da doadora. */
    ROUNDED,
}

/** Símbolo do Lactare, opcionalmente acompanhado do nome. */
@Composable
fun LactareLogo(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    showWordmark: Boolean = true,
    variant: LactareLogoVariant = LactareLogoVariant.CIRCLE,
    wordmarkColor: Color = AppColors.Primary,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        when (variant) {
            LactareLogoVariant.CIRCLE -> Box(
                modifier = Modifier
                    .size(size)
                    .background(AppColors.Primary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "L",
                    style = AppTextStyles.wordmark.copy(
                        color = AppColors.Surface,
                        fontSize = (size.value * 0.5f).sp,
                    ),
                )
            }

            LactareLogoVariant.ROUNDED -> Box(
                modifier = Modifier
                    .size(size)
                    .background(AppColors.Primary, RoundedCornerShape(size * 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(size * 0.46f)
                        .border(size * 0.09f, AppColors.Accent, CircleShape),
                )
            }
        }

        if (showWordmark) {
            Spacer(Modifier.width(10.dp))
            Text(text = "Lactare", style = AppTextStyles.wordmark.copy(color = wordmarkColor))
        }
    }
}
