package com.samirqb.factur.ui.states

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

data class CuentaDeCobroDataState(
    var cuenta_de_cobro_fecha_expedicion: MutableState<String?> = mutableStateOf("")
    ,var cuenta_de_cobro_hora_expedicion: MutableState<String?> = mutableStateOf("")
    ,var cuenta_de_cobro_numero: MutableLiveData<Int?> = MutableLiveData(0)
    ,var imagen_corporativa: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)
)