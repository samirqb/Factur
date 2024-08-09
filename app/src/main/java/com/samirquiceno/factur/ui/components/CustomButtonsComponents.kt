package com.samirquiceno.factur.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlineButton(@StringRes text:Int,
                        @DrawableRes icono: Int,
                        enabled: Boolean = true,
                        onClick:()->Unit,
                        modifier:Modifier = Modifier,
                        ) {

    OutlinedButton(
        enabled = enabled,
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),

    ) {
        Text(
            text = stringResource(id = text)
            , modifier = Modifier
        )

        Icon(
            painter = painterResource(id = icono)
            , contentDescription = stringResource(id = text)
            //, modifier = modifier
        )
    }

}


@Composable
fun CustomButton(@StringRes text:Int,
                 @DrawableRes icono: Int,
                 onClick:()->Unit,
                 modifier:Modifier = Modifier,
                 enabled: Boolean = true,){

    Button(
        enabled = enabled
        , onClick = { onClick() }
        , modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp)
    ) {
        Text(
            text = stringResource(id = text)
            , modifier = Modifier
        )
        Icon(
            painter = painterResource(id = icono)
            , contentDescription = stringResource(id = text)
            //, modifier = modifier
        )
    }
}


@Composable
fun CustomFilledTonalButton(@StringRes text:Int,
                            @DrawableRes icono: Int,
                            onClick: () -> Unit,
                            modifier:Modifier = Modifier,
                            enabled: Boolean = true,) {

    FilledTonalButton(
        enabled = enabled,
        onClick = { onClick() }
        , modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp)
    ) {
        Text(
            text = stringResource(id = text)
            //, modifier = modifier
        )
        Icon(
            painter = painterResource(id = icono)
            , contentDescription = stringResource(id = text)
            //, modifier = modifier
        )
    }
}


@Composable
fun CustomIconButton(@StringRes text:Int,
                     @DrawableRes icono: Int,
                     onClick: () -> Unit,
                     modifier:Modifier = Modifier,
                     enabled: Boolean = true,
                     ) {
    IconButton(
        enabled = enabled,
        onClick = { onClick() },
        modifier = modifier,
    ) {

        Icon(painter = painterResource(id = icono)
            , contentDescription = stringResource(id = text)
            , modifier = modifier
        )
    }
}


@Composable
fun CustomIconButton2(
    icono: Unit,
    onClick: () -> Unit,
    modifier:Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        enabled = enabled,
        onClick = {onClick()},
        modifier = modifier,
        content = {
            icono
            //Text(text = "esto es un menu")
        }
    )
}
