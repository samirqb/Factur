package com.samirqb.factur.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onTertiaryContainerDark
import com.example.compose.onTertiaryContainerLight
import com.example.compose.secondaryDark
import com.samirqb.factur.R


@OptIn(ExperimentalMaterial3Api::class)
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

        var darkTheme: Boolean = isSystemInDarkTheme()

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(

                //containerColor = if (darkTheme) colorScheme.primaryContainer
                containerColor = if (darkTheme) colorScheme.primaryContainer
                else colorScheme.primary,

                titleContentColor = if (darkTheme) onPrimaryContainerDark
                else onPrimaryContainerLight,
            ),

            title = { titulo() }
        )

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //titulo()
            content()
        }
    }
}

@Composable
fun CustomCardComponentV2(
    onClick: ()-> Unit,
    titulo: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
){

    var darkTheme: Boolean = isSystemInDarkTheme()

    Card(
        colors = CardDefaults.cardColors(
            //containerColor = if (darkTheme) onTertiaryContainerLight
            //containerColor = if (darkTheme) onTertiaryDark
            containerColor = if (darkTheme) onTertiaryContainerDark
            else onTertiaryContainerLight,
            contentColor =  contentColorFor(containerColor)
        )

        ,modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)

        ,shape = RoundedCornerShape(7.dp)
    ) {
        Row(modifier = modifier) {
            Icon(modifier = modifier.fillMaxHeight().align(Alignment.CenterVertically).size(50.dp), painter = painterResource(id = R.drawable.baseline_task_alt_24), contentDescription = "", tint = secondaryDark)
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                titulo()
                content()
            }
        }
        /*
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            titulo()
            content()
        }
        */
    }
}

@Composable
fun CustomCardComponentV3(
    onClick: ()-> Unit,
    titulo: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
){

    var darkTheme: Boolean = isSystemInDarkTheme()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (darkTheme) onTertiaryContainerDark
            else onTertiaryContainerLight,
            contentColor =  contentColorFor(containerColor)
        )

        ,modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)

        ,shape = RoundedCornerShape(7.dp)
    ) {
        Row(modifier = modifier) {
            Icon(
                modifier = modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically).size(50.dp),
                painter = painterResource(id = R.drawable.baseline_info_24),
                contentDescription = "", tint = secondaryDark)
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                titulo()
                content()
            }
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
            defaultElevation =3.dp
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
