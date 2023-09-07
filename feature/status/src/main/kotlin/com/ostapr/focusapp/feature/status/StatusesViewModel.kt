package com.ostapr.focusapp.feature.status

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ostapr.focusapp.core.data.repositories.StatusesRepository
import com.ostapr.focusapp.feature.status.model.UiStatusDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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
        statusesRepo.getStatuses().map(statusesMapper::convertCoresToUi)
            .stateIn(viewModelScope, WhileSubscribed(stopTimeoutMillis = 5_000), emptyList())

}