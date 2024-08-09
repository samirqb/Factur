package com.samirquiceno.factur.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ClienteDataState(
    var cliente_identificacion: MutableState<String?> = mutableStateOf("")
    ,var cliente_nombre: MutableState<String?> = mutableStateOf("")
    ,var cliente_ubicacion: MutableState<String?> = mutableStateOf("")
    ,var cliente_telefono: MutableState<String?> = mutableStateOf("")
    ,var cliente_email: MutableState<String?> = mutableStateOf("")
)