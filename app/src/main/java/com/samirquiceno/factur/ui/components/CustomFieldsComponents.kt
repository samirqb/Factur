package com.samirquiceno.factur.ui.components


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CustomOutlinedTextField(
    @StringRes label: Int
    ,value:String
    ,onValueChange: (String)-> Unit
    ,modifier: Modifier = Modifier
    ,textMaxChar:Int = 29

) {

    OutlinedTextField(
        //value = value.uppercase(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Characters),
        singleLine = true,
        supportingText = {
            Text(
                text = "${value.length} / $textMaxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
    )
}

@Composable
fun CustomOutlinedNumberField(
    @StringRes label: Int
    ,value:String
    ,onValueChange: (String)-> Unit
    ,textMaxChar:Int
    ,modifier: Modifier = Modifier

) {

    OutlinedTextField(
        //value = value.uppercase(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            capitalization = KeyboardCapitalization.Characters),
        singleLine = true,
        supportingText = {
            Text(
                text = "${value.length} / $textMaxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        )
}


@Composable
fun CustomOutlinedCurencyField(
    @StringRes label: Int
    ,value:String
    ,onValueChange: (String)-> Unit
    ,modifier: Modifier = Modifier
    ,textMaxChar:Int = 9
    ,visualTransformation: VisualTransformation = VisualTransformation.None
) {

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            capitalization = KeyboardCapitalization.Characters),
        singleLine = true,

        supportingText = {
            Text(
                text = "${value.length} / $textMaxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },

        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),

        //visualTransformation = MonedaVisualTransformation()
        visualTransformation = visualTransformation
    )
}


@Composable
fun CustomOutlinedEmailField(
    @StringRes label: Int
    ,value:String
    ,onValueChange: (String)-> Unit
    ,modifier: Modifier = Modifier
    ,textMaxChar: Int = 50

) {

    OutlinedTextField(
        //value = value.lowercase(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            capitalization = KeyboardCapitalization.Words),
        singleLine = true,
        supportingText = {
            Text(
                text = "${value.length} / $textMaxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },

        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
    )
}

@Composable
fun CustomOutlinedPhoneField(
    @StringRes label: Int
    ,value:String
    ,onValueChange: (String)-> Unit
    ,modifier: Modifier = Modifier
    ,textMaxChar:Int = 29

) {

    OutlinedTextField(
        //value = value.lowercase(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            capitalization = KeyboardCapitalization.Words),
        singleLine = true,
        supportingText = {
            Text(
                text = "${value.length} / $textMaxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
    )
}

@Composable
fun CustomOutlinedUriField(
    @StringRes label: Int
    ,value:String
    ,onValueChange: (String)-> Unit
    ,modifier: Modifier = Modifier
    ,textMaxChar:Int = 250
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            capitalization = KeyboardCapitalization.Words),
        singleLine = true,
        supportingText = {
            Text(
                text = "${value.length} / $textMaxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),

    )
}



@Composable
fun CustomNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        singleLine = true,
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIcon), null)
        },
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
    )

}