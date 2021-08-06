package com.semicolon.highball.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.semicolon.highball.ui.detail.WhiskyDetail
import com.semicolon.highball.ui.main.Main
import com.semicolon.highball.ui.main.NavigationItem
import com.semicolon.highball.viewmodel.WhiskyViewModel

@ExperimentalCoilApi
@Composable
fun HighballApp(
    whiskyViewModel: WhiskyViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main/whisky"
    ) {
        composable("main/whisky") {
            Main(
                navController = navController,
                whiskyViewModel = whiskyViewModel,
                page = "whisky"
            )
        }

        composable(Screen.Main.route) {
            val page = it.arguments?.getString("page")
            requireNotNull(page)
            Main(
                navController = navController,
                whiskyViewModel = whiskyViewModel,
                page = page
            )
        }

        composable(Screen.Detail.route) {
            val whiskyId = it.arguments?.getString("whiskyId")
            requireNotNull(whiskyId)
            WhiskyDetail(
                id =  whiskyId.toInt(),
                whiskyViewModel = whiskyViewModel,
                navController = navController
            )
        }
    }
}