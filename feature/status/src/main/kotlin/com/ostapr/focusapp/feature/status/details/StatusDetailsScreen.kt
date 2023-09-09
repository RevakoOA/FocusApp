package com.ostapr.focusapp.feature.status.details

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import androidx.compose.foundation.Image
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getDrawable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.ostapr.focusapp.core.designsystem.theme.FocusAppTheme
import com.ostapr.focusapp.feature.status.R
import com.ostapr.focusapp.feature.status.model.UiInstalledAppItem
import com.ostapr.focusapp.feature.status.model.UiStatusDetails

@Composable
internal fun StatusDetailsScreenRoute(
    viewModel: StatusDetailsViewModel = hiltViewModel()
) {
    val statusState: StatusUiState by viewModel.statusDetailsState.collectAsState()
    StatusDetailsScreen(statusState)
}

@Composable
internal fun StatusDetailsScreen(
    statusState: StatusUiState,
    modifier: Modifier = Modifier
) {
    var filterText: String by rememberSaveable { mutableStateOf("") }

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

                when (statusState) {
                    is StatusUiState.Loading -> {
                        LinearProgressIndicator()
                    }

                    is StatusUiState.Success -> {
                        val statusDetails = statusState.statusDetails
                        val title =
                            "Apps List ${statusDetails.dateTime}"
                        Text(title, style = MaterialTheme.typography.headlineSmall)

                        Spacer(
                            Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                        )

                        TextField(
                            value = filterText,
                            onValueChange = { newText -> filterText = newText },
                            placeholder = { Text("Search for an app") },
                            leadingIcon = {
                                getDrawable(
                                    LocalContext.current,
                                    R.drawable.search_24
                                )
                            }
                        )

                        Spacer(
                            Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                        )

                        AppItemsLazyList(statusDetails.apps)
                    }
                }
            }
        }
    }
}

@Composable
internal fun AppItemsLazyList(
    appInfos: List<UiInstalledAppItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        items(items = appInfos, itemContent = { item ->
            AppItem(item)
        })
    }
}

@Composable
internal fun AppItem(appInfo: UiInstalledAppItem, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberDrawablePainter(appInfo.image),
            contentDescription = appInfo.name,
            modifier = Modifier.size(48.dp)
        )
        Spacer(
            Modifier
                .fillMaxHeight()
                .width(16.dp)
        )
        Text(appInfo.name, Modifier, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_MASK)
fun DetailsPreview() {
    val context = LocalContext.current
    val sampleUiStatusDetails = UiStatusDetails(
        id = 1,
        "7/09/23, 11:15",
        apps = listOf(
            UiInstalledAppItem("Facebook", context.getDrawable(R.drawable.touch_app_24)!!),
            UiInstalledAppItem("Play Market", context.getDrawable(R.drawable.touch_app_24)!!),
            UiInstalledAppItem("Some Game", context.getDrawable(R.drawable.touch_app_24)!!),
        ),
        isFocused = true
    )

    StatusDetailsScreen(StatusUiState.Success(sampleUiStatusDetails))
}