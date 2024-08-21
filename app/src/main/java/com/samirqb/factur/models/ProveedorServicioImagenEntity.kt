package com.samirqb.factur.models

import android.net.Uri
import com.samirqb.factur.models.Interfaces.IBaseEntity

data class ProveedorServicioImagenEntity(
    var id : String,
    var uri: Uri,
    ):IBaseEntity