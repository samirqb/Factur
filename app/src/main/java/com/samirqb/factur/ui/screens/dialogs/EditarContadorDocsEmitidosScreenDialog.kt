package com.samirqb.factur.ui.screens.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.samirqb.factur.R
import com.samirqb.factur.models.CotizacionContadorEntity
import com.samirqb.factur.models.CuentaDeCobroContadorEntity
import com.samirqb.factur.models.FacturaContadorEntity
import com.samirqb.factur.tools.RestriccionesDeCamposDeEntradas
import com.samirqb.factur.ui.components.BotonesIconosDeAccion
import com.samirqb.factur.ui.components.CustomCardSmallComponent
import com.samirqb.factur.ui.components.CustomOutlinedNumberField
import com.samirqb.factur.ui.components.TextH2
import com.samirqb.factur.ui.components.TextP1
import com.samirqb.factur.viewmodels.CotizacionViewModel
import com.samirqb.factur.viewmodels.CuentaDeCobroViewModel
import com.samirqb.factur.viewmodels.FacturaViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarContadorDocsEmitidosScreenDialog(
    navController: NavHostController,
    //onDismissRequest: ()->Unit,
    cotizacionViewModel: CotizacionViewModel,
    cuentaDeCobroViewModel: CuentaDeCobroViewModel,
    facturaViewModel: FacturaViewModel,
    modifier: Modifier = Modifier,
    contador_id :String?
) {

    var contador_cotizacion by rememberSaveable { mutableStateOf("") }
    var contador_cuenta_cobro by rememberSaveable { mutableStateOf("") }
    var contador_factura by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = { navController.popBackStack() },
        content = {

            CustomCardSmallComponent(
                modifier = modifier,
                onClick = { /*TODO*/ }
                , titulo = {
                    TextH2(modifier = modifier, text = stringResource(id = R.string.num_onsecutivo) + ": ")
                }
                , content = {

                    TextP1(modifier = modifier, text = "Ingrese el numero consecutivo")

                    /*
                    CustomOutlinedNumberField(
                        label = R.string.num_onsecutivo
                        , value = contador_cuenta_cobro
                        , textMaxChar = 9
                        , onValueChange = { if ( it.length <= 9) contador_cuenta_cobro = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
                        , modifier = modifier
                    )
                    */

                    if (contador_id == "contador_cotizacion" ){
                        CustomOutlinedNumberField(
                            label = R.string.num_onsecutivo
                            , value = contador_cotizacion
                            , textMaxChar = 9
                            , onValueChange = { if ( it.length <= 9) contador_cotizacion = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
                            , modifier = modifier
                        )
                    }

                    if (contador_id == "contador_cuenta_cobro" ){
                        CustomOutlinedNumberField(
                            label = R.string.num_onsecutivo
                            , value = contador_cuenta_cobro
                            , textMaxChar = 9
                            , onValueChange = { if ( it.length <= 9) contador_cuenta_cobro = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
                            , modifier = modifier
                        )
                    }

                    if (contador_id == "contador_factura" ){
                        CustomOutlinedNumberField(
                            label = R.string.num_onsecutivo
                            , value = contador_factura
                            , textMaxChar = 9
                            , onValueChange = { if ( it.length <= 9) contador_factura = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
                            , modifier = modifier
                        )
                    }

                    BotonesIconosDeAccion(
                        //modifier = modifier

                        enabledGuardarButton = if( contador_cotizacion.isNotEmpty()
                            || contador_cuenta_cobro.isNotEmpty()
                            || contador_factura.isNotEmpty() ) true
                        else false,

                        onCancelar = {
                            //navController.popBackStack()
                            navController.navigateUp()
                                     }

                        , onGuardar = {

                            runBlocking {

                                if (contador_id == "contador_cotizacion" ){
                                    cotizacionViewModel.insert(
                                        entity = CotizacionContadorEntity(
                                            contador = contador_cotizacion.toInt(),
                                        )
                                    )
                                }
                                if (contador_id == "contador_cuenta_cobro" ){
                                    cuentaDeCobroViewModel.insert(
                                        entity = CuentaDeCobroContadorEntity(
                                            contador = contador_cuenta_cobro.toInt(),
                                        )
                                    )
                                }

                                if (contador_id == "contador_factura" ){
                                    facturaViewModel.insert(
                                        entity = FacturaContadorEntity(
                                            contador = contador_factura.toInt(),
                                        )
                                    )
                                }
                            }

                            //navController.popBackStack()
                            navController.navigateUp()
                        }
                    )
                }
            )
        }
    )

}
