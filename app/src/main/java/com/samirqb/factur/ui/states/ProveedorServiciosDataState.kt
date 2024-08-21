package com.samirqb.factur.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ProveedorServiciosDataState(
    var proovedor_servicio_identificacion:MutableState<String?> = mutableStateOf("")
    ,var proovedor_servicio_nombre:MutableState<String?> = mutableStateOf("")
    ,var proovedor_servicio_ubicacion:MutableState<String?> = mutableStateOf("")
    ,var proovedor_servicio_telefono:MutableState<String?> = mutableStateOf("")
    ,var proovedor_servicio_email:MutableState<String?> = mutableStateOf("")
) {
}