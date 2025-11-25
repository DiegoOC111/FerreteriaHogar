package com.example.ferreteriahogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.ferreteriahogar.ui.Routes
import com.example.ferreteriahogar.ui.screens.*
import com.example.ferreteriahogar.ui.theme.FerreteriaHogarTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ferreteriahogar.viewModels.InventoryViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    private val requestNotificationPermission =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
        ) { granted ->

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashOnScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }
        lifecycleScope.launch {
            delay(1500)
            keepSplashOnScreen = false
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            val navController = rememberNavController()
            Box(modifier = androidx.compose.ui.Modifier.padding(WindowInsets.systemBars.asPaddingValues())) {
                val viewModel: InventoryViewModel = viewModel()

                NavHost(navController = navController, startDestination = Routes.Login ) {
                    composable(Routes.Login) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            Login(paddingValues = innerPadding, navController)
                        }
                    }
                    composable(Routes.MainMenu  ) {

                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            MainMenu(paddingValues = innerPadding, navController)
                        }
                    }
                    composable(Routes.AdminScreen) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            AdminScreen(
                                paddingValues = innerPadding,
                                navController = navController
                            )
                        }
                    }

                    composable(Routes.AdminUsuarios) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            AdminUsuariosScreen(
                                paddingValues = innerPadding,
                                navController = navController,
                                context = this@MainActivity // o el contexto correcto
                            )
                        }
                    }
                    composable(Routes.AdminInventariosScreen) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            AdminInventariosScreen(
                                paddingValues = innerPadding,
                                navController = navController,
                                context = this@MainActivity // o el contexto correcto
                            )
                        }
                    }
                    composable(Routes.AdminProductos) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            ProductADScreen(
                                paddingValues = innerPadding,
                                navController = navController,
                                context = this@MainActivity // o el contexto correcto
                            )
                        }
                    }
                    composable(Routes.Inventory) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            Inventory(paddingValues = innerPadding, navController, viewModel = viewModel)
                        }
                    }
                    composable(Routes.Hoja_Inventario) {
                        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                            HojaInventario(paddingValues = innerPadding, navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
