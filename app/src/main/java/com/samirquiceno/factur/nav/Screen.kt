package com.samirquiceno.factur.nav

sealed class Screen(val route_screen : String) {
    object  MainScreenRoute: Screen(route_screen = "main_screen_route")
    object  ProveedorServiciosFormScreenRoute: Screen(route_screen = "proveedor_servicios_form_screen_route")
    object  ClienteFormScreenRoute: Screen(route_screen = "cliente_form_screen_route")
    object  ServicioFormScreenRoute: Screen(route_screen = "servicio_form_screen_route")
    object  EditarContadorDocsEmitidosFormScreenRoute: Screen(route_screen = "editar_contador_docs_emitidos_form_screen_route")
    object  CompartirPDFScreenRoute: Screen(route_screen = "compartir_pdf_dialog_screen_route")
    object  CuentaDeCobroScreenRoute: Screen(route_screen = "cuenta_de_cobro_screen_route")
    object  CotizacionScreenRoute: Screen(route_screen = "cotizacion_screen_route")
    object  FacturaScreenRoute: Screen(route_screen = "factura_screen_route")
    object  CargarImagenScreenRoute: Screen(route_screen = "cargar_imagen_screen_route")
    object  PoliticasWebViewScreenRoute: Screen(route_screen = "politicas_webview_screen_route")
    object  AcercaDeScreenRoute: Screen(route_screen = "acerca_de_screen_route")
}