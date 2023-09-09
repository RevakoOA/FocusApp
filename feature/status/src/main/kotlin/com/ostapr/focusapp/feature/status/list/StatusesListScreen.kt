package com.ostapr.focusapp.feature.status.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ostapr.focusapp.core.designsystem.theme.FocusAppTheme
import com.ostapr.focusapp.feature.status.R
import com.ostapr.focusapp.feature.status.StatusesViewModel
import com.ostapr.focusapp.feature.status.model.UiInstalledAppItem
import com.ostapr.focusapp.feature.status.model.UiStatusDetails

@Composable
internal fun StatusesListRoute(
    onStatusClick: (UiStatusDetails) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StatusesViewModel = hiltViewModel(),
) {
    val statuses: List<UiStatusDetails> by viewModel.statusesFlow.collectAsStateWithLifecycle()

    StatusesListScreen(
        statuses,
        onStatusClick,
    )
}

@Composable
internal fun StatusesListScreen(
    statuses: List<UiStatusDetails>,
    onStatusClick: (UiStatusDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    FocusAppTheme {
        Surface(
            modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                val title = stringResource(R.string.statuses_title)
                Text(title, style = MaterialTheme.typography.headlineSmall)

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                StatusesLazyColumn(statuses, onStatusClick)
            }
        }
    }
}

@Composable
internal fun StatusesLazyColumn(
    statuses: List<UiStatusDetails>,
    onStatusClick: (UiStatusDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        items(items = statuses, itemContent = { status ->
            StatusItem(status, onStatusClick)
        })
    }
}

@Composable
internal fun StatusItem(
    statusDetails: UiStatusDetails,
    onStatusClick: (UiStatusDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { onStatusClick(statusDetails) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(statusDetails.dateTime)
        Spacer(
            Modifier
                .fillMaxHeight()
                .width(16.dp)
        )
        Text("${statusDetails.appsCount}")

        Canvas(modifier = Modifier.size(24.dp)) {
            val color = if (statusDetails.isFocused) Color.Green else Color.Red
            drawCircle(color)
        }
    }
}

@Preview()
@Composable
fun StatusesListScreenPreview() {
    val statuses = listOf(
        UiStatusDetails(
            id = 1,
            "1/1/23 8:00",
            apps = listOf(
                UiInstalledAppItem(
                    "App 1",
                    LocalContext.current.getDrawable(R.drawable.touch_app_24)!!
                ),
                UiInstalledAppItem(
                    "App 1",
                    LocalContext.current.getDrawable(R.drawable.touch_app_24)!!
                ),
            ),
            isFocused = true
        )
    )
    StatusesListScreen(statuses, {})
}