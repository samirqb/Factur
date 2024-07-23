package com.samirquiceno.micuentadecobro.models

import android.net.Uri
import com.samirquiceno.micuentadecobro.models.Interfaces.IBaseEntity

data class ProveedorServicioImagenEntity(
    var id : String,
    var uri: Uri,
    ):IBaseEntity