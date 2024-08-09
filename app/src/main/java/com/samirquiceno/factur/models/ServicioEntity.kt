package com.samirquiceno.factur.models


import com.samirquiceno.factur.models.Interfaces.IBaseEntity

data class ServicioEntity (

    /* datos informados en formulario por el usuario */
    //val id_servicio : Int,
    //val fecha_hora_creacion_servicio : String,
    val cantidad : Int,
    val descripcion_servicio : String,
    val valor_unitario_del_servicio : Int,
    val valor_total_del_servicio : Int,

) : IBaseEntity