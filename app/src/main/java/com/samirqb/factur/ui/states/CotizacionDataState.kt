package com.samirqb.factur.ui.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

data class CotizacionDataState(
    var cotizacion_fecha_expedicion: MutableState<String?> = mutableStateOf("")
    , var cotizacion_hora_expedicion: MutableState<String?> = mutableStateOf("")
    , var cotizacion_numero: MutableLiveData<Int?> = MutableLiveData(0)
    //, var imagen_corporativa: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)

    /** datos temporales para cada cotizacion emitida */
    , var cotizacion_vigencia: MutableState<Int?> = mutableStateOf(0)
    //, var cotizacion_observaciones: MutableList<String> = mutableStateListOf()
)