package com.semicolon.highball.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.semicolon.highball.ui.Screen

@Composable
fun WhiskyBottomNavigation(
    navController: NavController
) {
    WhiskyBottomNavigation {
        navController.navigate(Screen.Main.createRoute(it))
    }
}

@Composable
fun WhiskyBottomNavigation(
    onClick: (item: NavigationItem) -> Unit
) {
    val navigationItems = listOf(
        NavigationItem.AllWhisky,
        NavigationItem.FavoriteWhisky
    )

    val backgroundColor =
        if (isSystemInDarkTheme()) Color.Black else Color.White

    val contentColor =
        if (isSystemInDarkTheme()) Color.White else Color.Black

    BottomNavigation(
        backgroundColor = backgroundColor,
        contentColor = contentColor
    ) {
        navigationItems.forEach {
            BottomNavigationItem(icon = {
                Icon(
                    imageVector = it.icon,
                    contentDescription = it.route
                )
            }, selected = false, onClick = { onClick(it) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WhiskyBottomNavigationPreview() {
    WhiskyBottomNavigation {}
}