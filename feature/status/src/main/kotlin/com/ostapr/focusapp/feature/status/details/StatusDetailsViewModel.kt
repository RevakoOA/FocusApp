package com.ostapr.focusapp.feature.status.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ostapr.focusapp.core.data.repositories.StatusesRepository
import com.ostapr.focusapp.feature.status.details.navigation.StatusArgs
import com.ostapr.focusapp.feature.status.model.UiStatusDetails
import com.ostapr.focusapp.feature.status.model.UiStatusDetailsMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class StatusDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val statusesRepository: StatusesRepository,
    private val statusMapper: UiStatusDetailsMapper
) : ViewModel() {

    private val statusArgs: StatusArgs = StatusArgs(savedStateHandle)

    val statusDetailsState: StateFlow<StatusUiState> =
        statusesRepository.getStatus(statusArgs.statusId)
            .map { status ->
                StatusUiState.Success(statusMapper.convertCoreToUi(status))
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                StatusUiState.Loading
            )


}

internal sealed interface StatusUiState {
    data class Success(val statusDetails: UiStatusDetails) : StatusUiState
    object Loading : StatusUiState
}