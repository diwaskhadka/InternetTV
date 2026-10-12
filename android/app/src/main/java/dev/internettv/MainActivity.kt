package dev.internettv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.Text
import dev.internettv.ui.Background
import dev.internettv.ui.BrowseScreen
import dev.internettv.ui.InternetTvTheme
import dev.internettv.ui.Muted
import dev.internettv.ui.PlayerScreen
import dev.internettv.ui.SetupScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternetTvTheme {
                val vm: MainViewModel = viewModel()
                Box(Modifier.fillMaxSize().background(Background)) {
                    when (val s = vm.screen) {
                        Screen.Loading -> Text("Loading…", color = Muted, modifier = Modifier.align(Alignment.Center))
                        Screen.Setup -> SetupScreen(vm)
                        Screen.Browse -> BrowseScreen(vm)
                        is Screen.Player -> PlayerScreen(vm, s.playlist, s.index)
                    }
                }
            }
        }
    }
}
