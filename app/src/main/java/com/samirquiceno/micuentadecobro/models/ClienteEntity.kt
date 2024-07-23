package com.samirquiceno.micuentadecobro.models

import com.samirquiceno.micuentadecobro.models.Interfaces.IBaseEntity

data class ClienteEntity(
    val identificacion: String,
    val nombre: String,
    val telefono: String,
    val email: String,
    val ubicacion: String,

    ): IBaseEntity