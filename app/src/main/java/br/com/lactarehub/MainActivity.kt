package br.com.lactarehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.LactareHubTheme
import br.com.lactarehub.presentation.LactareApp

/**
 * Porta de entrada do aplicativo.
 *
 * Equivale ao `main()` + `LactareApp` do projeto Flutter: monta o tema e
 * entrega a navegação. O grafo de dependências vive no `ServiceLocator`, que
 * é um objeto e se inicializa no primeiro acesso.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // O design de referência é claro: barras transparentes com ícones
        // escuros, como no `SystemUiOverlayStyle` do tema Flutter.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                AppColors.Surface.toArgb(),
                AppColors.Surface.toArgb(),
            ),
        )
        setContent {
            LactareHubTheme {
                LactareApp()
            }
        }
    }
}
