package com.samirquiceno.micuentadecobro.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CuentaDeCobroUiState(
    var openAlertDialog : MutableState<Boolean> = mutableStateOf(false)
)
