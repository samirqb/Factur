package com.samirquiceno.micuentadecobro.nav

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
import com.samirquiceno.micuentadecobro.ui.screens.ClienteFormScreen
import com.samirquiceno.micuentadecobro.ui.screens.MainScreen
import com.samirquiceno.micuentadecobro.ui.screens.ProveedorServiciosFormScreen
import com.samirquiceno.micuentadecobro.ui.screens.ServicioFormScreen
import com.samirquiceno.micuentadecobro.ui.screens.dialogs.CompartirPDFScreenDialog
import com.samirquiceno.micuentadecobro.ui.screens.dialogs.EditarContadorDocsEmitidosScreenDialog
import com.samirquiceno.micuentadecobro.viewmodels.ClienteViewModel
import com.samirquiceno.micuentadecobro.viewmodels.CuentaDeCobroViewModel
import com.samirquiceno.micuentadecobro.viewmodels.ProveedorServiciosViewModel
import com.samirquiceno.micuentadecobro.viewmodels.ServicioViewModel

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
        viewModel(factory = CuentaDeCobroViewModel.Factory)


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
                , onNavigateToDialog = { navController.navigate(route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen) }
                , onNavigateToDialogComparitPDF = { navController.navigate(route = Screen.CompartirPDFScreenRoute.route_screen) }
                , proveedorServiciosViewModel = mProveedorServiciosViewModel
                , clienteViewModel = mClienteViewModel
                , servicioViewModel = mServicioViewModel
                , cuentaDeCobroViewModel = mCuentaDeCobroViewModel
                , modifier = modifier
            )
        }

        dialog(
            route = Screen.EditarContadorDocsEmitidosFormScreenRoute.route_screen
        ){
            EditarContadorDocsEmitidosScreenDialog( modifier = modifier, navController = navController,cuentaDeCobroViewModel = mCuentaDeCobroViewModel )
        }

        dialog(
            route = Screen.CompartirPDFScreenRoute.route_screen
        ){
            CompartirPDFScreenDialog( modifier = modifier, navController = navController,cuentaDeCobroViewModel = mCuentaDeCobroViewModel )
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
            route = Screen.ClienteFormScreenRoute.route_screen
        ) {

            ClienteFormScreen(
                navController = navController
                , clienteViewModel = mClienteViewModel
                , modifier = modifier
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

        // Add more destinations similarly.
    }
}
