package com.samirquiceno.factur.models

import com.samirquiceno.factur.models.Interfaces.IBaseEntity

data class ClienteEntity(
    val identificacion: String,
    val nombre: String,
    val telefono: String,
    val email: String,
    val ubicacion: String,

    ): IBaseEntity