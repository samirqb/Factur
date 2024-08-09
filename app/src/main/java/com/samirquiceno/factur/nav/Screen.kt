package com.samirquiceno.factur.nav

sealed class Screen(val route_screen : String) {
    object  MainScreenRoute: Screen(route_screen = "main_screen_route")
    object  ProveedorServiciosFormScreenRoute: Screen(route_screen = "proveedor_servicios_form_screen_route")
    object  ClienteFormScreenRoute: Screen(route_screen = "cliente_form_screen_route")
    object  ServicioFormScreenRoute: Screen(route_screen = "servicio_form_screen_route")
    object  EditarContadorDocsEmitidosFormScreenRoute: Screen(route_screen = "editar_contador_docs_emitidos_form_screen_route")
    object  CompartirPDFScreenRoute: Screen(route_screen = "compartir_pdf_dialog_screen_route")
    object  CuentaDeCobroScreenRoute: Screen(route_screen = "cuenta_de_cobro_screen_route")
    object  CargarImagenScreenRoute: Screen(route_screen = "cargar_imagen_screen_route")
}