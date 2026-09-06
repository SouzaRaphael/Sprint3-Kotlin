package br.com.lactarehub.presentation.screen.testimonials

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeBottomPadding
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.AvatarCircle
import br.com.lactarehub.presentation.component.EmptyState
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.component.SegmentedFilter
import br.com.lactarehub.presentation.component.TestimonialBadge
import br.com.lactarehub.presentation.viewmodel.TestimonialsViewModel

@Composable
fun TestimonialsScreen(
    goBack: () -> Unit,
    onWriteTestimonial: () -> Unit,
    viewModel: TestimonialsViewModel = viewModel(),
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { viewModel.refresh() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Surface)
            .safeTopPadding(),
    ) {
        AppTopBar(title = "Depoimentos", onBack = goBack)

        Column(
            modifier = Modifier.padding(
                start = AppSpacing.page,
                top = AppSpacing.xl,
                end = AppSpacing.page,
                bottom = AppSpacing.lg,
            ),
        ) {
            Text(
                text = "Histórias que nos movem",
                style = AppTextStyles.heroTitle.withSize(25.0).copy(color = AppColors.PrimaryDark),
            )
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = "Pessoas reais, gestos que transformam outras famílias.",
                style = AppTextStyles.body,
            )
            Spacer(Modifier.height(AppSpacing.xl))
            SegmentedFilter(
                labels = viewModel.filterLabels,
                selectedIndex = viewModel.selectedFilterIndex,
                onSelected = viewModel::selectFilter,
            )
        }

        when {
            viewModel.isLoading -> LoadingBox()

            viewModel.testimonials.isEmpty() -> EmptyState(
                message = "Ainda não há depoimentos nesse filtro. Que tal ser a " +
                    "primeira a escrever?",
            )

            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .safeBottomPadding(),
                contentPadding = PaddingValues(
                    start = AppSpacing.page,
                    end = AppSpacing.page,
                    bottom = AppSpacing.section,
                ),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.md),
            ) {
                items(viewModel.testimonials, key = { it.id }) { testimonial ->
                    TestimonialCard(testimonial)
                }
                item {
                    Spacer(Modifier.height(AppSpacing.lg))
                    ShareStoryCard(onWrite = onWriteTestimonial)
                }
            }
        }
    }
}

@Composable
fun TestimonialCard(testimonial: Testimonial, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface, AppRadius.largeCard)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .padding(AppSpacing.lg),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            AvatarCircle(
                name = testimonial.authorName,
                gradientIndex = testimonial.avatarGradientIndex,
            )
            Spacer(Modifier.width(AppSpacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = testimonial.authorName,
                    style = AppTextStyles.cardTitleBlue.withSize(15.0),
                )
                Spacer(Modifier.height(2.dp))
                Text(text = testimonial.cityAndState, style = AppTextStyles.caption)
            }
            Spacer(Modifier.width(AppSpacing.sm))
            TestimonialBadge(testimonial.type)
        }
        Spacer(Modifier.height(AppSpacing.lg))
        Row(verticalAlignment = Alignment.Top) {
            Icon(
                AppIcons.Quote,
                contentDescription = null,
                tint = AppColors.Accent,
                modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.width(6.dp))
            Text(text = testimonial.message, style = AppTextStyles.quote)
        }
    }
}

@Composable
fun ShareStoryCard(onWrite: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.TintBlue, AppRadius.largeCard)
            .padding(AppSpacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            AppIcons.Chat,
            contentDescription = null,
            tint = AppColors.Primary,
            modifier = Modifier.size(30.dp),
        )
        Spacer(Modifier.height(AppSpacing.md))
        Text(text = "Compartilhe sua história", style = AppTextStyles.sectionTitle.withSize(19.0))
        Spacer(Modifier.height(AppSpacing.sm))
        Text(
            text = "Sua jornada inspira outras pessoas a serem doadoras.",
            textAlign = TextAlign.Center,
            style = AppTextStyles.bodySmall,
        )
        Spacer(Modifier.height(AppSpacing.lg))
        PrimaryButton(
            label = "Escrever depoimento",
            icon = AppIcons.Edit,
            showTrailingIcon = false,
            expand = false,
            onClick = onWrite,
        )
    }
}
