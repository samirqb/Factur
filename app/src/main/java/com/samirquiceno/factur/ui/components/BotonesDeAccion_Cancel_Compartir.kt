package com.samirquiceno.factur.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.samirquiceno.factur.R


@Composable
fun BotonesDeAccion_Cancel_Compartir(
    onCancelar : ()-> Unit,
    onCompartir : ()-> Unit,
    modifier: Modifier = Modifier,
    enabledCancelButton: Boolean = true,
    enabledGuardarButton: Boolean = true,
){

    Row(modifier = modifier) {

        Column(modifier = modifier.weight(0.5f)) {

            CustomOutlineButton(
                enabled = enabledCancelButton,
                text = R.string.no,
                icono = R.drawable.baseline_cancel_24,
                onClick = onCancelar,
                modifier = modifier.wrapContentWidth(Alignment.Start),
            )
        }



        Column(modifier = modifier.weight(0.5f)) {

            CustomButton(
                enabled = enabledGuardarButton,
                text = R.string.compartir,
                icono = R.drawable.baseline_share_24,
                onClick = onCompartir,
                modifier = modifier.wrapContentWidth(Alignment.End),
            )
        }
    }
}

@Composable
fun BotonesIconosDeAccion_Cancel_Compartir(
    onCancelar : ()-> Unit,
    onCompartir : ()-> Unit,
    modifier: Modifier = Modifier,
    enabledCancelButton: Boolean = true,
    enabledGuardarButton: Boolean = true,
){

    /* botones del formulario proveedor de servicios */
    //LazyVerticalGrid(columns = GridCells.Fixed(2),) {
    //LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), ) {
    Row(modifier = modifier.fillMaxWidth()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.weight(1f).wrapContentWidth(Alignment.End) ) {
            //Column(modifier = modifier) {

            CustomIconButton(
                enabled = enabledCancelButton,
                text = R.string.no,
                icono = R.drawable.baseline_cancel_24,
                onClick = onCancelar,
                modifier = modifier,
            )

            TextP1(
                text = stringResource(id = R.string.no)
                , textAlign = TextAlign.Center
                , modifier = modifier
            )
        }



        Column(modifier = modifier.weight(0.3f).wrapContentWidth(Alignment.End)) {
            //Column(modifier = modifier) {

            CustomIconButton(
                enabled = enabledGuardarButton,
                text = R.string.boton_aceptar,
                icono = R.drawable.baseline_share_24,
                onClick = onCompartir,
                modifier = modifier
            )

            TextP1(
                text = stringResource(id = R.string.compartir)
                , textAlign = TextAlign.Center
                , modifier = modifier
            )
        }
    }
}
