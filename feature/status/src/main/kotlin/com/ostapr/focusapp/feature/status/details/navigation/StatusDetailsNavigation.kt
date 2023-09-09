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
import com.ostapr.focusapp.feature.status.details.StatusDetailsScreenRoute
import com.ostapr.focusapp.feature.status.details.StatusUiState
import com.ostapr.focusapp.feature.status.model.UiStatusDetails


const val statusDetailsBaseNavigationRoute = "status/"

@VisibleForTesting
internal const val statusIdArg = "statusId"

internal class StatusArgs(val statusId: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[statusIdArg]) as Long)
}

fun NavController.navigateToStatus(statusId: Long) {
    this.navigate("$statusDetailsBaseNavigationRoute$statusId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.statusDetailsScreen(

) {
    composable(
        route = "$statusDetailsBaseNavigationRoute{$statusIdArg}",
        arguments = listOf(
            navArgument(statusIdArg) { type = NavType.LongType },
        ),
    ) {
        StatusDetailsScreenRoute()
    }
}
