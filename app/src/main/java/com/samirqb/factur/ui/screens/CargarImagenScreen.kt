package com.samirqb.factur.ui.screens

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
import com.samirqb.factur.R
import com.samirqb.factur.models.ImagenCorporativaEntity
import com.samirqb.factur.ui.components.CustomIconButton2
import com.samirqb.factur.ui.components.CustomOutlineButton
import com.samirqb.factur.ui.components.TextH2
import com.samirqb.factur.ui.components.TextH3
import com.samirqb.factur.viewmodels.ImagenCorporativaViewModel
import kotlinx.coroutines.runBlocking
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CargarImagenScreen(
    navController : NavHostController,
    imagenCorporativaViewModel: ImagenCorporativaViewModel,
    modifier : Modifier = Modifier) {

    val context = LocalContext.current

    val NOMBRE_IMAGEN_CORPORATIVA = "imagen_corporativa"

    var _imagen_corp_uri_nueva by rememberSaveable { mutableStateOf<Uri?>( Uri.EMPTY ) }

    /** var imagen_corp_uri_actual
     * esta variable/estado toma el valor URI de la imagen que exista en el directorio interno de la app
     * o en su defecto, si nunca se a cargado uns imagen, tomara la URI de una imagen pre-establecica
     * en el directorio Resourses */
    val imagen_corp_uri_actual by rememberSaveable {
        mutableStateOf(
            imagenCorporativaViewModel.read(
                entity = ImagenCorporativaEntity(
                    context = context,
                    nombre = NOMBRE_IMAGEN_CORPORATIVA
                )
            )?.uri
        )
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

                    imagenCorporativaViewModel.insert(
                        entity = ImagenCorporativaEntity(
                            context = context,
                            nombre = NOMBRE_IMAGEN_CORPORATIVA,
                            uri = uri
                        )
                    )

                    /** si la imagen se actualiza, aqui se anctualiza la nueva URI
                     *  y se actualiza el nuevo estado en la variable _imagen_corp_uri_nueva */
                    _imagen_corp_uri_nueva = uri

                } ?: throw Exception("URI nula")
            } else {
                Toast.makeText(
                    context,
                    "La imagen seleccionada no tiene las dimensiones requeridas",
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

            item {

                /** si detecta una diferencia entrw (_imagen_corp_uri_nueva) y (imagen_corp_uri_actual)
                 * las variables/estados, se igualan para generar una recomposicion de la IU y que se
                 * puede leer en tiempo real la nueva imagen */
                if (imagen_corp_uri_actual != _imagen_corp_uri_nueva){

                    _imagen_corp_uri_nueva = imagen_corp_uri_actual

                } else {

                    AsyncImage(
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize(),
                        model = runBlocking{
                            imagenCorporativaViewModel
                                .imagenCorporativaDataState
                                .value
                                .imagen_corporativa
                                .value
                        },
                    )
                }

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
        }
    }
}
