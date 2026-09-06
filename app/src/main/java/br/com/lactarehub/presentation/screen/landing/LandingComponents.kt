package br.com.lactarehub.presentation.screen.landing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.cardShadow
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.domain.entity.HowItWorksIcon
import br.com.lactarehub.domain.entity.HowItWorksStep
import br.com.lactarehub.domain.entity.ImpactStats
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.LactareLogo
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.component.SecondaryButton
import br.com.lactarehub.presentation.component.StateAvatar
import br.com.lactarehub.presentation.component.StatTile

@Composable
fun LandingHeader(onLogin: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().background(AppColors.Surface)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = AppSpacing.page),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LactareLogo()
            Text(
                text = "Entrar",
                style = AppTextStyles.label.copy(color = AppColors.Primary),
                modifier = Modifier
                    .clickable(onClick = onLogin)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AppColors.Border),
        )
    }
}

@Composable
fun HeroBlob(modifier: Modifier = Modifier, height: Dp = 260.dp) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
        SoftGlow(
            size = height * 1.15f,
            color = AppColors.Accent.copy(alpha = 0.28f),
            modifier = Modifier.offset(x = (-40).dp, y = 30.dp),
        )
        SoftGlow(
            size = height * 0.95f,
            color = AppColors.AccentCyan.copy(alpha = 0.22f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-20).dp),
        )
        val orbSize = height * 0.58f
        Canvas(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-30).dp, y = 10.dp)
                .size(orbSize),
        ) {
            drawCircle(
                brush = AppColors.heroBlob(
                    radiusPx = size.minDimension * 0.9f,
                    center = Offset(size.width * 0.35f, size.height * 0.3f),
                ),
            )
        }
    }
}

@Composable
private fun SoftGlow(size: Dp, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(size)) {
        drawCircle(
            brush = Brush.radialGradient(
                0.35f to color,
                1.0f to color.copy(alpha = 0f),
                center = center,
                radius = this.size.minDimension / 2f,
            ),
        )
    }
}

@Composable
fun HeroSection(
    stats: ImpactStats,
    onStartDonation: () -> Unit,
    onHowItWorks: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth().background(AppColors.BgLanding)) {
        Column(
            modifier = Modifier.padding(
                start = AppSpacing.page,
                top = AppSpacing.xxl,
                end = AppSpacing.page,
            ),
        ) {
            NetworkBadge(stats)
            Spacer(Modifier.height(AppSpacing.xl))
            Headline()
            Spacer(Modifier.height(AppSpacing.lg))
            Text(
                text = "A primeira rede digital que conecta nutrizes a bancos de " +
                    "leite humano em tempo real. Cadastro em 2 minutos, coleta " +
                    "agendada na sua casa e cada mL rastreado até o bebê.",
                style = AppTextStyles.body,
            )
            Spacer(Modifier.height(AppSpacing.xxl))
            PrimaryButton(
                label = "Quero doar leite",
                color = AppColors.PrimaryDeep,
                onClick = onStartDonation,
            )
            Spacer(Modifier.height(AppSpacing.md))
            SecondaryButton(
                label = "Como funciona",
                icon = AppIcons.Play,
                onClick = onHowItWorks,
            )
            Spacer(Modifier.height(AppSpacing.xl))
            SocialProof(stats = stats, states = stats.highlightedStates)
            Spacer(Modifier.height(AppSpacing.xxl))
        }
        HeroBlob()
    }
}

@Composable
private fun NetworkBadge(stats: ImpactStats) {
    Row(
        modifier = Modifier
            .background(AppColors.Border.copy(alpha = 0.55f), AppRadius.pillShape)
            .padding(horizontal = 14.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .background(AppColors.AccentCyan, CircleShape),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "${stats.connectedBanks} BLHs · " +
                "${Formatters.thousands(stats.donorsInNetwork)} doadoras ativas",
            style = AppTextStyles.badge.copy(color = AppColors.Ink),
        )
    }
}

@Composable
private fun Headline() {
    val accent = AppTextStyles.heroTitleAccent
    Text(
        text = buildAnnotatedString {
            append("Cada gota\n")
            withStyle(
                SpanStyle(
                    color = accent.color,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                ),
            ) {
                append("salva")
            }
            append(" uma vida\nde prematuro.")
        },
        style = AppTextStyles.heroTitle,
    )
}

@Composable
private fun SocialProof(stats: ImpactStats, states: List<String>) {
    val roundedDonors = (stats.donorsInNetwork / 100) * 100

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(36.dp + 26.dp * (states.size - 1))
                .height(36.dp),
        ) {
            states.forEachIndexed { index, state ->
                StateAvatar(state = state, modifier = Modifier.offset(x = 26.dp * index))
            }
        }
        Spacer(Modifier.width(AppSpacing.md))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = AppColors.Ink, fontWeight = FontWeight.Bold)) {
                    append("+${Formatters.thousands(roundedDonors)} ")
                }
                append("nutrizes já participam da rede em ${stats.states} estados")
            },
            style = AppTextStyles.statLabel,
        )
    }
}

@Composable
fun ImpactBand(stats: ImpactStats, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Ink)
            .padding(horizontal = AppSpacing.page, vertical = AppSpacing.xxl),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            StatTile(
                value = Formatters.thousands(stats.litersCollected),
                label = "litros coletados em ${stats.collectionYear}",
                onDark = true,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(AppSpacing.lg))
            StatTile(
                value = Formatters.thousands(stats.babiesAssisted),
                label = "bebês atendidos",
                onDark = true,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(Modifier.height(AppSpacing.xxl))
        Row(verticalAlignment = Alignment.Top) {
            StatTile(
                value = Formatters.thousands(stats.donorsInNetwork),
                label = "nutrizes na rede",
                onDark = true,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(AppSpacing.lg))
            StatTile(
                value = "${stats.connectedBanks}",
                label = "BLHs conectados em ${stats.states} estados",
                onDark = true,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun HowItWorksSection(steps: List<HowItWorksStep>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.BgLanding)
            .padding(vertical = AppSpacing.section),
    ) {
        Column(modifier = Modifier.padding(horizontal = AppSpacing.page)) {
            Text(text = "COMO FUNCIONA", style = AppTextStyles.overline)
            Spacer(Modifier.height(AppSpacing.md))
            Text(
                text = buildAnnotatedString {
                    append("Em 3 ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("passos") }
                    append(" você se torna parte da rede.")
                },
                style = AppTextStyles.sectionTitle.withSize(26.0),
            )
        }
        Spacer(Modifier.height(AppSpacing.xl))
        steps.forEach { step ->
            StepCard(
                step = step,
                modifier = Modifier.padding(
                    start = AppSpacing.page,
                    end = AppSpacing.page,
                    bottom = AppSpacing.md,
                ),
            )
        }
    }
}

@Composable
private fun StepCard(step: HowItWorksStep, modifier: Modifier = Modifier) {
    val icon = when (step.icon) {
        HowItWorksIcon.PESSOA -> AppIcons.StepPerson
        HowItWorksIcon.LOCAL -> AppIcons.StepPlace
        HowItWorksIcon.CORACAO -> AppIcons.StepHeart
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .cardShadow(AppRadius.largeCard)
            .background(AppColors.Surface, AppRadius.largeCard)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .padding(AppSpacing.xl),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = step.number,
                style = AppTextStyles.statValue.withSize(26.0).copy(color = AppColors.PrimaryDeep),
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(AppColors.TintBlue, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = AppColors.Primary, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(Modifier.height(AppSpacing.lg))
        Text(text = step.title, style = AppTextStyles.cardTitleBlue.withSize(17.0))
        Spacer(Modifier.height(AppSpacing.sm))
        Text(text = step.description, style = AppTextStyles.bodySmall)
    }
}

@Composable
fun StoriesTeaser(onOpen: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface)
            .padding(horizontal = AppSpacing.page, vertical = AppSpacing.section),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.TintBlue, AppRadius.largeCard)
                .padding(AppSpacing.xl),
        ) {
            Icon(
                AppIcons.Quote,
                contentDescription = null,
                tint = AppColors.Primary,
                modifier = Modifier.size(30.dp),
            )
            Spacer(Modifier.height(AppSpacing.md))
            Text(text = "Histórias que nos movem", style = AppTextStyles.sectionTitle.withSize(21.0))
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = "Pessoas reais, gestos que transformam outras famílias.",
                style = AppTextStyles.bodySmall,
            )
            Spacer(Modifier.height(AppSpacing.lg))
            Row(
                modifier = Modifier.clickable(onClick = onOpen),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Ler depoimentos",
                    style = AppTextStyles.label.copy(color = AppColors.Primary),
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    AppIcons.Forward,
                    contentDescription = null,
                    tint = AppColors.Primary,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LandingFooter(modifier: Modifier = Modifier) {
    val links = listOf("Privacidade & LGPD", "Termos", "Imprensa", "Contato")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.BgLanding)
            .padding(
                start = AppSpacing.page,
                top = AppSpacing.xxl,
                end = AppSpacing.page,
                bottom = AppSpacing.section,
            ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LactareLogo(size = 34.dp, showWordmark = false)
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = "Lactare Conecta", style = AppTextStyles.wordmark)
                Text(text = "Rede de bancos de leite", style = AppTextStyles.caption)
            }
        }
        Spacer(Modifier.height(AppSpacing.xl))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.xl),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.md),
        ) {
            links.forEach { link ->
                Text(
                    text = link,
                    style = AppTextStyles.bodySmall.copy(
                        color = AppColors.PrimaryDark,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }
        }
        Spacer(Modifier.height(AppSpacing.xl))
        Text(
            text = "© 2026 Lactare Conecta. Todos os direitos reservados.",
            style = AppTextStyles.caption,
        )
    }
}
