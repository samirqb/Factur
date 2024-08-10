package com.samirquiceno.factur.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerDark
import com.example.compose.primaryContainerLight
import com.samirquiceno.factur.R
import com.samirquiceno.factur.ui.components.CustomIconButton2
import com.samirquiceno.factur.ui.components.TextH2
import com.samirquiceno.factur.ui.forms.ProveedorServiciosForm
import com.samirquiceno.factur.viewmodels.ProveedorServiciosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProveedorServiciosFormScreen(
    navController: NavHostController,
    proveedorServiciosViewModel : ProveedorServiciosViewModel,
    modifier: Modifier = Modifier,
) {

    var darkTheme: Boolean = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = if (darkTheme) primaryContainerDark
                    else primaryContainerLight,

                    titleContentColor = if (darkTheme) onPrimaryContainerDark
                    else onPrimaryContainerLight,
                ),
                title = {

                    /** titulo - Cuenta de Cobro */
                    TextH2(
                        modifier = modifier,
                        //text =  stringResource(id = R.string.imagen_corporativa)
                        text =  stringResource(id = R.string.proovedor_servicio_titulo_form)
                    )
                },

                navigationIcon = {
                    Surface(
                        color = primaryContainerDark,
                        modifier = Modifier
                            .fillMaxHeight(.1f)
                            .fillMaxWidth(.11f),
                        content = {

                            CustomIconButton2(
                                icono = Icon(
                                    tint = onPrimaryLight,
                                    modifier = Modifier.size(30.dp),
                                    painter = painterResource(id = R.drawable.baseline_arrow_back_24 ),
                                    contentDescription = null),
                                onClick = {

                                    //navController.popBackStack()
                                    navController.navigateUp()

                                }
                            )

                        }
                    )
                }
            )
        },
        //modifier = modifier
    ) { innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(77.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(innerPadding).fillMaxSize()) {

            /*
            /** H E A D E R  -------------------------------------------------------------------------*/
            item {
                TextH1(
                    modifier = modifier,
                    text = stringResource(R.string.proovedor_servicio_titulo_form,)
                    )
            }
            */

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
}
