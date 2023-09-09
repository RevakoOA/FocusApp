package com.ostapr.focusapp.feature.status.details.navigation

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ostapr.focusapp.feature.status.details.StatusDetailsScreen
import com.ostapr.focusapp.feature.status.model.UiStatusDetails


const val statusDetailsBaseNavigationRoute = "status/"

@VisibleForTesting
internal const val statusIdArg = "statusId"

internal class StatusArgs(val statusId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[statusIdArg]) as String)
}

fun NavController.navigateToStatus(statusDetails: UiStatusDetails) {
    this.navigate("$statusDetailsBaseNavigationRoute$statusDetails") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.statusDetailsScreen(

) {
    composable(
        route = "$statusDetailsBaseNavigationRoute{$statusIdArg}",
        arguments = listOf(
            navArgument(statusIdArg) { type = NavType.StringType },
        ),
    ) {
        StatusDetailsScreen(UiStatusDetails("date time", emptyList(), isFocused = true))
    }
}
