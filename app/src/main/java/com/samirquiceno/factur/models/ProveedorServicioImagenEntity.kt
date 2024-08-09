package com.samirquiceno.factur.models

import android.net.Uri
import com.samirquiceno.factur.models.Interfaces.IBaseEntity

data class ProveedorServicioImagenEntity(
    var id : String,
    var uri: Uri,
    ):IBaseEntity