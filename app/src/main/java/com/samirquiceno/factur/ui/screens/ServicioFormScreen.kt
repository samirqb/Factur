package com.samirquiceno.factur.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.samirquiceno.factur.R
import com.samirquiceno.factur.ui.components.TextH1
import com.samirquiceno.factur.ui.forms.ServicioForm
import com.samirquiceno.factur.viewmodels.ServicioViewModel

@Composable
fun ServicioFormScreen(
    navController: NavHostController,
    servicioViewModel : ServicioViewModel,
    servicio_id: String?,
    modifier: Modifier = Modifier,
) {

    LazyColumn(modifier = modifier) {

        /** H E A D E R  -------------------------------------------------------------------------*/
        item {
            TextH1(
                modifier = modifier,
                text = stringResource(R.string.servicio_titulo_form,),
            )
        }

        /** B O D Y  -----------------------------------------------------------------------------*/
        item {

            if (checkNotNull(servicio_id) == " "){
                ServicioForm(
                    navController = navController
                    , servicioViewModel = servicioViewModel
                    , modifier = modifier
                    , servicio_id = "null"
                )
            } else {
                ServicioForm(
                    navController = navController
                    , servicioViewModel = servicioViewModel
                    , modifier = modifier
                    , servicio_id = servicio_id
                )
            }
        }

        /** F O O T E R  -------------------------------------------------------------------------*/
        //item{ Footer(modifier = modifier) }
    }
}