package br.com.lactarehub.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.lactarehub.presentation.component.AppSnackbar
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.rememberAppFeedbackController
import br.com.lactarehub.presentation.navigation.AppRoutes
import br.com.lactarehub.presentation.screen.auth.LoginScreen
import br.com.lactarehub.presentation.screen.content.ArticleDetailScreen
import br.com.lactarehub.presentation.screen.donation.DonationDetailScreen
import br.com.lactarehub.presentation.screen.landing.LandingScreen
import br.com.lactarehub.presentation.screen.points.CollectionPointDetailScreen
import br.com.lactarehub.presentation.screen.profile.ProfileScreen
import br.com.lactarehub.presentation.screen.registration.RegistrationScreen
import br.com.lactarehub.presentation.screen.registration.RegistrationSuccessScreen
import br.com.lactarehub.presentation.screen.shell.MainShellScreen
import br.com.lactarehub.presentation.screen.shell.ShellTab
import br.com.lactarehub.presentation.screen.splash.SplashScreen
import br.com.lactarehub.presentation.screen.testimonials.TestimonialsScreen
import br.com.lactarehub.presentation.screen.testimonials.WriteTestimonialScreen

/**
 * Roteamento central do aplicativo.
 *
 * Cada tela recebe apenas callbacks de navegação, o que a mantém ignorante
 * sobre o `NavController` e fácil de testar isoladamente — a mesma divisão do
 * `AppNavigation` do projeto Flutter.
 */
@Composable
fun LactareApp() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val feedback = rememberAppFeedbackController(snackbarHostState, scope)

    // A aba ativa da casca vive acima do NavHost: é assim que o perfil
    // consegue pedir "abra o agendamento" ao voltar, e que o login e o
    // cadastro escolhem em qual aba a casca abre.
    var shellTab by rememberSaveable { mutableStateOf(ShellTab.INICIO) }

    /** Abre a casca autenticada já na aba pedida, limpando a pilha pública. */
    fun openApp(tab: ShellTab) {
        shellTab = tab
        navController.navigate(AppRoutes.APP) {
            popUpTo(AppRoutes.LANDING) { inclusive = false }
            launchSingleTop = true
        }
    }

    CompositionLocalProvider(LocalAppFeedback provides feedback) {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = AppRoutes.SPLASH,
            ) {
                composable(AppRoutes.SPLASH) {
                    SplashScreen(
                        goToLanding = {
                            navController.navigate(AppRoutes.LANDING) {
                                popUpTo(AppRoutes.SPLASH) { inclusive = true }
                            }
                        },
                    )
                }

                composable(AppRoutes.LANDING) {
                    LandingScreen(
                        onStartDonation = { navController.navigate(AppRoutes.REGISTRATION) },
                        onLogin = { navController.navigate(AppRoutes.LOGIN) },
                        onOpenTestimonials = { navController.navigate(AppRoutes.TESTIMONIALS) },
                    )
                }

                composable(AppRoutes.LOGIN) {
                    LoginScreen(
                        onSignedIn = { openApp(ShellTab.INICIO) },
                        onRegister = {
                            navController.navigate(AppRoutes.REGISTRATION) {
                                popUpTo(AppRoutes.LOGIN) { inclusive = true }
                            }
                        },
                        goBack = { navController.popBackStack() },
                    )
                }

                composable(AppRoutes.REGISTRATION) {
                    RegistrationScreen(
                        onCompleted = {
                            navController.navigate(AppRoutes.REGISTRATION_SUCCESS) {
                                popUpTo(AppRoutes.REGISTRATION) { inclusive = true }
                            }
                        },
                        goBack = { navController.popBackStack() },
                    )
                }

                composable(AppRoutes.REGISTRATION_SUCCESS) {
                    RegistrationSuccessScreen(onEnterApp = { openApp(ShellTab.INICIO) })
                }

                composable(AppRoutes.APP) {
                    MainShellScreen(
                        currentTab = shellTab,
                        onTabChange = { shellTab = it },
                        onOpenTestimonials = { navController.navigate(AppRoutes.TESTIMONIALS) },
                        onOpenArticle = { article ->
                            navController.navigate(AppRoutes.articleDetail(article.id))
                        },
                        onOpenCollectionPoint = { point ->
                            navController.navigate(AppRoutes.collectionPointDetail(point.id))
                        },
                        onOpenDonation = { donation ->
                            navController.navigate(AppRoutes.donationDetail(donation.code))
                        },
                        onOpenProfile = { navController.navigate(AppRoutes.PROFILE) },
                    )
                }

                composable(AppRoutes.TESTIMONIALS) {
                    TestimonialsScreen(
                        goBack = { navController.popBackStack() },
                        onWriteTestimonial = {
                            navController.navigate(AppRoutes.WRITE_TESTIMONIAL)
                        },
                    )
                }

                composable(AppRoutes.WRITE_TESTIMONIAL) {
                    WriteTestimonialScreen(goBack = { navController.popBackStack() })
                }

                composable(
                    route = AppRoutes.ARTICLE_DETAIL,
                    arguments = listOf(
                        navArgument(AppRoutes.ARG_ARTICLE_ID) { type = NavType.StringType },
                    ),
                ) { entry ->
                    ArticleDetailScreen(
                        articleId = entry.arguments
                            ?.getString(AppRoutes.ARG_ARTICLE_ID)
                            .orEmpty(),
                        goBack = { navController.popBackStack() },
                    )
                }

                composable(
                    route = AppRoutes.COLLECTION_POINT_DETAIL,
                    arguments = listOf(
                        navArgument(AppRoutes.ARG_POINT_ID) { type = NavType.StringType },
                    ),
                ) { entry ->
                    CollectionPointDetailScreen(
                        pointId = entry.arguments?.getString(AppRoutes.ARG_POINT_ID).orEmpty(),
                        goBack = { navController.popBackStack() },
                    )
                }

                composable(
                    route = AppRoutes.DONATION_DETAIL,
                    arguments = listOf(
                        navArgument(AppRoutes.ARG_DONATION_CODE) { type = NavType.StringType },
                    ),
                ) { entry ->
                    DonationDetailScreen(
                        donationCode = entry.arguments
                            ?.getString(AppRoutes.ARG_DONATION_CODE)
                            .orEmpty(),
                        goBack = { navController.popBackStack() },
                    )
                }

                composable(AppRoutes.PROFILE) {
                    ProfileScreen(
                        goBack = { navController.popBackStack() },
                        // Fecha o perfil pedindo à casca que abra a aba de
                        // agendamento — o `Navigator.pop(context, true)` do Flutter.
                        onScheduleCollection = {
                            shellTab = ShellTab.DOAR
                            navController.popBackStack()
                        },
                        onSignedOut = { signOutTo(navController) },
                    )
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding(),
            ) { data -> AppSnackbar(data) }
        }
    }
}

/** Encerra a sessão e devolve o aplicativo à home pública, sem pilha. */
private fun signOutTo(navController: NavHostController) {
    navController.navigate(AppRoutes.LANDING) {
        popUpTo(navController.graph.id) { inclusive = true }
    }
}
