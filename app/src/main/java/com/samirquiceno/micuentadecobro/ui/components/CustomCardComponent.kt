package com.samirquiceno.micuentadecobro.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.samirquiceno.micuentadecobro.R


@Composable
fun CustomCardComponent(
    onClick: ()-> Unit,
    titulo: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
){

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purpura_transparente)
            //,contentColor =
        )

        ,modifier = modifier
            .fillMaxWidth(1f)
            .clickable(onClick = onClick)

        ,shape = RoundedCornerShape(7.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            titulo()
            content()
        }
    }

}

@Composable
fun CustomCardSmallComponent(
    onClick: ()-> Unit,
    titulo: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
){

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
            , contentColor =  contentColorFor(containerColor),
        )

        ,modifier = modifier
            .fillMaxWidth()
            //.height(400.dp)
            //.clickable(onClick = onClick)

        ,shape = RoundedCornerShape(7.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            titulo()
            content()
        }
    }

}

@Composable
fun CustomElevatedCardComponent(
    onClick: ()-> Unit,
    titulo: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
){

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = .51.dp
        )
        ,colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purpura_transparente)
            //,contentColor =
        )

        ,modifier = modifier
            .fillMaxWidth(1f)
            .clickable(onClick = onClick)
            //.size(width = 240.dp, height = 100.dp)


        ,shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            titulo()
            content()
        }
    }
}

