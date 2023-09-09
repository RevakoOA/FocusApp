package com.ostapr.focusapp.feature.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ostapr.focusapp.core.data.repositories.StatusesRepository
import com.ostapr.focusapp.feature.status.model.UiStatusDetails
import com.ostapr.focusapp.feature.status.model.UiStatusDetailsMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatusesViewModel @Inject constructor(
    private val statusesRepo: StatusesRepository,
    private val statusesMapper: UiStatusDetailsMapper,
) : ViewModel() {

    val statusesFlow: StateFlow<List<UiStatusDetails>> =
        statusesRepo.getStatuses().map(statusesMapper::convertCoreToUi)
            .stateIn(viewModelScope, WhileSubscribed(stopTimeoutMillis = 5_000), emptyList())

}