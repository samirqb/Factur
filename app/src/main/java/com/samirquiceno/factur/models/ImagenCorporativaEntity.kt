package com.samirquiceno.factur.models

import android.content.Context
import android.net.Uri
import com.samirquiceno.factur.models.Interfaces.IBaseEntity

data class ImagenCorporativaEntity(
    val context: Context,
    val nombre: String = "",
    val uri: Uri = Uri.EMPTY,
): IBaseEntity