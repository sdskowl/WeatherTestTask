package com.example.weathertesttask.root

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weathertesttask.ui.theme.WeatherTestTaskTheme
import com.example.weathertesttask.ui.screens.first_sreen.FirstScreenScreen
import com.example.weathertesttask.ui.screens.second_sreen.SecondScreenScreen
import com.example.weathertesttask.ui.screens.second_sreen.viewmodel.SecondScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTestTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = FIRST_SCREEN_ROUTE
                    ) {
                        composable(route = FIRST_SCREEN_ROUTE) {
                            FirstScreenScreen(navigateToSecond = { city, days, reportMode ->
                                navController.navigate(
                                    secondScreenRoutWithArgs(
                                        city,
                                        days,
                                        reportMode
                                    )
                                )
                            })
                        }

                        composable(route = SECOND_SCREEN_ROUTE_WITH_ARGS, arguments = arguments) { entry ->
                            val city = entry.arguments?.getString(CITY) ?: ""
                            val days = entry.arguments?.getString(DAYS) ?: ""
                            val isFullMode = entry.arguments?.getBoolean(REPORT_MODE) ?: ""
                            Log.e("entry", "city: $city, days: $days, isFullMode: $isFullMode")
                            val vm: SecondScreenViewModel = koinViewModel{
                                parametersOf(city, days, isFullMode)
                            }
                            SecondScreenScreen(vm)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val FIRST_SCREEN_ROUTE = "first"
        const val SECOND_SCREEN_ROUTE = "second"
        const val CITY = "city"
        const val DAYS = "days"
        const val REPORT_MODE = "reportMode"
        const val SECOND_SCREEN_ROUTE_WITH_ARGS =
            "$SECOND_SCREEN_ROUTE?$CITY={$CITY}&$DAYS={$DAYS}&$REPORT_MODE={$REPORT_MODE}"
        val arguments = listOf(
            navArgument(CITY) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(DAYS) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(REPORT_MODE) {
                type = NavType.BoolType
                defaultValue = false
            }
        )

        fun secondScreenRoutWithArgs(city: String, days: String, reportMode: Boolean): String {
            return "$SECOND_SCREEN_ROUTE?$CITY=$city&$DAYS=$days&$REPORT_MODE=$reportMode"
        }
    }
}



