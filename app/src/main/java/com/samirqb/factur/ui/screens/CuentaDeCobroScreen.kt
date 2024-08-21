package com.samirqb.factur.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.core.IOException
import androidx.navigation.NavHostController
import com.example.compose.backgroundLight
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerDark
import com.example.compose.primaryContainerLight
import com.samirqb.factur.R
import com.samirqb.factur.models.ClienteEntity
import com.samirqb.factur.models.CuentaDeCobroEntity
import com.samirqb.factur.models.ProveedorServiciosEntity
import com.samirqb.factur.models.ServicioEntity
import com.samirqb.factur.nav.Screen
import com.samirqb.factur.ui.components.CustomButton
import com.samirqb.factur.ui.components.CustomCardComponent
import com.samirqb.factur.ui.components.CustomCardComponentV2
import com.samirqb.factur.ui.components.CustomIconButton2
import com.samirqb.factur.ui.components.CustomOutlineButton
import com.samirqb.factur.ui.components.TextH2
import com.samirqb.factur.ui.components.TextH3
import com.samirqb.factur.viewmodels.ClienteViewModel
import com.samirqb.factur.viewmodels.CuentaDeCobroViewModel
import com.samirqb.factur.viewmodels.ImagenCorporativaViewModel
import com.samirqb.factur.viewmodels.ProveedorServiciosViewModel
import com.samirqb.factur.viewmodels.ServicioViewModel
import kotlinx.coroutines.runBlocking


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "Range")
@Composable
fun CuentaDeCobroScreen(
    navController: NavHostController
    , onNavigateToDialog: () -> Unit
    , onNavigateToDialogComparitPDF: () -> Unit
    //, onDismissRequest : ()-> Unit
    , modifier: Modifier
    , imagenCorporativaViewModel : ImagenCorporativaViewModel
    , proveedorServiciosViewModel : ProveedorServiciosViewModel
    , clienteViewModel: ClienteViewModel
    , servicioViewModel: ServicioViewModel
    , cuentaDeCobroViewModel: CuentaDeCobroViewModel
){


    val context = LocalContext.current


    var mNumFormat = NumberFormat.getCurrencyInstance()


    var fecha_expedicion_cuenta_de_cobro = cuentaDeCobroViewModel
        .cuentaDeCobroDataState
        .value
        .cuenta_de_cobro_fecha_expedicion
        .value.toString()


    var hora_expedicion_cuenta_de_cobro = cuentaDeCobroViewModel
        .cuentaDeCobroDataState
        .value
        .cuenta_de_cobro_hora_expedicion
        .value.toString()


    var mCuentaDeCobroContadorEntity = cuentaDeCobroViewModel.read("").observeAsState()
    cuentaDeCobroViewModel.actualizarContadorDocsEmitidos(mCuentaDeCobroContadorEntity)


    var mProveedorServicioEntity = proveedorServiciosViewModel.read("").observeAsState()
    proveedorServiciosViewModel.updateProveedorServiciosDataState(mProveedorServicioEntity)


    var mClienteEntity = clienteViewModel.read("").observeAsState()
    clienteViewModel.updateClienteDataState(mClienteEntity)


    var contador_documentos_emitidos = mCuentaDeCobroContadorEntity.value?.contador


    var proveedor_serv_identificacion = mProveedorServicioEntity.value?.identificacion
    var proveedor_serv_nombre         = mProveedorServicioEntity.value?.nombre
    var proveedor_serv_telefono       = mProveedorServicioEntity.value?.telefono
    var proveedor_serv_email          = mProveedorServicioEntity.value?.email
    var proveedor_serv_ubicacion      = mProveedorServicioEntity.value?.ubicacion


    var cliente_nombre         = mClienteEntity.value?.nombre
    var cliente_identificacion = mClienteEntity.value?.identificacion
    var cliente_telefono       = mClienteEntity.value?.telefono
    var cliente_email          = mClienteEntity.value?.email
    var cliente_ubicacion      = mClienteEntity.value?.ubicacion


    var darkTheme: Boolean = isSystemInDarkTheme()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
            //TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = if (darkTheme) primaryContainerDark
                    else primaryContainerLight,

                    titleContentColor = if (darkTheme) onPrimaryContainerDark
                    else onPrimaryContainerLight,
                ),
                title = {

                    /** titulo - Cuenta de Cobro */
                    TextH2(
                        modifier = modifier,
                        text =  stringResource(id = R.string.cuenta_cobro)
                    )
                },

                navigationIcon = {
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
                                    painter = painterResource(id = R.drawable.baseline_arrow_back_24 ), contentDescription = null),
                                onClick = {
                                    //navController.popBackStack()
                                    navController.navigateUp()
                                    //Toast.makeText(context,"Boton de Menu OK!",Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                }

            )
        },) { innerPadding ->
        /** I N I C I O   S C A F O L D */
        LazyColumn(modifier = modifier.padding(innerPadding)) {

            /** HEADER  --------------------------------------------------------------------------*/

            /** HEADER.SECCION: titulo de la aplicacion */

            item {

                /** contador de documentos emitidos  */
                Row {

                    if (contador_documentos_emitidos == null){
                        TextH3(
                            modifier = modifier
                            , text = stringResource( id = R.string.ingrese_num_consecutivo)
                        )
                    } else {
                        TextH3(
                            modifier = modifier
                            , text = stringResource( id = R.string.num_onsecutivo) + ": ${ contador_documentos_emitidos }"
                        )
                    }


                    Icon(

                        modifier = modifier
                            .scale(1f)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        onNavigateToDialog()
                                    }
                                )
                            },

                        imageVector = ImageVector.vectorResource(id = R.drawable.outline_edit_24),

                        contentDescription = ""
                    )
                }

                /*
                Column {

                    /** contador de documentos emitidos  */
                    Row {

                        if (contador_documentos_emitidos == null){
                            TextH3(
                                modifier = modifier
                                , text = stringResource( id = R.string.ingrese_num_consecutivo)
                            )
                        } else {
                            TextH3(
                                modifier = modifier
                                , text = stringResource( id = R.string.num_onsecutivo) + ": ${ contador_documentos_emitidos }"
                            )
                        }


                        Icon(

                            modifier = modifier
                                .scale(1f)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            onNavigateToDialog()
                                        }
                                    )
                                },

                            imageVector = ImageVector.vectorResource(id = R.drawable.outline_edit_24),

                            contentDescription = ""
                        )
                    }
                }
                */

                Spacer(modifier = modifier)
                Spacer(modifier = modifier)
            }


            item {

                TextH3(modifier = modifier, text = stringResource(id = R.string.fecha_y_hora_expedicion))

                Row(modifier = modifier) {


                    Row(modifier = Modifier.weight(1f)) {

                        Icon(
                            modifier = modifier.scale(1.3f),
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = ""
                        )

                        TextH3(textAlign = TextAlign.End, modifier = modifier, text = fecha_expedicion_cuenta_de_cobro )
                    }

                    Row(modifier = Modifier.weight(1f)) {
                        Icon(
                            modifier = modifier.scale(1.3f),
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_watch_later_24),
                            contentDescription = ""
                        )

                        TextH3(textAlign = TextAlign.End, modifier = modifier, text = hora_expedicion_cuenta_de_cobro)
                    }
                }

                Spacer(modifier = modifier)
            }


            item {
                if(servicioViewModel.servicioDataStore.value.listaServicioEntity.isNotEmpty()){

                    TextH3( textAlign = TextAlign.End, modifier = modifier, text = "Total a pagar:" )

                    Row(modifier = modifier.fillMaxWidth()) {
                        Icon(
                            modifier = modifier.scale(1.3f),
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_attach_money_24),
                            contentDescription = ""
                        )

                        TextH3( textAlign = TextAlign.End, modifier = modifier, text = "${mNumFormat.format(servicioViewModel.sumaTotalServicios())}" )
                    }

                    //TextH3( textAlign = TextAlign.End, modifier = modifier, text = "Total a pagar:" )
                    //TextH3( textAlign = TextAlign.End, modifier = modifier, text = "${servicioViewModel.sumaTotalServicios()}" )

                }

                Spacer(modifier = modifier)
                Spacer(modifier = modifier)
            }


            /** B O D Y ------------------------------------------------------------------------------*/

            /** BODY.SECCION: imagen corporativa del Proveedor de Servicios */
            /*
            item {
                CargarImagenIntent(
                    navController = navController,
                    cuentaDeCobroViewModel = cuentaDeCobroViewModel,
                    modifier = modifier)

                Spacer(modifier = modifier)
            }
            */

            /*
            /** BODY.SECCION: informacion del Proveedor de Servicios */
            item {

                if ((proveedor_serv_identificacion == null) or (proveedor_serv_identificacion =="")
                    and (proveedor_serv_nombre == null) or (proveedor_serv_nombre =="")
                    and (proveedor_serv_telefono == null) or (proveedor_serv_telefono =="")
                    and (proveedor_serv_email == null) or (proveedor_serv_email =="")
                    and (proveedor_serv_ubicacion == null) or (proveedor_serv_ubicacion =="")
                ){


                    CustomCardComponent(
                        titulo = {
                            TextH2 (
                                modifier = modifier,
                                text = stringResource(id = R.string.datos_proveedor_servicio)
                            )
                        },

                        content = {
                            TextH3(
                                modifier = modifier,
                                text = stringResource(id = R.string.informacion_no_disponible)
                            )
                        },

                        modifier = modifier,

                        onClick = {
                            navController.navigate( route = Screen.ProveedorServiciosFormScreenRoute.route_screen )
                        }
                    )

                } else {

                    CustomCardComponent(

                        titulo = {
                            TextH2(
                                modifier = modifier,
                                text = stringResource(id = R.string.datos_proveedor_servicio)
                            )
                        },

                        content = {

                            Column(modifier = Modifier.fillMaxWidth()) {

                                Row(modifier = modifier) {
                                    Icon(
                                        modifier = modifier.weight(01f)
                                        , painter = painterResource(id = R.drawable.outline_tag_24)
                                        , contentDescription = stringResource(id = R.string.proovedor_servicio_identificacion)
                                        //, modifier = modifier
                                    )

                                    TextH3(
                                        modifier = modifier.weight(7f),
                                        text = "${proveedor_serv_identificacion}")
                                }

                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(01f)
                                        , painter = painterResource(id = R.drawable.outline_add_business_24)
                                        , contentDescription = stringResource(id = R.string.proovedor_servicio_nombre)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),
                                        text = "${proveedor_serv_nombre}")
                                }


                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(01f)
                                        , painter = painterResource(id = R.drawable.baseline_phone_24)
                                        , contentDescription = stringResource(id = R.string.proovedor_servicio_telefono)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${proveedor_serv_telefono}")
                                }

                                Row(modifier= modifier) {

                                    Icon(
                                        modifier = modifier.weight(01f)
                                        , painter = painterResource(id = R.drawable.outline_alternate_email_24)
                                        , contentDescription = stringResource(id = R.string.proovedor_servicio_email)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${proveedor_serv_email}")
                                }

                                Row(modifier= modifier) {

                                    Icon(
                                        modifier = modifier.weight(1f)
                                        , painter = painterResource(id = R.drawable.outline_location_on_24)
                                        , contentDescription = stringResource(id = R.string.proovedor_servicio_ubicacion)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${proveedor_serv_ubicacion}")
                                }
                            }
                        },

                        modifier = modifier,

                        onClick = {
                            navController.navigate( route = Screen.ProveedorServiciosFormScreenRoute.route_screen )
                        }
                    )
                }

                Spacer(modifier = modifier)
            }
            */

            /** BODY.SECCION: informacion del Cliente */
            item {

                if ((cliente_identificacion == null) or (cliente_identificacion =="")
                    and (cliente_nombre == null) or (cliente_nombre =="")
                    and (cliente_telefono == null) or (cliente_telefono =="")
                    and (cliente_email == null) or (cliente_email =="")
                    and (cliente_ubicacion == null) or (cliente_ubicacion =="")
                ){

                    CustomCardComponent(
                        titulo = {
                            TextH2 (
                                modifier = modifier,
                                text = stringResource(id = R.string.datos_cliente)
                            )
                        },

                        content = {
                            TextH3(
                                modifier = modifier,
                                text = stringResource(id = R.string.informacion_no_disponible)
                            )
                        },


                        modifier = modifier,
                        onClick = {
                            navController.navigate(route = Screen.ClienteFormScreenRoute.route_screen)
                        }
                    )

                } else {

                    CustomCardComponent(

                        titulo = {
                            TextH2(
                                modifier = modifier,
                                text = stringResource(id = R.string.datos_cliente)
                            )
                        },

                        content = {
                            Column(modifier = Modifier.fillMaxWidth()) {

                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(1f)
                                        , painter = painterResource(id = R.drawable.outline_person_outline_24)
                                        , contentDescription = stringResource(id = R.string.cliente_nombre)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${cliente_nombre}")
                                }

                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(1f)
                                        , painter = painterResource(id = R.drawable.outline_tag_24)
                                        , contentDescription = stringResource(id = R.string.cliente_identificacin)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${cliente_identificacion}")
                                }

                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(1f)
                                        , painter = painterResource(id = R.drawable.outline_location_on_24)
                                        , contentDescription = stringResource(id = R.string.cliente_ubicacion)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${cliente_ubicacion}")
                                }

                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(1f)
                                        , painter = painterResource(id = R.drawable.baseline_phone_24)
                                        , contentDescription = stringResource(id = R.string.cliente_telefono)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${cliente_telefono}")
                                }

                                Row(modifier= modifier) {
                                    Icon(
                                        modifier = modifier.weight(1f)
                                        , painter = painterResource(id = R.drawable.outline_alternate_email_24)
                                        , contentDescription = stringResource(id = R.string.cliente_email)
                                        //, modifier = modifier
                                    )

                                    TextH3(modifier = modifier.weight(7f),text = "${cliente_email}")
                                }
                            }
                        },

                        modifier = modifier.pointerInput(Unit){
                            detectTapGestures(
                                onTap = { navController.navigate(route = Screen.ClienteFormScreenRoute.route_screen) },

                                onDoubleTap = {
                                    runBlocking{
                                        clienteViewModel.limpiarDatosCliente()
                                    }
                                }
                            )
                        },

                        onClick = { },
                    )
                }
            }

            /** BODY.SECCION: titulo lista de servicios */
            item {

                Spacer(modifier = modifier.padding(top = 19.dp))

                TextH2(
                    //color = colorScheme.primaryContainer,
                    text = stringResource(id = R.string.lista_servicios_contratados)
                    ,   modifier = modifier
                )

                if(servicioViewModel.servicioDataStore.value.listaServicioEntity.isEmpty()){
                    TextH3(
                        modifier = modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.informacion_no_disponible)
                    )
                }
            }


            /** BODY.SECCION: lista de servicios */
            itemsIndexed( servicioViewModel.servicioDataStore.value.listaServicioEntity ){ index, servicioAgregado ->

                var dobletap_info = stringResource(id = R.string.doble_tap_eliminar)

                CustomCardComponentV2(
                    modifier = modifier
                        .pointerInput(Unit){
                            detectTapGestures(
                                onTap = {
                                    navController.navigate(route = Screen.ServicioFormScreenRoute.route_screen+"/$index")
                                },

                                onDoubleTap = { dobleTap ->

                                    servicioViewModel.eliminar(indice = index)

                                }
                            )},

                    onClick = { },

                    titulo = {
                        Row {
                            TextH3(
                                color = backgroundLight,
                                //modifier = modifier.fillMaxWidth(.03f),
                                modifier = modifier,
                                text = servicioAgregado.cantidad.toString(),
                                //textAlign = TextAlign.End
                                textAlign = TextAlign.Start
                            )

                            TextH3(
                                color = backgroundLight,
                                modifier = modifier.fillMaxWidth(),
                                text = servicioAgregado.descripcion_servicio,
                                //textAlign = TextAlign.End
                            )
                        }
                    },

                    content = {
                        //Row {
                        Column {
                            /*
                            TextH3(
                                modifier = modifier.fillMaxWidth(),
                                //modifier = modifier,
                                text = stringResource(id = R.string.total_servicio),
                                //textAlign = TextAlign.Start
                                textAlign = TextAlign.Right
                            )
                            */
                            TextH2(
                                //color = backgroundLight,
                                //color = MaterialTheme.colorScheme.primaryContainer,
                                color = primaryContainerDark,
                                modifier = modifier.fillMaxWidth(),
                                //modifier = modifier.weight(1f),
                                //modifier = modifier,
                                text = "${mNumFormat.format( servicioAgregado.valor_total_del_servicio )}",
                                textAlign = TextAlign.End
                                //textAlign = TextAlign.Right
                            )
                        }
                    }
                )
            }

            /** BODY.SECCION: Suma total de todos los servicios/ventas contratado */
            item {

                Spacer(modifier = modifier)
                Spacer(modifier = modifier)

                if(servicioViewModel.servicioDataStore.value.listaServicioEntity.isNotEmpty()){
                    Column {
                        TextH2(
                            //color = backgroundLight,
                            textAlign = TextAlign.End,
                            modifier = modifier.fillMaxWidth(),
                            text = "${stringResource(id = R.string.total_servicio)}"
                        )

                        TextH2(
                            color = primaryContainerDark,
                            textAlign = TextAlign.End,
                            modifier = modifier.fillMaxWidth(),
                            text = "${mNumFormat.format(servicioViewModel.sumaTotalServicios())}"
                        )
                    }
                }

                Spacer(modifier = modifier)

            }

            /** BODY.SECCION: Boton para AGREGAR Nuevo Servicio */
            item {

                if (servicioViewModel.servicioDataStore.value.listaServicioEntity.size <= 14) {
                    CustomOutlineButton(
                        text = R.string.boton_agregar_servicio
                        , icono = R.drawable.baseline_add_circle_outline_24
                        , onClick = { navController.navigate(route = Screen.ServicioFormScreenRoute.route_screen+"/ ") }
                        , modifier = modifier
                    )
                }
            }

            /** F O O T E R --------------------------------------------------------------------------*/
            /** Botonera de acciones finales como Exportar a PDF y Comportir */
            item {

                //var uri: Uri?
                //var uri = Uri.EMPTY
                var uri = cuentaDeCobroViewModel.pdf_generado_uri.value

                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->

                    llenarEntidadesYGenerarPDF(
                        context = context,
                        fecha_expedicion_cuenta_de_cobro = fecha_expedicion_cuenta_de_cobro,
                        hora_expedicion_cuenta_de_cobro = hora_expedicion_cuenta_de_cobro,
                        imagenCorporativaViewModel = imagenCorporativaViewModel,
                        cuentaDeCobroViewModel = cuentaDeCobroViewModel,
                        proveedorServiciosViewModel = proveedorServiciosViewModel,
                        clienteViewModel = clienteViewModel,
                        servicioViewModel = servicioViewModel
                    )

                    uri = cuentaDeCobroViewModel.pdf_generado_uri.value
                }

                /** Esta condicion determina si el boton de exportar a PDF sea visible o no
                valida que todos los campos de los fomularios sean informados para poder imprimie el PDF correctamente */
                if (
                    cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_numero.value!! > 0
                    &&
                    //cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value!! != Uri.EMPTY
                    imagenCorporativaViewModel.imagenCorporativaDataState.value.imagen_corporativa.value!! != Uri.EMPTY
                    &&
                    proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_identificacion.value!! != ""
                    &&
                    proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_nombre.value!! != ""
                    &&
                    proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_ubicacion.value!! != ""
                    &&
                    proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_telefono.value!! != ""
                    &&
                    proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_email.value!! != ""
                    &&

                    clienteViewModel.clienteDataState.value.cliente_identificacion.value!! != ""
                    &&
                    clienteViewModel.clienteDataState.value.cliente_nombre.value!! != ""
                    &&
                    clienteViewModel.clienteDataState.value.cliente_ubicacion.value!! != ""
                    &&
                    clienteViewModel.clienteDataState.value.cliente_telefono.value!! != ""
                    &&
                    clienteViewModel.clienteDataState.value.cliente_email.value!! != ""
                    &&

                    servicioViewModel.servicioDataStore.value.listaServicioEntity != emptyList<ServicioEntity>()
                    && servicioViewModel.servicioDataStore.value.listaServicioEntity.size > 0
                ){

                    CustomButton(
                        text = R.string.exportar_a_PDF
                        , icono = R.drawable.outline_picture_as_pdf_24
                        , onClick = {

                            try {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                                    requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                } else {

                                    llenarEntidadesYGenerarPDF(
                                        context = context,
                                        fecha_expedicion_cuenta_de_cobro = fecha_expedicion_cuenta_de_cobro,
                                        hora_expedicion_cuenta_de_cobro = hora_expedicion_cuenta_de_cobro,
                                        imagenCorporativaViewModel = imagenCorporativaViewModel,
                                        cuentaDeCobroViewModel = cuentaDeCobroViewModel,
                                        proveedorServiciosViewModel = proveedorServiciosViewModel,
                                        clienteViewModel = clienteViewModel,
                                        servicioViewModel = servicioViewModel
                                    )

                                    uri = cuentaDeCobroViewModel.pdf_generado_uri.value

                                }

                                onNavigateToDialogComparitPDF()

                                cuentaDeCobroViewModel.incrementarContadorDocsEmitidos(mCuentaDeCobroContadorEntity)
                                cuentaDeCobroViewModel.actualizarFechaHoraSistema()

                                Toast.makeText(context,"PDF creado exitosamente!", Toast.LENGTH_SHORT).show()


                                /** despues de imprimir se limpia datos del cliente */
                                /* T E M P O R A L M E N T E   D E S A C T I V A D O
                                runBlocking{
                                    clienteViewModel.limpiarDatosCliente()
                                }
                                */

                                /** despues de imprimir se limpia datos de servicios agregados */
                                servicioViewModel.limpiarListaDeServicios()

                            } catch (e:Exception){

                                Toast.makeText(context,"Error! No se creó PDF", Toast.LENGTH_SHORT).show()
                                Log.e("_xxx","e: ${e}")
                                Log.e("_xxx","e.message: ${e.message}")

                            } catch (io_e:IOException){

                                Toast.makeText(context,"IOError! No se creó PDF", Toast.LENGTH_SHORT).show()
                                Log.e("_xxx","io_e: ${io_e}")
                                Log.e("_xxx","io_e.message: ${io_e.message}")

                            }

                            /*
                            /** P R U E B A  -  I N I */

                            Log.d("_xxx","fecha_expedicion_cuenta_de_cobro: ${fecha_expedicion_cuenta_de_cobro}")
                            Log.d("_xxx","hora_expedicion_cuenta_de_cobro: ${hora_expedicion_cuenta_de_cobro}")

                            Log.d("_xxx","---------------------------------")

                            Log.d("_xxx","imagenCorporativaViewModel: ${imagenCorporativaViewModel.imagenCorporativaDataState.value.imagen_corporativa.value}")

                            Log.d("_xxx","---------------------------------")

                            Log.d("_xxx","cuentaDeCobroViewModel: ${cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_numero.value.toString()}")
                            Log.d("_xxx","cuentaDeCobroViewModel: ${cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_hora_expedicion.value}")
                            Log.d("_xxx","cuentaDeCobroViewModel: ${cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_fecha_expedicion.value}")

                            Log.d("_xxx","---------------------------------")

                            Log.d("_xxx","proveedorServiciosViewModel${proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_nombre.value}")
                            Log.d("_xxx","proveedorServiciosViewModel${proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_identificacion.value}")
                            Log.d("_xxx","proveedorServiciosViewModel${proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_ubicacion.value}")
                            Log.d("_xxx","proveedorServiciosViewModel${proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_telefono.value}")
                            Log.d("_xxx","proveedorServiciosViewModel${proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_email.value}")

                            Log.d("_xxx","---------------------------------")

                            Log.d("_xxx","clienteViewModel: ${clienteViewModel.clienteDataState.value.cliente_nombre.value}")
                            Log.d("_xxx","clienteViewModel: ${clienteViewModel.clienteDataState.value.cliente_identificacion.value}")
                            Log.d("_xxx","clienteViewModel: ${clienteViewModel.clienteDataState.value.cliente_ubicacion.value}")
                            Log.d("_xxx","clienteViewModel: ${clienteViewModel.clienteDataState.value.cliente_telefono.value}")
                            Log.d("_xxx","clienteViewModel: ${clienteViewModel.clienteDataState.value.cliente_email.value}")

                            Log.d("_xxx","---------------------------------")

                            Log.d("_xxx","servicioViewModel: ${servicioViewModel.servicioDataStore.value.listaServicioEntity.toList()}")
                            Log.d("_xxx","servicioViewModel: ${servicioViewModel.sumaTotalServicios()}")

                            /** P R U E B A  -  F I N */
                            */

                        }
                        , modifier = modifier
                    )
                }
            }
        }


        /** F I N   S C A F O L D */

    }
}



private fun llenarEntidadesYGenerarPDF(
    context: Context,
    fecha_expedicion_cuenta_de_cobro : String,
    hora_expedicion_cuenta_de_cobro : String,
    imagenCorporativaViewModel : ImagenCorporativaViewModel,
    cuentaDeCobroViewModel : CuentaDeCobroViewModel,
    proveedorServiciosViewModel : ProveedorServiciosViewModel,
    clienteViewModel : ClienteViewModel,
    servicioViewModel : ServicioViewModel,
){

    //Log.d("_xxx","imagen_corporativa_uri = ${ cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value }")
    //Log.d("_xxx","imagen_corporativa_uri = ${ cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value?.lastPathSegment }")

    cuentaDeCobroViewModel.generarPDF(
        context = context
        ,mCuentaDeCobroEntity = CuentaDeCobroEntity(
            numero_consecutivo = cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_numero.value.toString() ?: "SIN DATOS",
            //fecha_hora_generacion_reporte = cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_fecha_expedicion.value!! ?: "SIN DATOS",
            fecha_hora_generacion_reporte = "${ fecha_expedicion_cuenta_de_cobro } ${ hora_expedicion_cuenta_de_cobro }" ?: "SIN DATOS",
            tipo_reporte = "Cuenta de Cobro Nº${ cuentaDeCobroViewModel.cuentaDeCobroDataState.value.cuenta_de_cobro_numero.value?: 0 }",
            total_suma_servicios = servicioViewModel.sumaTotalServicios()?: 0,
            //imagen_corporativa_uri = cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value!! ?: Uri.EMPTY,
            imagen_corporativa_uri = imagenCorporativaViewModel.imagenCorporativaDataState.value.imagen_corporativa.value!! ?: Uri.EMPTY,

            mDatosPrestadorServicioEntity = ProveedorServiciosEntity(
                identificacion = proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_identificacion.value!! ?: "SIN DATOS",
                nombre = proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_nombre.value!! ?: "SIN DATOS",
                ubicacion = proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_ubicacion.value!! ?: "SIN DATOS",
                telefono = proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_telefono.value!! ?: "SIN DATOS",
                email = proveedorServiciosViewModel.proveedorServiciosDataState.value.proovedor_servicio_email.value!! ?: "SIN DATOS"
            ),

            mDatosReceptorServicioEntity = ClienteEntity(
                identificacion = clienteViewModel.clienteDataState.value.cliente_identificacion.value!! ?: "SIN DATOS",
                nombre = clienteViewModel.clienteDataState.value.cliente_nombre.value!! ?: "SIN DATOS",
                ubicacion = clienteViewModel.clienteDataState.value.cliente_ubicacion.value!! ?: "SIN DATOS",
                telefono = clienteViewModel.clienteDataState.value.cliente_telefono.value!! ?: "SIN DATOS",
                email = clienteViewModel.clienteDataState.value.cliente_email.value!! ?: "SIN DATOS"
            ),

            mServicioEntity = servicioViewModel.servicioDataStore.value.listaServicioEntity ?: emptyList()
        )
    )
}
