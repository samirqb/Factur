package com.samirquiceno.micuentadecobro.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.samirquiceno.micuentadecobro.R
import com.samirquiceno.micuentadecobro.ui.components.TextH1
import com.samirquiceno.micuentadecobro.ui.forms.ProveedorServiciosForm
import com.samirquiceno.micuentadecobro.viewmodels.ProveedorServiciosViewModel

@Composable
fun ProveedorServiciosFormScreen(
    navController: NavHostController,
    proveedorServiciosViewModel : ProveedorServiciosViewModel,
    modifier: Modifier = Modifier,
) {

    LazyColumn(modifier = modifier) {

        /** H E A D E R  -------------------------------------------------------------------------*/
        item {
            TextH1(
                modifier = modifier,
                text = stringResource(R.string.proovedor_servicio_titulo_form,),
            )
        }

        /** B O D Y  -----------------------------------------------------------------------------*/
        item {
            ProveedorServiciosForm(
                navController = navController
                ,proveedorServiciosViewModel = proveedorServiciosViewModel
                ,modifier = modifier
            )
        }

        /** F O O T E R  -------------------------------------------------------------------------*/
        //item{ Footer(modifier = modifier) }
    }
}



/*** V I S T A   P R E V I A ***/
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProveedorServiciosFormScreenPreview(){
    //ProveedorServiciosFormScreenRoute({})
}
