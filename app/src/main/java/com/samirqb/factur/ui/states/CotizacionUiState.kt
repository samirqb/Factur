package com.samirqb.factur.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CotizacionUiState(
    var openAlertDialog : MutableState<Boolean> = mutableStateOf(false)
)
