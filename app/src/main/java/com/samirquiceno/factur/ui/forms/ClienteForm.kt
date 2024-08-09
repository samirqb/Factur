package com.samirquiceno.factur.ui.forms

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.samirquiceno.factur.R
import com.samirquiceno.factur.models.ClienteEntity
import com.samirquiceno.factur.tools.IsNullOrBlankOrEmptyTool
import com.samirquiceno.factur.ui.components.BotonesDeAccion
import com.samirquiceno.factur.ui.components.CustomOutlinedEmailField
import com.samirquiceno.factur.ui.components.CustomOutlinedPhoneField
import com.samirquiceno.factur.ui.components.CustomOutlinedTextField
import com.samirquiceno.factur.viewmodels.ClienteViewModel
import kotlinx.coroutines.runBlocking

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ClienteForm(
    navController: NavHostController,
    clienteViewModel : ClienteViewModel,
    modifier: Modifier = Modifier
) {

    var entity = clienteViewModel.clienteDataState


    var identificacion = (entity.value.cliente_identificacion.value).toString()
    var nombre         = entity.value.cliente_nombre.value.toString()
    var telefono       = entity.value.cliente_telefono.value.toString()
    var email          = entity.value.cliente_email.value.toString()
    var ubicacion      = entity.value.cliente_ubicacion.value.toString()


    var cliente_identificacion by rememberSaveable { mutableStateOf( identificacion ) }
    var cliente_nombre by rememberSaveable { mutableStateOf( nombre ) }
    var cliente_ubicacion by rememberSaveable { mutableStateOf(ubicacion) }
    var cliente_telefono by rememberSaveable { mutableStateOf(telefono) }
    var cliente_email by rememberSaveable { mutableStateOf(email) }

    var cliente_identificacion_bool = IsNullOrBlankOrEmptyTool(cliente_identificacion)
    var cliente_nombre_tool = IsNullOrBlankOrEmptyTool(cliente_nombre)
    var cliente_ubicacion_tool = IsNullOrBlankOrEmptyTool(cliente_ubicacion)
    var cliente_telefono_tool = IsNullOrBlankOrEmptyTool(cliente_telefono)
    var cliente_email_tool = IsNullOrBlankOrEmptyTool(cliente_email)

    var enabledGuardarButton by rememberSaveable {mutableStateOf(false)}

    val INPUT_TEXT_MAX_CHAR = 29
    val INPUT_EMAIL_MAX_CHAR = 50

    //LazyColumn(modifier = modifier.padding(7.dp)) {
    Column( modifier = modifier ) {

        /* campos del formulario proveedor de servicios */
        CustomOutlinedTextField(
            label = R.string.identificacion,
            value = cliente_identificacion,
            onValueChange = {
                //if ( it.length <= INPUT_TEXT_MAX_CHAR) cliente_identificacion = it.uppercase()

                if ( it.length <= INPUT_TEXT_MAX_CHAR) {
                    cliente_identificacion = it.uppercase()
                }
            }
        )

        CustomOutlinedTextField(
            label = R.string.nombre,
            value = cliente_nombre,
            //onValueChange = { cliente_nombre = it },
            onValueChange = {
                //if ( it.length <= INPUT_TEXT_MAX_CHAR) cliente_nombre = it.uppercase()

                if ( it.length <= INPUT_TEXT_MAX_CHAR) {
                    cliente_nombre = it.uppercase()
                }

            }
        )

        CustomOutlinedTextField(
            label = R.string.ubicacion,
            value = cliente_ubicacion,
            //onValueChange = {cliente_ubicacion = it},
            onValueChange = {
                //if ( it.length <= INPUT_TEXT_MAX_CHAR) cliente_ubicacion = it.uppercase()

                if ( it.length <= INPUT_TEXT_MAX_CHAR) {
                    cliente_ubicacion = it.uppercase()
                }
            }
        )

        CustomOutlinedPhoneField(
            label = R.string.telefono,
            value = cliente_telefono,
            //onValueChange = { cliente_telefono = it },
            onValueChange = {
                //if ( it.length <= INPUT_TEXT_MAX_CHAR) cliente_telefono = it.uppercase()

                if ( it.length <= INPUT_TEXT_MAX_CHAR) {
                    cliente_telefono = it.uppercase()
                }
            }
        )

        CustomOutlinedEmailField(
            label = R.string.email,
            value = cliente_email,
            //onValueChange = { cliente_email = it },
            onValueChange = {
                //if ( it.length <= INPUT_EMAIL_MAX_CHAR) cliente_email = it.lowercase()

                if ( it.length <= INPUT_EMAIL_MAX_CHAR) {
                    cliente_email = it.lowercase()
                }
            }
        )





        BotonesDeAccion(
            modifier = modifier,
            enabledGuardarButton = if (cliente_identificacion_bool
                && cliente_nombre_tool
                && cliente_ubicacion_tool
                && cliente_telefono_tool
                && cliente_email_tool ) { true } else { false }

            , onCancelar = {navController.popBackStack()}
            , onGuardar = {
                runBlocking {
                    clienteViewModel.insert(
                        entity = ClienteEntity(
                            identificacion = cliente_identificacion,
                            nombre = cliente_nombre,
                            telefono = cliente_telefono,
                            email = cliente_email,
                            ubicacion = cliente_ubicacion
                        )
                    )
                }

                navController.popBackStack()

            }
        )
    }
}
