package com.samirquiceno.factur.tools

import com.samirquiceno.factur.models.ServicioEntity

class OperacionesMetematicas {

    fun sumarTodosLosServicios(lista_servicio_entity: List<ServicioEntity?>):Int{

        var total_suma: Int = 0

        //for (mServicioEntidad in lista_subtotales) total_suma += mServicioEntidad?.subtotal_servicio
        lista_servicio_entity.forEach {
            total_suma += it!!.valor_total_del_servicio
        }

        return total_suma
    }
}