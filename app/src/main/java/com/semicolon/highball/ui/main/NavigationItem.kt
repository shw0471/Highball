package com.semicolon.highball.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector) {
    object AllWhisky : NavigationItem("whisky", Icons.Outlined.List)
    object FavoriteWhisky : NavigationItem("favorite", Icons.Outlined.Favorite)
}
