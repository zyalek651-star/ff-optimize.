package com.cancer666.ffoptimize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cancer666.ffoptimize.ui.screens.HistoryScreen
import com.cancer666.ffoptimize.ui.screens.HomeScreen
import com.cancer666.ffoptimize.ui.screens.ProfileScreen
import com.cancer666.ffoptimize.ui.screens.TweakScreen
import com.cancer666.ffoptimize.ui.theme.FFOptimizeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as FFApp).container
        setContent {
            FFOptimizeTheme {
                val nav = rememberNavController()
                var selected by remember { mutableIntStateOf(0) }
                val tabs = listOf("Home", "Profile", "Tweaks", "History")
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            tabs.forEachIndexed { i, label ->
                                NavigationBarItem(
                                    selected = selected == i,
                                    onClick = {
                                        selected = i
                                        nav.navigate(label) {
                                            popUpTo(nav.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = when (i) {
                                                0 -> Icons.Filled.Home
                                                1 -> Icons.Filled.Settings
                                                2 -> Icons.Filled.Tune
                                                else -> Icons.Filled.History
                                            },
                                            contentDescription = label
                                        )
                                    },
                                    label = { Text(label) }
                                )
                            }
                        }
                    }
                ) { pad ->
                    NavHost(
                        navController = nav,
                        startDestination = "Home",
                        modifier = Modifier.padding(pad)
                    ) {
                        composable("Home") { HomeScreen(container) }
                        composable("Profile") { ProfileScreen(container) }
                        composable("Tweaks") { TweakScreen(container) }
                        composable("History") { HistoryScreen(container) }
                    }
                }
            }
        }
    }
}
