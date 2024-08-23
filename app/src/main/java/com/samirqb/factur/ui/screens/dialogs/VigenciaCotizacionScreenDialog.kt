package com.samirqb.factur.ui.screens.dialogs

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
import com.samirqb.factur.tools.RestriccionesDeCamposDeEntradas
import com.samirqb.factur.ui.components.BotonesIconosDeAccion
import com.samirqb.factur.ui.components.CustomCardSmallComponent
import com.samirqb.factur.ui.components.CustomOutlinedNumberField
import com.samirqb.factur.ui.components.TextH2
import com.samirqb.factur.ui.components.TextP1
import com.samirqb.factur.viewmodels.CotizacionViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun VigenciaCotizacionScreenDialog(
    navController: NavHostController,
    cotizacionViewModel: CotizacionViewModel,
    modifier: Modifier = Modifier,
){

    var vigencia_cotizacion by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = { navController.popBackStack() },
        content = {

            CustomCardSmallComponent(
                modifier = modifier,
                onClick = { /*TODO*/ }
                , titulo = {
                    TextH2(modifier = modifier, text = stringResource(id = R.string.titulo_vigencia) + ": ")
                }
                , content = {

                    TextP1(modifier = modifier, text = "Ingrese los dias de vigencia")

                    CustomOutlinedNumberField(
                        label = R.string.titulo_vigencia
                        , value = vigencia_cotizacion
                        , textMaxChar = 3
                        , onValueChange = { if ( it.length <= 3) vigencia_cotizacion = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
                        , modifier = modifier
                    )


                    BotonesIconosDeAccion(

                        //modifier = modifier

                        enabledGuardarButton = if( vigencia_cotizacion.isNotEmpty()) true
                        else false,

                        onCancelar = {

                            navController.navigateUp()

                        }

                        , onGuardar = {

                            runBlocking {

                                cotizacionViewModel.actualizarVigencia(
                                    vigencia_cotizacion.toInt()
                                )

                            }

                            navController.navigateUp()

                        }
                    )
                }
            )
        }
    )
}
