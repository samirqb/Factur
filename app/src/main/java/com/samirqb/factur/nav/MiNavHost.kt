package com.samirqb.factur.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samirqb.factur.ui.screens.AcercaDeScreen
import com.samirqb.factur.ui.screens.CargarImagenScreen
import com.samirqb.factur.ui.screens.ClienteFormScreen
import com.samirqb.factur.ui.screens.CotizacionScreen
import com.samirqb.factur.ui.screens.CuentaDeCobroScreen
import com.samirqb.factur.ui.screens.FacturaScreen
import com.samirqb.factur.ui.screens.MainScreen
import com.samirqb.factur.ui.screens.PoliticasWebViewScreen
import com.samirqb.factur.ui.screens.ProveedorServiciosFormScreen
import com.samirqb.factur.ui.screens.ServicioFormScreen
import com.samirqb.factur.ui.screens.dialogs.CompartirPDFScreenDialog
import com.samirqb.factur.ui.screens.dialogs.EditarContadorDocsEmitidosScreenDialog
import com.samirqb.factur.ui.screens.dialogs.VigenciaCotizacionScreenDialog
import com.samirqb.factur.viewmodels.ClienteViewModel
import com.samirqb.factur.viewmodels.CotizacionViewModel
import com.samirqb.factur.viewmodels.CuentaDeCobroViewModel
import com.samirqb.factur.viewmodels.FacturaViewModel
import com.samirqb.factur.viewmodels.ImagenCorporativaViewModel
import com.samirqb.factur.viewmodels.ProveedorServiciosViewModel
import com.samirqb.factur.viewmodels.ServicioViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MiNavHost(

    modifier : Modifier = Modifier.padding(7.dp),

    navController: NavHostController = rememberNavController(),

    mProveedorServiciosViewModel: ProveedorServiciosViewModel =
        viewModel(factory = ProveedorServiciosViewModel.Factory),

    mClienteViewModel: ClienteViewModel =
        viewModel(factory = ClienteViewModel.Factory),

    mServicioViewModel: ServicioViewModel =
        viewModel(factory = ServicioViewModel.Factory),

    mCuentaDeCobroViewModel: CuentaDeCobroViewModel =
        viewModel(factory = CuentaDeCobroViewModel.Factory),

    mCotizacionViewModel : CotizacionViewModel =
        viewModel(factory = CotizacionViewModel.Factory),

    mFacturaViewModel : FacturaViewModel =
        viewModel(factory = FacturaViewModel.Factory),

    mImagenCorporativaViewModel: ImagenCorporativaViewModel =
        viewModel(factory = ImagenCorporativaViewModel.Factory)

){

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreenRoute.route_screen,
    ){


        composable(
            route = Screen.MainScreenRoute.route_screen
        ) {
            MainScreen(
                navController = navController
                , proveedorServiciosViewModel = mProveedorServiciosViewModel
                , modifier = modifier
            )
        }


        dialog(
            route = Screen.CompartirPDFScreenRoute.route_screen
        ){
            CompartirPDFScreenDialog(
                modifier = modifier,
                navController = navController,
                cuentaDeCobroViewModel = mCuentaDeCobroViewModel
            )
        }


        composable(
            route = Screen.ProveedorServiciosFormScreenRoute.route_screen
        ) {

            ProveedorServiciosFormScreen(
                navController = navController
                , proveedorServiciosViewModel = mProveedorServiciosViewModel
                , modifier = modifier
            )
        }

        composable(
            route = Screen.CargarImagenScreenRoute.route_screen
        ) {

            CargarImagenScreen(
                navController = navController
                , imagenCorporativaViewModel = mImagenCorporativaViewModel
                , modifier = modifier
            )
        }


        composable(
            route = Screen.ClienteFormScreenRoute.route_screen
        ) {

            ClienteFormScreen(
                navController = navController
                , clienteViewModel = mClienteViewModel
                , modifier = modifier
            )
        }


        dialog(
            route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen+"/{contador_id}",
            arguments = listOf(navArgument("contador_id"){ type = NavType.StringType })
        ){

            var contador_id =  it.arguments?.getString("contador_id")

            EditarContadorDocsEmitidosScreenDialog(
                modifier = modifier,
                navController = navController,
                cotizacionViewModel = mCotizacionViewModel,
                cuentaDeCobroViewModel = mCuentaDeCobroViewModel,
                facturaViewModel = mFacturaViewModel,
                contador_id = contador_id
            )
        }


        dialog(
            route = Screen.VigenciaCotizacionScreenRoute.route_screen,
        ){

            VigenciaCotizacionScreenDialog(
                modifier = modifier,
                navController = navController,
                cotizacionViewModel = mCotizacionViewModel,
            )
        }


        composable(
            route = Screen.ServicioFormScreenRoute.route_screen+"/{servicio_id}"
            ,arguments = listOf(navArgument("servicio_id"){ type = NavType.StringType })
        ) {

            var servicio_id =  it.arguments?.getString("servicio_id")

            ServicioFormScreen(
                navController = navController
                , servicioViewModel = mServicioViewModel
                , modifier = modifier
                , servicio_id = servicio_id
            )
        }


        composable(
            route = Screen.CuentaDeCobroScreenRoute.route_screen
        ) {

            CuentaDeCobroScreen(
                navController = navController
                //, onNavigateToDialog = { navController.navigate(route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen) }
                , onNavigateToDialog = { navController.navigate(route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen+"/contador_cuenta_cobro") }
                , onNavigateToDialogComparitPDF = { navController.navigate(route = Screen.CompartirPDFScreenRoute.route_screen) }
                , imagenCorporativaViewModel = mImagenCorporativaViewModel
                , proveedorServiciosViewModel = mProveedorServiciosViewModel
                , clienteViewModel = mClienteViewModel
                , servicioViewModel = mServicioViewModel
                , cuentaDeCobroViewModel = mCuentaDeCobroViewModel
                , modifier = modifier
            )
        }

        composable(
            route = Screen.CotizacionScreenRoute.route_screen
        ) {

            CotizacionScreen(
                navController = navController
                , onNavigateToDialog = { navController.navigate(route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen+"/contador_cotizacion") }
                , onNavigateToDialog2 = { navController.navigate(route = Screen.VigenciaCotizacionScreenRoute.route_screen) }
                , onNavigateToDialogComparitPDF = { navController.navigate(route = Screen.CompartirPDFScreenRoute.route_screen) }
                , imagenCorporativaViewModel = mImagenCorporativaViewModel
                , proveedorServiciosViewModel = mProveedorServiciosViewModel
                , clienteViewModel = mClienteViewModel
                , servicioViewModel = mServicioViewModel
                , cotizacionViewModel = mCotizacionViewModel
                , modifier = modifier
            )
        }

        composable(
            route = Screen.FacturaScreenRoute.route_screen
        ) {

            FacturaScreen(
                navController = navController
                , onNavigateToDialog = { navController.navigate(route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen+"/contador_factura") }
                , onNavigateToDialogComparitPDF = { navController.navigate(route = Screen.CompartirPDFScreenRoute.route_screen) }
                , imagenCorporativaViewModel = mImagenCorporativaViewModel
                , proveedorServiciosViewModel = mProveedorServiciosViewModel
                , clienteViewModel = mClienteViewModel
                , servicioViewModel = mServicioViewModel
                , facturaViewModel = mFacturaViewModel
                , modifier = modifier
            )
        }

        composable(
            route = Screen.PoliticasWebViewScreenRoute.route_screen
        ) {

            PoliticasWebViewScreen(
                navController = navController
                , modifier = modifier
            )
        }


        composable(
            route = Screen.AcercaDeScreenRoute.route_screen
        ) {

            AcercaDeScreen(
                navController = navController
                , modifier = modifier
            )
        }
    }
}

