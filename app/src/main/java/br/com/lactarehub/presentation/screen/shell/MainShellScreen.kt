package br.com.lactarehub.presentation.screen.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.presentation.component.AppBottomNav
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.BottomNavItem
import br.com.lactarehub.presentation.screen.content.ContentScreen
import br.com.lactarehub.presentation.screen.donorhome.DonorHomeScreen
import br.com.lactarehub.presentation.screen.myarea.MyAreaScreen
import br.com.lactarehub.presentation.screen.points.CollectionPointsScreen
import br.com.lactarehub.presentation.screen.schedule.ScheduleCollectionScreen
import br.com.lactarehub.presentation.viewmodel.CollectionPointsViewModel
import br.com.lactarehub.presentation.viewmodel.ContentViewModel
import br.com.lactarehub.presentation.viewmodel.DonorHomeViewModel
import br.com.lactarehub.presentation.viewmodel.MyAreaViewModel
import br.com.lactarehub.presentation.viewmodel.ScheduleViewModel

enum class ShellTab { INICIO, DOAR, PONTOS, CONTEUDO, EU }

private val navItems = listOf(
    BottomNavItem(icon = AppIcons.TabHome, label = "Início"),
    BottomNavItem(icon = AppIcons.TabDonate, label = "Doar"),
    BottomNavItem(icon = AppIcons.TabPoints, label = "Pontos"),
    BottomNavItem(icon = AppIcons.TabContent, label = "Conteúdo"),
    BottomNavItem(icon = AppIcons.TabMe, label = "Eu"),
)

@Composable
fun MainShellScreen(
    currentTab: ShellTab,
    onTabChange: (ShellTab) -> Unit,
    onOpenTestimonials: () -> Unit,
    onOpenArticle: (Article) -> Unit,
    onOpenCollectionPoint: (CollectionPoint) -> Unit,
    onOpenDonation: (Donation) -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val homeViewModel: DonorHomeViewModel = viewModel()
    val scheduleViewModel: ScheduleViewModel = viewModel()
    val pointsViewModel: CollectionPointsViewModel = viewModel()
    val contentViewModel: ContentViewModel = viewModel()
    val myAreaViewModel: MyAreaViewModel = viewModel()

    val homeListState = rememberLazyListState()
    val contentListState = rememberLazyListState()
    val myAreaListState = rememberLazyListState()

    LaunchedEffect(currentTab) {
        when (currentTab) {
            ShellTab.INICIO -> homeViewModel.refresh()
            ShellTab.EU -> myAreaViewModel.refresh()
            ShellTab.DOAR -> scheduleViewModel.load()
            else -> Unit
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (currentTab) {
                ShellTab.INICIO -> DonorHomeScreen(
                    viewModel = homeViewModel,
                    onOpenSchedule = { onTabChange(ShellTab.DOAR) },
                    onOpenPoints = { onTabChange(ShellTab.PONTOS) },
                    onOpenContent = { onTabChange(ShellTab.CONTEUDO) },
                    onOpenMyArea = { onTabChange(ShellTab.EU) },
                    onOpenArticle = onOpenArticle,
                    onOpenDonation = onOpenDonation,
                    listState = homeListState,
                )

                ShellTab.DOAR -> ScheduleCollectionScreen(
                    viewModel = scheduleViewModel,
                    onScheduled = { onTabChange(ShellTab.INICIO) },
                )

                ShellTab.PONTOS -> CollectionPointsScreen(
                    viewModel = pointsViewModel,
                    onOpenPoint = onOpenCollectionPoint,
                )

                ShellTab.CONTEUDO -> ContentScreen(
                    viewModel = contentViewModel,
                    onOpenArticle = onOpenArticle,
                    listState = contentListState,
                )

                ShellTab.EU -> MyAreaScreen(
                    viewModel = myAreaViewModel,
                    onOpenSchedule = { onTabChange(ShellTab.DOAR) },
                    onOpenArticle = onOpenArticle,
                    onOpenDonation = onOpenDonation,
                    onOpenTestimonials = onOpenTestimonials,
                    onOpenProfile = onOpenProfile,
                    listState = myAreaListState,
                )
            }
        }

        AppBottomNav(
            items = navItems,
            currentIndex = currentTab.ordinal,
            onSelected = { index -> onTabChange(ShellTab.entries[index]) },
        )
    }
}
