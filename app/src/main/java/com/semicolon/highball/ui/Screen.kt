package com.semicolon.highball.ui

import com.semicolon.highball.ui.main.NavigationItem

sealed class Screen(val route: String) {

    object Main: Screen("main/{page}") {
        fun createRoute(page: NavigationItem) = "main/${page.route}"
    }

    object Detail: Screen("{whiskyId}/detail") {
        fun createRoute(whiskyId: Int) = "$whiskyId/detail"
    }
}
