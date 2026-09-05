package br.com.lactarehub.presentation.screen.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.viewmodel.LandingViewModel
import kotlinx.coroutines.launch

/** Tela 01 do protótipo — apresentação pública do Lactare. */
@Composable
fun LandingScreen(
    onStartDonation: () -> Unit,
    onLogin: () -> Unit,
    onOpenTestimonials: () -> Unit,
    viewModel: LandingViewModel = viewModel(),
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var howItWorksOffset by remember { mutableIntStateOf(0) }

    val stats = viewModel.stats

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgLanding)
            .safeTopPadding(),
    ) {
        LandingHeader(onLogin = onLogin)

        if (viewModel.isLoading || stats == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingBox()
            }
        } else {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                HeroSection(
                    stats = stats,
                    onStartDonation = onStartDonation,
                    // "Como funciona" rola a página até a seção dos três passos.
                    onHowItWorks = {
                        scope.launch { scrollState.animateScrollTo(howItWorksOffset) }
                    },
                )
                ImpactBand(stats = stats)
                HowItWorksSection(
                    steps = viewModel.steps,
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        howItWorksOffset = coordinates.positionInParent().y.toInt()
                    },
                )
                StoriesTeaser(onOpen = onOpenTestimonials)
                LandingFooter()
            }
        }
    }
}
