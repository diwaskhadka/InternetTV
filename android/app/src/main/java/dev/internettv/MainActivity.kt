package dev.internettv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.tv.material3.Text
import dev.internettv.ui.Background
import dev.internettv.ui.InternetTvTheme
import dev.internettv.ui.MainShell
import dev.internettv.ui.Muted
import dev.internettv.ui.PlayerScreen
import dev.internettv.ui.SetupScreen

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternetTvTheme(theme = vm.themeName) {
                Box(Modifier.fillMaxSize().background(Background)) {
                    when (vm.screen) {
                        Screen.Loading -> Text("Loading…", color = Muted, modifier = Modifier.align(Alignment.Center))
                        Screen.Setup -> SetupScreen(vm)
                        Screen.Main -> MainShell(vm)
                        Screen.Player -> PlayerScreen(vm)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Home button, another app or the screen saver: don't keep streaming in the background.
        vm.onAppHidden()
    }
}
