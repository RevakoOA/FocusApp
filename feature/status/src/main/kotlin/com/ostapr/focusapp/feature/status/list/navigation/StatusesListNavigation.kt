package com.ostapr.focusapp.feature.status.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ostapr.focusapp.feature.status.list.StatusesListRoute
import com.ostapr.focusapp.feature.status.model.UiStatusDetails

const val statusesNavigationRoute = "statuses"

fun NavController.navigateToStatuses() {
    this.navigate(statusesNavigationRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.statusesScreen(
    onStatusClick: (UiStatusDetails) -> Unit
) {
    composable(
        route = statusesNavigationRoute,
    ) {
        StatusesListRoute(onStatusClick)
    }
}
