package com.samirqb.factur.ui.screens.dialogs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.samirqb.factur.R
import com.samirqb.factur.ui.components.BotonesIconosDeAccion_Cancel_Compartir
import com.samirqb.factur.ui.components.CustomCardSmallComponent
import com.samirqb.factur.ui.components.TextH2
import com.samirqb.factur.ui.components.TextP1
import com.samirqb.factur.ui.intents.CompartiIntent
import com.samirqb.factur.viewmodels.CuentaDeCobroViewModel
import kotlinx.coroutines.runBlocking
import okio.IOException

@Composable
fun CompartirPDFScreenDialog(
    modifier: Modifier,
    navController: NavHostController,
    cuentaDeCobroViewModel:CuentaDeCobroViewModel
) {

    var context = LocalContext.current

    Dialog(
        onDismissRequest = { navController.popBackStack() },
        content = {
            CustomCardSmallComponent(
                modifier = modifier,
                onClick = { /*TODO*/ }
                , titulo = {

                    TextH2(modifier = modifier, text =  stringResource(id = R.string.compartir_pdf)+"?",)
                    /*
                    Column(modifier = modifier) {
                        Icon(painter = painterResource(id = R.drawable.baseline_share_24) , contentDescription = "", modifier = modifier.fillMaxWidth().align(
                            Alignment.CenterHorizontally))
                        TextH2(modifier = modifier, text =  stringResource(id = R.string.compartir_pdf),)
                    }
                    */
                }
                , content = {
                    TextP1(modifier = modifier, text = stringResource(id = R.string.desea_compartir_pdf))


                    //BotonesDeAccion_Cancel_Compartir(
                    BotonesIconosDeAccion_Cancel_Compartir(
                        //modifier = modifier,

                        onCancelar = {
                            //navController.popBackStack()
                            navController.navigateUp()
                                     }

                        , onCompartir = {

                            runBlocking {

                                try {
                                    CompartiIntent(context = context, pdfFileUri = cuentaDeCobroViewModel.pdf_generado_uri.value)
                                } catch (e:Exception){
                                    Log.e("_xxx","Error al tratar de compartir el PDF")
                                    Log.e("_xxx","Exception: ${e}")
                                } catch (ioe: IOException){
                                    Log.e("_xxx","Error al tratar de compartir el PDF")
                                    Log.e("_xxx","IOException: ${ioe}")
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