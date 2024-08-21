package com.samirqb.factur.ui.forms

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.samirqb.factur.R
import com.samirqb.factur.models.ProveedorServiciosEntity
import com.samirqb.factur.tools.IsNullOrBlankOrEmptyTool
import com.samirqb.factur.ui.components.BotonesDeAccion
import com.samirqb.factur.ui.components.CustomOutlinedEmailField
import com.samirqb.factur.ui.components.CustomOutlinedPhoneField
import com.samirqb.factur.ui.components.CustomOutlinedTextField
import com.samirqb.factur.viewmodels.ProveedorServiciosViewModel
import kotlinx.coroutines.runBlocking

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProveedorServiciosForm(
    navController: NavHostController,
    proveedorServiciosViewModel : ProveedorServiciosViewModel,
    modifier: Modifier = Modifier
) {

    var entity = proveedorServiciosViewModel.proveedorServiciosDataState


    var identificacion = (entity.value.proovedor_servicio_identificacion.value).toString()
    var nombre         = entity.value.proovedor_servicio_nombre.value.toString()
    var telefono       = entity.value.proovedor_servicio_telefono.value.toString()
    var email          = entity.value.proovedor_servicio_email.value.toString()
    var ubicacion      = entity.value.proovedor_servicio_ubicacion.value.toString()


    var proovedor_servicio_identificacion by rememberSaveable { mutableStateOf( identificacion ) }
    var proovedor_servicio_nombre by rememberSaveable { mutableStateOf( nombre ) }
    var proovedor_servicio_ubicacion by rememberSaveable { mutableStateOf(ubicacion) }
    var proovedor_servicio_telefono by rememberSaveable { mutableStateOf(telefono) }
    var proovedor_servicio_email by rememberSaveable { mutableStateOf(email) }


    var proovedor_servicio_identificacion_bool = IsNullOrBlankOrEmptyTool(proovedor_servicio_identificacion)
    var proovedor_servicio_nombre_bool = IsNullOrBlankOrEmptyTool(proovedor_servicio_nombre)
    var proovedor_servicio_ubicacion_bool = IsNullOrBlankOrEmptyTool(proovedor_servicio_ubicacion)
    var proovedor_servicio_telefono_bool = IsNullOrBlankOrEmptyTool(proovedor_servicio_telefono)
    var proovedor_servicio_email_bool = IsNullOrBlankOrEmptyTool(proovedor_servicio_email)



    val INPUT_TEXT_MAX_CHAR = 29
    val INPUT_EMAIL_MAX_CHAR = 50

    //LazyColumn(modifier = modifier.padding(7.dp)) {
    Column( modifier = modifier ) {

        /* campos del formulario proveedor de servicios */
        CustomOutlinedTextField(
            label = R.string.identificacion,
            value = proovedor_servicio_identificacion,
            onValueChange = { if ( it.length <= INPUT_TEXT_MAX_CHAR) proovedor_servicio_identificacion = it.uppercase() },
        )

        CustomOutlinedTextField(
            label = R.string.nombre,
            value = proovedor_servicio_nombre,
            //onValueChange = { proovedor_servicio_nombre = it },
            onValueChange = { if ( it.length <= INPUT_TEXT_MAX_CHAR) proovedor_servicio_nombre = it.uppercase() },
        )

        CustomOutlinedTextField(
            label = R.string.ubicacion,
            value = proovedor_servicio_ubicacion,
            //onValueChange = {proovedor_servicio_ubicacion = it},
            onValueChange = { if ( it.length <= INPUT_TEXT_MAX_CHAR) proovedor_servicio_ubicacion = it.uppercase() },
        )

        CustomOutlinedPhoneField(
            label = R.string.telefono,
            value = proovedor_servicio_telefono,
            //onValueChange = { proovedor_servicio_telefono = it },
            onValueChange = { if ( it.length <= INPUT_TEXT_MAX_CHAR) proovedor_servicio_telefono = it.uppercase() },

        )

        CustomOutlinedEmailField(
            label = R.string.email,
            value = proovedor_servicio_email,
            //onValueChange = { proovedor_servicio_email = it },
            onValueChange = { if ( it.length <= INPUT_EMAIL_MAX_CHAR) proovedor_servicio_email = it.lowercase() },
        )

        BotonesDeAccion(
            modifier = modifier,
            onCancelar = {
                //navController.popBackStack()
                navController.navigateUp()
                         }
            , enabledGuardarButton = if (
                proovedor_servicio_identificacion_bool && proovedor_servicio_nombre_bool
                && proovedor_servicio_ubicacion_bool && proovedor_servicio_telefono_bool
                && proovedor_servicio_email_bool
                ) { true } else { false }

            ,onGuardar = {
                runBlocking {
                    proveedorServiciosViewModel.insert(
                        entity = ProveedorServiciosEntity(
                            identificacion = proovedor_servicio_identificacion,
                            nombre = proovedor_servicio_nombre,
                            telefono = proovedor_servicio_telefono,
                            email = proovedor_servicio_email,
                            ubicacion = proovedor_servicio_ubicacion
                        )
                    )
                }

                //navController.popBackStack()
                navController.navigateUp()
            }
        )
    }
}
