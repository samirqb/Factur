package com.samirquiceno.micuentadecobro.models

import android.net.Uri

class CuentaDeCobroEntity(
    val numero_consecutivo: String,
    val fecha_hora_generacion_reporte: String,
    val tipo_reporte: String,
    val total_suma_servicios: Int,
    val imagen_corporativa_uri: Uri,
    val mDatosPrestadorServicioEntity: ProveedorServiciosEntity,
    val mDatosReceptorServicioEntity: ClienteEntity,
    val mServicioEntity: List<ServicioEntity?>,
)