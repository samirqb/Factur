package com.samirqb.factur.models

import android.net.Uri
import com.samirqb.factur.models.Interfaces.IBaseEntity

data class FacturaEntity(
    val numero_consecutivo: String,
    val fecha_hora_generacion_reporte: String,
    val tipo_reporte: String,
    val total_suma_servicios: Int,
    val imagen_corporativa_uri: Uri,
    val mDatosPrestadorServicioEntity: ProveedorServiciosEntity,
    val mDatosReceptorServicioEntity: ClienteEntity,
    val mServicioEntity: List<ServicioEntity?>,
): IBaseEntity