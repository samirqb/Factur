package com.samirquiceno.factur.ui.screens.dialogs

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
import com.samirquiceno.factur.R
import com.samirquiceno.factur.models.CuentaDeCobroContadorEntity
import com.samirquiceno.factur.tools.RestriccionesDeCamposDeEntradas
import com.samirquiceno.factur.ui.components.BotonesIconosDeAccion
import com.samirquiceno.factur.ui.components.CustomCardSmallComponent
import com.samirquiceno.factur.ui.components.CustomOutlinedNumberField
import com.samirquiceno.factur.ui.components.TextH2
import com.samirquiceno.factur.ui.components.TextP1
import com.samirquiceno.factur.viewmodels.CuentaDeCobroViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarContadorDocsEmitidosScreenDialog(
    navController: NavHostController,
    //onDismissRequest: ()->Unit,
    cuentaDeCobroViewModel: CuentaDeCobroViewModel,
    modifier: Modifier = Modifier

) {


    /*
    val openAlertDialog = remember { mutableStateOf(false) }
    var openAlertDialog2 by rememberSaveable { mutableStateOf(false) }
    val openAlertDialog3 = rememberSaveable { mutableStateOf(false) }
    */

    var value = rememberSaveable { mutableStateOf("") }
    var value1 = rememberSaveable { mutableStateOf("") }
    var value2 by rememberSaveable { mutableStateOf("") }

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

                    CustomOutlinedNumberField(
                        label = R.string.num_onsecutivo
                        , value = value2
                        , textMaxChar = 9
                        , onValueChange = { if ( it.length <= 9) value2 = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
                        , modifier = modifier
                    )

                    BotonesIconosDeAccion(
                        //modifier = modifier

                        enabledGuardarButton = if(value2.isNotEmpty()) true else false,

                        onCancelar = {
                            //navController.popBackStack()
                            navController.navigateUp()
                                     }

                        , onGuardar = {
                            //cuentaDeCobroViewModel.incrementarContadorDocsEmitidos(value2.toInt())

                            runBlocking {
                                cuentaDeCobroViewModel.insert(
                                    entity = CuentaDeCobroContadorEntity(
                                        contador = value2.toInt(),
                                    )
                                )
                            }

                            //cuentaDeCobroViewModel.actualizarContadorDocsEmitidos(value2.toInt())

                            //navController.popBackStack()
                            navController.navigateUp()
                        }
                    )
                }
            )
        }
    )

}
