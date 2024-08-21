package com.samirqb.factur.ui.states

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ImagenCorporativaDataState(
    var imagen_corporativa: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)
    //var imagen_corporativa: MutableLiveData<Uri?> = MutableLiveData(Uri.EMPTY)
)