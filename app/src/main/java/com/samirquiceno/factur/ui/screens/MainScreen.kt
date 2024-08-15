package com.samirquiceno.factur.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose.backgroundLight
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerDark
import com.example.compose.primaryContainerLight
import com.samirquiceno.factur.R
import com.samirquiceno.factur.nav.Screen
import com.samirquiceno.factur.ui.components.CustomButton
import com.samirquiceno.factur.ui.components.CustomCardComponentV3
import com.samirquiceno.factur.ui.components.CustomIconButton2
import com.samirquiceno.factur.ui.components.TextH2
import com.samirquiceno.factur.ui.components.TextH3
import com.samirquiceno.factur.viewmodels.ClienteViewModel
import com.samirquiceno.factur.viewmodels.CuentaDeCobroViewModel
import com.samirquiceno.factur.viewmodels.ImagenCorporativaViewModel
import com.samirquiceno.factur.viewmodels.ProveedorServiciosViewModel
import com.samirquiceno.factur.viewmodels.ServicioViewModel


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "Range")
@Composable
fun MainScreen(
    navController: NavHostController
    , onNavigateToDialog: () -> Unit
    , onNavigateToDialogComparitPDF: () -> Unit
    //, onDismissRequest : ()-> Unit
    , modifier: Modifier
    , imagenCorporativaViewModel: ImagenCorporativaViewModel
    , proveedorServiciosViewModel : ProveedorServiciosViewModel
    , clienteViewModel: ClienteViewModel
    , servicioViewModel: ServicioViewModel
    , cuentaDeCobroViewModel: CuentaDeCobroViewModel
){

    /*** Create a boolean variable
    to store the display menu state */
    var display_menu by rememberSaveable { mutableStateOf(false) }

    var darkTheme: Boolean = isSystemInDarkTheme()
    var context = LocalContext.current


    var mProveedorServicioEntity = proveedorServiciosViewModel.read("").observeAsState()
    proveedorServiciosViewModel.updateProveedorServiciosDataState(mProveedorServicioEntity)



    var proveedor_serv_identificacion = mProveedorServicioEntity.value?.identificacion
    var proveedor_serv_nombre         = mProveedorServicioEntity.value?.nombre
    var proveedor_serv_telefono       = mProveedorServicioEntity.value?.telefono
    var proveedor_serv_email          = mProveedorServicioEntity.value?.email
    var proveedor_serv_ubicacion      = mProveedorServicioEntity.value?.ubicacion

    var datos_proveedor_bool =
        if( proveedor_serv_identificacion.isNullOrEmpty()
            && proveedor_serv_nombre.isNullOrEmpty()
            && proveedor_serv_telefono.isNullOrEmpty()
            && proveedor_serv_email.isNullOrEmpty()
            && proveedor_serv_ubicacion.isNullOrEmpty() ) false
        else true

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(

                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = if (darkTheme) primaryContainerDark
                    else primaryContainerLight,

                    titleContentColor = if (darkTheme) onPrimaryContainerDark
                    else onPrimaryContainerLight,
                ),

                title = {
                    /** titulo - Inicio */
                    TextH2(
                        text =  stringResource(id = R.string.inicio)
                    )
                },

                actions = {

                    /*** boton del menu en la barra de acciones ( Actionbar ) */

                    Surface(
                        color = primaryContainerDark,
                        modifier = Modifier
                            .fillMaxHeight(.1f)
                            .fillMaxWidth(.11f),
                        content = {
                            CustomIconButton2(
                                icono = Icon(
                                    tint = onPrimaryLight,
                                    modifier = Modifier.size(30.dp),
                                    painter = painterResource(id = R.drawable.baseline_menu_24), contentDescription = null),
                                onClick = {
                                    display_menu = !display_menu
                                    //Toast.makeText(context,"Boton de Menu OK!",Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )

                    DropdownMenu(
                        expanded = display_menu,
                        onDismissRequest = { display_menu = false },
                        content = {

                            DropdownMenuItem(
                                text = {

                                    Row(content = {
                                        Icon(
                                            //tint = onPrimaryLight,
                                            modifier = Modifier.size(30.dp),
                                            painter = painterResource(id = R.drawable.outline_add_business_24), contentDescription = null)

                                        Spacer(modifier = Modifier.padding(5.dp))

                                        TextH3(text = "Datos del comerciante")
                                    })
                                },
                                onClick = {
                                    //Toast.makeText(context,"Datos del comerciante",Toast.LENGTH_SHORT).show()

                                    display_menu = false
                                    navController.navigate( route = Screen.ProveedorServiciosFormScreenRoute.route_screen )
                                }
                            )

                            Divider()

                            DropdownMenuItem(
                                text = {

                                    Row {
                                        Icon(
                                            //tint = onPrimaryLight,
                                            modifier = Modifier.size(30.dp),
                                            painter = painterResource(id = R.drawable.baseline_image_search_24), contentDescription = null)

                                        Spacer(modifier = Modifier.padding(5.dp))

                                        TextH3(text = "Imagen corporativa")
                                    }
                                },
                                onClick = {
                                    //Toast.makeText(context,"Imagen corporativa",Toast.LENGTH_SHORT).show()
                                    display_menu = false
                                    navController.navigate( route = Screen.CargarImagenScreenRoute.route_screen )
                                }
                            )

                            Divider()

                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Icon(
                                            //tint = onPrimaryLight,
                                            modifier = Modifier.size(30.dp),
                                            painter = painterResource(id = R.drawable.baseline_developer_mode_24), contentDescription = null)

                                        Spacer(modifier = Modifier.padding(5.dp))

                                        TextH3(text = "Acerca de")
                                    }

                                },
                                onClick = { Toast.makeText(context,"Acerca de",Toast.LENGTH_SHORT).show()}
                            )
                        }
                    )
                }
            )
        },

    ) { innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(77.dp,Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            /** Se valida que la informacion del Proveedor de servicios este compleata antes de poder
             * realizar una emision de algun soporte (factura, cuenta de cobro, cotizacion),
             *
             * IF - si los datos del Proveedor de Servicios estan completos, se mostraran los botones
             * para acceder a las funcionalidades de (factura, cuenta de cobro, cotizacion)
             *
             * ELSE - si los datos del Proveedor de Servicios estan vacios, se muestra mensaje informativo
             * para solicitar la informacion requerida */

            if(datos_proveedor_bool){
                item {

                    Surface(
                        modifier = Modifier.size(100.dp),
                        color = colorResource(id = R.color.purpura_transparente),
                        shape = CircleShape,
                        content = {
                            Icon(
                                modifier = modifier
                                    .padding(7.dp)
                                    .size(100.dp)

                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onTap = { navController.navigate(route = Screen.CotizacionScreenRoute.route_screen) },
                                            /*
                                            onDoubleTap = {
                                                runBlocking{
                                                    clienteViewModel.limpiarDatosCliente()
                                                }
                                            }
                                            */
                                        )
                                    }

                                ,painter = painterResource(id = R.drawable.outline_request_quote_24),
                                contentDescription = ""
                            )
                        }
                    )

                    TextH3(text = stringResource(id = R.string.cotizacion))

                }

                item {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        color = colorResource(id = R.color.purpura_transparente),
                        shape = CircleShape,
                        content = {
                            Icon(modifier = modifier
                                .padding(7.dp)
                                .size(100.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = { navController.navigate(route = Screen.CuentaDeCobroScreenRoute.route_screen) },
                                        /*
                                        onDoubleTap = {
                                            runBlocking{
                                                clienteViewModel.limpiarDatosCliente()
                                            }
                                        }
                                        */
                                    )
                                },
                                painter = painterResource(id = R.drawable.outline_insert_drive_file_24), contentDescription = "" )
                        }
                    )
                    //Icon(modifier = modifier.size(100.dp), painter = painterResource(id = R.drawable.outline_insert_drive_file_24), contentDescription = "" )
                    TextH3(text = stringResource(id = R.string.cuenta_cobro))
                }

                item {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        color = colorResource(id = R.color.purpura_transparente),
                        shape = CircleShape,
                        content = {
                            Icon(modifier = modifier
                                .padding(11.dp)
                                .size(100.dp), painter = painterResource(id = R.drawable.outline_receipt_24), contentDescription = "" )
                        }
                    )
                    //Icon(modifier = modifier.size(100.dp), painter = painterResource(id = R.drawable.outline_receipt_24), contentDescription = "" )
                    TextH3(text = stringResource(id = R.string.factura))
                }
            } else {
                item{

                    CustomCardComponentV3(
                        titulo = {
                            TextH2 (
                                color = backgroundLight,
                                modifier = modifier,
                                text = stringResource(id = R.string.bienvenido)
                            )
                        },

                        content = {

                            /*
                            TextH3(
                                color = backgroundLight,
                                modifier = modifier,
                                text = stringResource(id = R.string.informacion_no_disponible)
                            )
                            */

                            TextH3(
                                color = backgroundLight,
                                modifier = modifier,
                                text = stringResource(id = R.string.antes_de_iniciar)
                            )

                            /*
                            TextH3(
                                color = primaryContainerDark,
                                modifier = modifier,
                                text = stringResource(id = R.string.ingresa_desde_aqui)
                            )
                            */

                            CustomButton(
                                modifier = modifier,
                                text = R.string.btn_ingresa_aqui,
                                icono = R.drawable.outline_add_business_24,
                                onClick = { navController.navigate(route = Screen.ProveedorServiciosFormScreenRoute.route_screen) }
                            )
                        },

                        modifier = modifier,
                        onClick = {
                            //navController.navigate(route = Screen.ProveedorServiciosFormScreenRoute.route_screen)
                        }
                    )
                }

                /*
                item {
                    CustomButton(
                        modifier = modifier,
                        text = R.string.proovedor_servicio_titulo_form,
                        icono = R.drawable.outline_add_business_24,
                        onClick = { navController.navigate(route = Screen.ProveedorServiciosFormScreenRoute.route_screen) }
                    )
                }
                */
            }
        }
    }
}