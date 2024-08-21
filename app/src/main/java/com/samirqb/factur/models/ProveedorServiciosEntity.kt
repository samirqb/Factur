package com.samirqb.factur.models

import com.samirqb.factur.models.Interfaces.IBaseEntity

class ProveedorServiciosEntity (
    val identificacion: String,
    val nombre: String,
    val telefono: String,
    val email: String,
    val ubicacion: String,

) : IBaseEntity