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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
