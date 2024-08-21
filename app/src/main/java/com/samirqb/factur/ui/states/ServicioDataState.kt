package com.samirqb.factur.ui.states

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import com.samirqb.factur.models.ServicioEntity

data class ServicioDataState(
    var listaServicioEntity : MutableList<ServicioEntity> = mutableStateListOf(),
    var sumaTotalListaServicioEntity : MutableIntState = mutableIntStateOf(0)
)