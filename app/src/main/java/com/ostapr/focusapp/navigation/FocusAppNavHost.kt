package com.ostapr.focusapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ostapr.focusapp.feature.status.details.navigation.navigateToStatus
import com.ostapr.focusapp.feature.status.details.navigation.statusDetailsScreen
import com.ostapr.focusapp.feature.status.list.navigation.statusesNavigationRoute
import com.ostapr.focusapp.feature.status.list.navigation.statusesScreen

@Composable
fun FocusAppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = statusesNavigationRoute,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        statusesScreen(onStatusClick = { uiStatus -> navController.navigateToStatus(uiStatus)})
        statusDetailsScreen()
    }
}