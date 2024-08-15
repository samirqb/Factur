package com.samirquiceno.factur.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.compose.onPrimaryContainerDark
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onPrimaryLight
import com.example.compose.primaryContainerDark
import com.example.compose.primaryContainerLight
import com.samirquiceno.factur.R
import com.samirquiceno.factur.models.ImagenCorporativaEntity
import com.samirquiceno.factur.ui.components.CustomIconButton2
import com.samirquiceno.factur.ui.components.CustomOutlineButton
import com.samirquiceno.factur.ui.components.TextH2
import com.samirquiceno.factur.ui.components.TextH3
import com.samirquiceno.factur.viewmodels.ImagenCorporativaViewModel
import kotlinx.coroutines.runBlocking
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CargarImagenScreen(
    navController : NavHostController,
    //cuentaDeCobroViewModel : CuentaDeCobroViewModel,
    imagenCorporativaViewModel: ImagenCorporativaViewModel,
    modifier : Modifier = Modifier) {

    val context = LocalContext.current

    val NOMBRE_IMAGEN_CORPORATIVA = "imagen_corporativa"

    var _imagen_corp_uri_nueva by rememberSaveable { mutableStateOf<Uri?>(Uri.EMPTY) }
    //val imagen_corp_uri_actual_xxx by rememberSaveable { mutableStateOf<Uri?>(cuentaDeCobroViewModel.readImagen(context,"imagen_corporativa")) }
    val imagen_corp_uri_actual by rememberSaveable { mutableStateOf<Uri?>(
        imagenCorporativaViewModel.read(
            entity = ImagenCorporativaEntity(
                context = context,
                nombre = NOMBRE_IMAGEN_CORPORATIVA
            )
        )?.uri)
    }



    var  pickerMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri? ->

        try {

            val imagenBytes = context.contentResolver.openInputStream(uri!!)
            val imagen_bitmap = BitmapFactory.decodeStream(imagenBytes)
            val alto = imagen_bitmap.height
            val ancho = imagen_bitmap.width

            if (alto == 100 && ancho == 140) {
                /** guardado de mi imagen */
                runBlocking {

                    /*
                    cuentaDeCobroViewModel
                        .insertarImagen(context = context, nombre_imagen = "imagen_corporativa",uri = uri!!)
                    _imagen_corp_uri_nueva = uri
                    */

                    imagenCorporativaViewModel.insert(
                        entity = ImagenCorporativaEntity(
                            context = context,
                            nombre = NOMBRE_IMAGEN_CORPORATIVA,
                            uri = uri
                        )
                    )



                } ?: throw Exception("URI nula")
            } else {
                Toast.makeText(context,"La imagen seleccionada no tiene las dimensiones requeridas",
                    Toast.LENGTH_LONG).show()
            }


        } catch (e:Exception){
            Log.e("_xxx","e: ${e}")
            Toast.makeText(context,"Error: Debe seleccionar una imagen", Toast.LENGTH_LONG).show()
        } catch (ioe: IOException){
            Log.e("_xxx","ioe: ${ioe}")
            Toast.makeText(context,"IOError: Debe seleccionar una imagen", Toast.LENGTH_LONG).show()
        }
    }

    var darkTheme: Boolean = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                //TopAppBar(
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
                        text =  stringResource(id = R.string.imagen_corporativa)
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
                                    //Toast.makeText(context,"Boton de Menu OK!",Toast.LENGTH_SHORT).show()
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
            item {
                TextH2 (
                    modifier = modifier,
                    text = stringResource(id = R.string.imagen_corporativa)
                )
            }
            */

            item {
                /*
                if (imagen_corp_uri_actual != _imagen_corp_uri_nueva){

                    AsyncImage(
                        model = runBlocking {
                            cuentaDeCobroViewModel
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize().size(10.dp)
                    )

                    _imagen_corp_uri_nueva = imagen_corp_uri_actual

                } else {

                    AsyncImage(
                        model = runBlocking{
                            cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                }
                */

                if (imagen_corp_uri_actual != _imagen_corp_uri_nueva){

                    _imagen_corp_uri_nueva = imagen_corp_uri_actual

                }

                AsyncImage(
                    model = runBlocking{
                        //cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value
                        imagenCorporativaViewModel.imagenCorporativaDataState.value.imagen_corporativa.value
                    },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )

                TextH3(
                    modifier = modifier,
                    text = stringResource(id = R.string.buscar_imagen_info)
                )
            }

            item {

                /** Botonera de seleccion y guardado de imagen corporativa */
                CustomOutlineButton(
                    modifier = modifier,
                    text = R.string.buscar_imagen,
                    icono = R.drawable.baseline_image_search_24,
                    onClick = {
                        try {
                            pickerMedia.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        } catch (e:Exception){
                            Toast.makeText(context,"Debe seleccionar una imagen",Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            /*
            item {
                CargarImagenIntent(
                    navController = navController,
                    cuentaDeCobroViewModel = cuentaDeCobroViewModel,
                    modifier = modifier
                )
            }
            */
        }

        /*
        CargarImagenIntent(
            navController = navController,
            cuentaDeCobroViewModel = cuentaDeCobroViewModel,
            modifier = modifier)

         */
    }
}
