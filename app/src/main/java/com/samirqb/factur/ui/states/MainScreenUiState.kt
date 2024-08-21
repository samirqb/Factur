package com.samirqb.factur.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MainScreenUiState
    (
            val mi_estado : MutableState<Boolean> = mutableStateOf(true)
    //val lista_servicios_agregados: MutableList<ProveedorServiciosEntity?> = mutableStateListOf()
    //val lista_servicios_agregados: String
)
{}