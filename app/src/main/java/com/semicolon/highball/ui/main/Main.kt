package com.semicolon.highball.ui.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.semicolon.highball.ui.all.AllWhisky
import com.semicolon.highball.ui.favorite.FavoriteWhisky
import com.semicolon.highball.viewmodel.WhiskyViewModel

@Composable
fun Main(
    navController: NavController,
    whiskyViewModel: WhiskyViewModel,
    page: String
) {
    Scaffold(
        bottomBar = { WhiskyBottomNavigation(navController) }
    ) {
        if (page == NavigationItem.AllWhisky.route)
            AllWhisky(
                navController = navController,
                whiskyViewModel = whiskyViewModel
            )
        else if (page == NavigationItem.FavoriteWhisky.route)
            FavoriteWhisky(
                navController = navController,
                whiskyViewModel = whiskyViewModel
            )

    }
}