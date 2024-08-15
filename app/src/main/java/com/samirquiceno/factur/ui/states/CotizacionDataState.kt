package com.samirquiceno.factur.ui.states

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

data class CotizacionDataState(
    var cotizacion_fecha_expedicion: MutableState<String?> = mutableStateOf("")
    ,var cotizacion_hora_expedicion: MutableState<String?> = mutableStateOf("")
    ,var cotizacion_numero: MutableLiveData<Int?> = MutableLiveData(0)
    ,var imagen_corporativa: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)
)