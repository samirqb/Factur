package com.samirqb.factur.ui.states

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

data class FacturaDataState(
    var factura_fecha_expedicion: MutableState<String?> = mutableStateOf("")
    ,var factura_hora_expedicion: MutableState<String?> = mutableStateOf("")
    ,var factura_numero: MutableLiveData<Int?> = MutableLiveData(0)
    ,var imagen_corporativa: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)
)