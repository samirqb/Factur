package com.samirquiceno.factur.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/* TAMAÑO FUENTES DE TITULOS */
var H1 = 37.sp
var H2 = 29.sp
var H3 = 17.sp

/* TAMAÑO FUENTES DE PARRAFOS */
var P1 = 12.sp

/* TAMAÑO ESPACIOS ENTRE LINEAS Y LETRAS */
var letterSpacing = 0.07.em
var lineHeight = 1.05.em


@Composable
fun TextH1(text: String, textAlign:TextAlign? = null, modifier: Modifier = Modifier) {
    Text(
        text = "$text",
        fontSize = H1,
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        modifier = modifier,
    )
}

@Composable
fun TextH2(text: String, color: Color = Color.Unspecified, textAlign:TextAlign? = null, modifier: Modifier = Modifier) {
    Text(
        text = "$text",
        color = color,
        fontSize = H2,
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        modifier = modifier,
    )
}

@Composable
fun TextH3(
    text: String
    , color: Color = Color.Unspecified
    , textAlign:TextAlign? = null
    , modifier: Modifier = Modifier) {
    Text(
        text = "$text",
        color = color,
        fontSize = H3,
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        modifier = modifier,
    )
}

@Composable
fun TextP1(text: String, textAlign:TextAlign? = null, modifier: Modifier = Modifier) {
    Text(
        text = "$text",
        //fontFamily = FontFamily.Monospace,
        fontSize = P1,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        modifier = modifier,
    )
}