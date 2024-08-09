package com.samirquiceno.factur.ui.forms

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.samirquiceno.factur.R
import com.samirquiceno.factur.models.ServicioEntity
import com.samirquiceno.factur.tools.RestriccionesDeCamposDeEntradas
import com.samirquiceno.factur.ui.components.BotonesDeAccion
import com.samirquiceno.factur.ui.components.CustomOutlinedCurencyField
import com.samirquiceno.factur.ui.components.CustomOutlinedNumberField
import com.samirquiceno.factur.ui.components.CustomOutlinedTextField
import com.samirquiceno.factur.ui.components.TextH2
import com.samirquiceno.factur.viewmodels.ServicioViewModel
import kotlinx.coroutines.runBlocking

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ServicioForm(
    navController: NavHostController,
    servicioViewModel: ServicioViewModel,
    servicio_id:String,
    modifier: Modifier = Modifier,
) {

    var entity = servicioViewModel.servicioDataStore.value.listaServicioEntity

    var servicio_cantidad by rememberSaveable{ mutableStateOf(
        if ( servicio_id == "null" )"" else entity[servicio_id.toInt()].cantidad.toString()
    ) }

    var servicio_descripcion by rememberSaveable{ mutableStateOf(
        if ( servicio_id == "null" )"" else entity[servicio_id.toInt()].descripcion_servicio
    ) }

    var servicio_valor_unitario by rememberSaveable{ mutableStateOf(
        if ( servicio_id == "null" )"" else entity[servicio_id.toInt()].valor_unitario_del_servicio.toString()
    ) }

    var servicio_valor_total by rememberSaveable{ mutableStateOf(
        if ( servicio_id == "null" )"" else entity[servicio_id.toInt()].valor_total_del_servicio.toString()
    ) }

    var mNumFormat = NumberFormat.getCurrencyInstance()

    var enabledGuardarButton by rememberSaveable {mutableStateOf(false)}

    val INPUT_TEXT_MAX_CHAR = 29
    val INPUT_NUM_MAX_CHAR = 9


    CustomOutlinedNumberField(
        label = R.string.servicio_cantidad,
        value = servicio_cantidad,
        textMaxChar = INPUT_NUM_MAX_CHAR,
        modifier = modifier,
        //onValueChange = { if ( it.length <= INPUT_NUM_MAX_CHAR) servicio_cantidad = it }
        onValueChange = { if ( it.length <= INPUT_NUM_MAX_CHAR) servicio_cantidad = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
    )

    CustomOutlinedTextField(
        label = R.string.servicio_descripcion,
        value = servicio_descripcion,
        modifier = modifier,
        onValueChange = {
            if ( it.length <= INPUT_TEXT_MAX_CHAR) {
                servicio_descripcion = it.uppercase()
            }
        },
    )

    CustomOutlinedCurencyField(
        label = R.string.servicio_valor_unitario,
        value = servicio_valor_unitario,
        //value = mNumFormat.format(if(servicio_valor_unitario == "")0 else servicio_valor_unitario.toInt()),
        modifier = modifier,
        //onValueChange = {  ( it.length <= INPUT_NUM_MAX_CHAR) servicio_valor_unitario = it }
        onValueChange = { if ( it.length <= INPUT_NUM_MAX_CHAR) servicio_valor_unitario = RestriccionesDeCamposDeEntradas.soloNumeros(it) }
    )

    /*
    CustomOutlinedCurencyField(
        label = R.string.servicio_valor_total,
        value = servicio_valor_total,
        //onValueChange = { cliente_telefono = it },
        modifier = modifier,
        onValueChange = { if ( it.length <= INPUT_NUM_MAX_CHAR) servicio_valor_total = it.uppercase() }
    )
    */

    Spacer(modifier = modifier)

    if (servicio_cantidad != "" && servicio_valor_unitario != ""){
        servicio_valor_total = (servicio_cantidad.toInt() * servicio_valor_unitario.toInt()).toString()
        TextH2(
            modifier = modifier.fillMaxWidth()
            //, text = "${stringResource(id = R.string.total_servicio)} ${servicio_valor_total}"
            , text = "${stringResource(id = R.string.total_servicio)}"
            , textAlign = TextAlign.End)

        TextH2(
            modifier = modifier.fillMaxWidth()
            , text = mNumFormat.format(servicio_valor_total.toInt())
            , textAlign = TextAlign.End)
    } else {
        servicio_valor_total = ""
    }


    Spacer(modifier = modifier)



    if ( servicio_descripcion.isNotEmpty() && servicio_valor_total.isNotEmpty() ) {
        enabledGuardarButton = true
    } else {
        enabledGuardarButton = false
    }


    if (servicio_id == "null"){
        /** PARA CREAR UN REGISTRO NUEVO */
        BotonesDeAccion(
            enabledGuardarButton = enabledGuardarButton,
            modifier = modifier
            , onCancelar = { navController.popBackStack() }

            , onGuardar = {
                runBlocking {
                    servicioViewModel.insert(
                        entity = ServicioEntity(
                            cantidad = servicio_cantidad.toInt()
                            , descripcion_servicio = servicio_descripcion
                            , valor_unitario_del_servicio = servicio_valor_unitario.toInt()
                            , valor_total_del_servicio = servicio_valor_total.toInt()
                        )
                    )
                }

                navController.popBackStack()

            }
        )
    } else {
        /** PARA EDITAR UN REGISTRO EXISTENTE */
        BotonesDeAccion(
            enabledGuardarButton = enabledGuardarButton,
            modifier = modifier
            , onCancelar = { navController.popBackStack() }

            , onGuardar = {
                runBlocking {
                    servicioViewModel.update(index = servicio_id.toInt(), entity = ServicioEntity(
                        cantidad = servicio_cantidad.toInt()
                        , descripcion_servicio = servicio_descripcion
                        , valor_unitario_del_servicio = servicio_valor_unitario.toInt()
                        , valor_total_del_servicio = servicio_valor_total.toInt()))
                }

                navController.popBackStack()
            }
        )
    }
}
