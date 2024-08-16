package com.samirquiceno.factur.ui.intents

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.samirquiceno.factur.R
import com.samirquiceno.factur.models.ImagenCorporativaEntity
import com.samirquiceno.factur.ui.components.CustomCardComponent
import com.samirquiceno.factur.ui.components.CustomOutlineButton
import com.samirquiceno.factur.ui.components.TextH2
import com.samirquiceno.factur.ui.components.TextP1
import com.samirquiceno.factur.viewmodels.ImagenCorporativaViewModel
import kotlinx.coroutines.runBlocking
import java.io.IOException

@Composable
fun CargarImagenIntent(navController:NavHostController,
                       //cuentaDeCobroViewModel: CuentaDeCobroViewModel,
                       imagenCorporativaViewModel: ImagenCorporativaViewModel,
                       modifier: Modifier = Modifier ) {

    val context = LocalContext.current

    val NOMBRE_IMAGEN_CORPORATIVA = "imagen_corporativa"

    var _imagen_corp_uri_nueva by rememberSaveable { mutableStateOf<Uri?>(Uri.EMPTY) }
    //val imagen_corp_uri_actual by rememberSaveable { mutableStateOf<Uri?>(cuentaDeCobroViewModel.readImagen(context,"imagen_corporativa")) }
    val imagen_corp_uri_actual by rememberSaveable { mutableStateOf<Uri?>(imagenCorporativaViewModel
        .read(
            entity = ImagenCorporativaEntity(
                context = context,
                nombre = NOMBRE_IMAGEN_CORPORATIVA
            )
        )?.uri)
    }

    var  pickerMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri:Uri? ->

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
                    */

                    imagenCorporativaViewModel.insert(
                        entity = ImagenCorporativaEntity(
                            context = context,
                            nombre = NOMBRE_IMAGEN_CORPORATIVA,
                            uri = uri
                        )
                    )

                    _imagen_corp_uri_nueva = uri
                } ?: throw Exception("URI nula")
            } else {
                Toast.makeText( context, "La imagen seleccionada no tiene las dimensiones requeridas",
                    Toast.LENGTH_LONG ).show()
            }


        } catch (e:Exception){
            Log.e("_xxx","e: ${e}")
            Toast.makeText(context,"Error: Debe seleccionar una imagen",Toast.LENGTH_LONG).show()
        } catch (ioe: IOException){
            Log.e("_xxx","ioe: ${ioe}")
            Toast.makeText(context,"IOError: Debe seleccionar una imagen",Toast.LENGTH_LONG).show()
        }
    }


    CustomCardComponent(
        titulo = {

            TextH2 (
                modifier = modifier,
                text = stringResource(id = R.string.imagen_corporativa)
            )
        },

        content = {

            //ini-column
            Column(modifier = modifier) {

                if (imagen_corp_uri_actual != _imagen_corp_uri_nueva){

                    /*
                    AsyncImage(
                        model = runBlocking {
                            cuentaDeCobroViewModel
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                    */

                    _imagen_corp_uri_nueva = imagen_corp_uri_actual

                } else {

                    /*
                    AsyncImage(
                        model = runBlocking{
                            cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                    */

                    AsyncImage(
                        model = runBlocking{
                            //cuentaDeCobroViewModel.cuentaDeCobroDataState.value.imagen_corporativa.value
                            imagenCorporativaViewModel.imagenCorporativaDataState.value.imagen_corporativa.value
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                }


                TextP1(
                    modifier = modifier,
                    text = stringResource(id = R.string.buscar_imagen_info)
                )


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
            } // fin-column
        },

        modifier = modifier,

        onClick = { }

    )
}
