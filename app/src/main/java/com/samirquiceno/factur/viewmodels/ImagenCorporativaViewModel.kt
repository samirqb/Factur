package com.samirquiceno.factur.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samirquiceno.factur.mApplication
import com.samirquiceno.factur.models.ImagenCorporativaEntity
import com.samirquiceno.factur.repositories.ImagenCorporativaRepository
import com.samirquiceno.factur.ui.states.ImagenCorporativaDataState
import com.samirquiceno.factur.viewmodels.interfaces.IBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ImagenCorporativaViewModel(
    application: Application,
    private var _mImagenCorporativaRepository: ImagenCorporativaRepository
) : AndroidViewModel(application), IBaseViewModel<ImagenCorporativaEntity> {

    /** ImagenCorporativaDataState State */
    private val _imagenCorporativaDataState = MutableStateFlow(ImagenCorporativaDataState())
    val imagenCorporativaDataState: StateFlow<ImagenCorporativaDataState> = _imagenCorporativaDataState.asStateFlow()


    val context = getApplication<mApplication>().applicationContext


    val NOMBRE_IMAGEN_CORPORATIVA = "imagen_corporativa"

    /** init{...}
     * este bloque de codigo solo se inicia cuado la app arranca desde cero onCreate,
     * nunca se va a ejecutar cuando se navega dentro de la app, se pausa y  salga de estado pausa */
    init {

        updateImagenStatusFromRepo(
            read(entity = ImagenCorporativaEntity(
                context = context,
                nombre = NOMBRE_IMAGEN_CORPORATIVA
                )
            )
        )
    }


    override suspend fun insert(entity: ImagenCorporativaEntity) {

        _mImagenCorporativaRepository.insert(entity)

        _imagenCorporativaDataState.update {
            it.imagen_corporativa = mutableStateOf(read(entity)?.uri)
            it.copy(
                imagen_corporativa = _imagenCorporativaDataState.value.imagen_corporativa,
            )
        }

    }

    override suspend fun update(entity: ImagenCorporativaEntity) {

        TODO("Not yet implemented")

    }

    override fun read(id: String): LiveData<ImagenCorporativaEntity?> {

        TODO("Not yet implemented")

    }
    fun read(entity: ImagenCorporativaEntity): ImagenCorporativaEntity? {

        val imagenCorporativaEntity = _mImagenCorporativaRepository.read(entity)
        return imagenCorporativaEntity

    }

    override fun readAll(): LiveData<ArrayList<ImagenCorporativaEntity>> {

        TODO("Not yet implemented")

    }

    override suspend fun eliminar(entity: ImagenCorporativaEntity) {

        TODO("Not yet implemented")

    }

    //fun updateImagenStatusFromRepo(entity : LiveData<ImagenCorporativaEntity?>){
    fun updateImagenStatusFromRepo(entity : ImagenCorporativaEntity?){

        var uri = entity?.uri

        _imagenCorporativaDataState.update {
            //it.imagen_corporativa = MutableLiveData(uri)
            it.imagen_corporativa = mutableStateOf(uri)
            it.copy(
                imagen_corporativa = _imagenCorporativaDataState.value.imagen_corporativa,
            )
        }

    }


    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val imagen_corporativa_repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mImagenCorporativaRepository

                ImagenCorporativaViewModel(
                    application = application,
                    _mImagenCorporativaRepository = imagen_corporativa_repository
                )

            }

        }

    }

}