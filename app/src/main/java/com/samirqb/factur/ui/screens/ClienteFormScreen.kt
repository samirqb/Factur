package com.samirqb.factur.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.samirqb.factur.R
import com.samirqb.factur.ui.components.TextH1
import com.samirqb.factur.ui.forms.ClienteForm
import com.samirqb.factur.viewmodels.ClienteViewModel

@Composable
fun ClienteFormScreen(
    navController: NavHostController,
    clienteViewModel : ClienteViewModel,
    modifier: Modifier = Modifier,
) {

    LazyColumn(modifier = modifier) {

        /** H E A D E R  -------------------------------------------------------------------------*/
        item {
            TextH1(
                modifier = modifier,
                text = stringResource(R.string.cliente_titulo_form,),
            )
        }

        /** B O D Y  -----------------------------------------------------------------------------*/
        item {
            ClienteForm(
                navController = navController
                ,clienteViewModel = clienteViewModel
                ,modifier = modifier
            )
        }

        /** F O O T E R  -------------------------------------------------------------------------*/
        //item{ Footer(modifier = modifier) }
    }
}