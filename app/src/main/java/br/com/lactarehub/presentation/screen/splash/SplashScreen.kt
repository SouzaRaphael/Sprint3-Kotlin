package br.com.lactarehub.presentation.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.presentation.component.LactareLogo
import kotlinx.coroutines.delay

/** Tempo de exibição da marca antes de seguir para a home pública. */
private const val SPLASH_DURATION_MS = 2000L

/** Tela de abertura: apresenta a marca e segue para a home pública. */
@Composable
fun SplashScreen(goToLanding: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "splash-fade",
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(SPLASH_DURATION_MS)
        goToLanding()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .alpha(progress)
                .scale(0.86f + 0.14f * progress),
        ) {
            LactareLogo(size = 72.dp, showWordmark = false)
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Lactare",
                style = AppTextStyles.heroTitle.withSize(38.0).copy(color = AppColors.Primary),
            )
            Spacer(Modifier.height(10.dp))
            Text(text = "Rede de bancos de leite humano", style = AppTextStyles.bodySmall)
            Spacer(Modifier.height(40.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(26.dp),
                strokeWidth = 2.4.dp,
                color = AppColors.Primary,
            )
        }
    }
}
