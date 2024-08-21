package com.samirqb.factur.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerDark
import com.example.compose.primaryContainerLight
import com.samirqb.factur.R
import com.samirqb.factur.ui.components.CustomIconButton2
import com.samirqb.factur.ui.components.TextH2
import com.samirqb.factur.ui.components.TextH3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcercaDeScreen(
    navController: NavHostController,
    modifier : Modifier = Modifier
){

    val darkTheme: Boolean = isSystemInDarkTheme()

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
                        text = "${ stringResource(id = R.string.acerca_titulo) } ${ stringResource(id = R.string.app_name) }"
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
                                    navController.navigateUp()
                                },
                            )
                        }
                    )
                }
            )
        },

        content = {

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(77.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(it)
                    .fillMaxSize(1f)
            ){

                item {
                    Image(
                        modifier = Modifier.size(240.dp),
                        painter = painterResource(id = R.drawable.factur_arte_isologotipo_140px_x_100px),
                        contentDescription = "Imagen corporativa")
                }

                item {

                    TextH3(
                        modifier = modifier,
                        text = stringResource(id = R.string.acerca_text_a)
                    )

                    TextH3(
                        modifier = modifier,
                        text = stringResource(id = R.string.acerca_text_b)
                    )
                }
            } // fin-lazyColumn
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AcercaDeScreenPreview(){
    AcercaDeScreen(
        navController = NavHostController(context = LocalContext.current)
    )
}