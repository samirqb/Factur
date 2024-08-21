package com.samirqb.factur.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samirqb.factur.mApplication
import com.samirqb.factur.models.FacturaContadorEntity
import com.samirqb.factur.models.FacturaEntity
import com.samirqb.factur.repositories.ImagenCorporativaRepository
import com.samirqb.factur.repositories.interfaces.IBaseRepository
import com.samirqb.factur.tools.FechaHoraSistema
import com.samirqb.factur.tools.GenerarFacturaPDF_FV2
import com.samirqb.factur.ui.states.FacturaDataState
import com.samirqb.factur.viewmodels.interfaces.IBaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FacturaViewModel(
    application: Application,
    private var _repository: IBaseRepository<FacturaContadorEntity>,
    //private var _imagen_corporativa_repository: ImagenRepository
    private var _imagen_corporativa_repository: ImagenCorporativaRepository
): AndroidViewModel(application), IBaseViewModel<FacturaContadorEntity> {

    //lateinit var pdf_generado_uri: Uri
    var pdf_generado_uri: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)

    val context = getApplication<mApplication>().applicationContext


    /** CuentaDeCobroDataState State */
    private val _facturaDataState = MutableStateFlow(FacturaDataState())
    val facturaDataState: StateFlow<FacturaDataState> = _facturaDataState.asStateFlow()


    /*
    /** CuentaDeCobroDataState State */
    private val _facturaUiState = MutableStateFlow(FacturaUiState())
    val facturaUiState: StateFlow<FacturaUiState> = _facturaUiState.asStateFlow()
    */

    init {

        actualizarFechaHoraSistema()

        /*
        updateImagenStatusFromRepo(
            readImagen(context = context, nombre_imagen = "imagen_corporativa")
        )
        */

    }


    override suspend fun insert(entity: FacturaContadorEntity) {
        _repository.insert(entity)
    }


    override suspend fun update(entity: FacturaContadorEntity) {
        _repository.update(entity)
    }


    override fun read(id: String): LiveData<FacturaContadorEntity?> =
        _repository.read(id).asLiveData()


    override fun readAll(): LiveData<ArrayList<FacturaContadorEntity>> {
        TODO("Not yet implemented")
    }


    override suspend fun eliminar(entity: FacturaContadorEntity) {
        TODO("Not yet implemented")
    }


    fun incrementarContadorDocsEmitidos(facturaContadorEntity: State<FacturaContadorEntity?>){

        val nuevo_valor = facturaContadorEntity.value?.contador?.plus(1)

        runBlocking {
            insert(FacturaContadorEntity(contador = nuevo_valor!!))
        }

        _facturaDataState.update {
            it.factura_numero = MutableLiveData(it.factura_numero?.value?.plus(1))
            it.copy(
                factura_numero = _facturaDataState.value.factura_numero
            )
        }
    }


    fun actualizarContadorDocsEmitidos(facturaContadorEntity: State<FacturaContadorEntity?>){

        _facturaDataState.update {currentState ->
            currentState.factura_numero = MutableLiveData(
                if (facturaContadorEntity.value?.contador != null) facturaContadorEntity.value?.contador else 0
            )
            currentState.copy(
                factura_numero = _facturaDataState.value.factura_numero
            )
        }
    }


    fun actualizarFechaHoraSistema(){
        _facturaDataState.update {
            it.factura_fecha_expedicion = mutableStateOf(
                FechaHoraSistema().obtenerFecha()
            )

            it.factura_hora_expedicion = mutableStateOf(
                FechaHoraSistema().obtenerHora()
            )

            it.copy(
                factura_fecha_expedicion = _facturaDataState.value.factura_fecha_expedicion,
                factura_hora_expedicion = _facturaDataState.value.factura_hora_expedicion
            )
        }
    }


    /*
    suspend fun insertarImagen(context: Context, nombre_imagen:String, uri: Uri){

        _imagen_corporativa_repository.insert(context = context, nombre_imagen= nombre_imagen, uri=uri)

        _facturaDataState.update {
            it.imagen_corporativa = mutableStateOf(readImagen(context = context, nombre_imagen= nombre_imagen))
            it.copy(
                imagen_corporativa = _facturaDataState.value.imagen_corporativa,
            )
        }
    }
    */

    fun updateImagenStatusFromRepo(uri: Uri?){

        _facturaDataState.update {
            it.imagen_corporativa = mutableStateOf(uri)
            it.copy(
                imagen_corporativa = _facturaDataState.value.imagen_corporativa,
            )
        }
    }


    /*
    fun readImagen(context: Context, nombre_imagen:String): Uri?{

        val uri = _imagen_corporativa_repository.read(context = context, nombre_imagen= nombre_imagen)

        return uri
    }
    */


    fun generarFacturaPDF(
        context: Context
        ,mFacturaEntity: FacturaEntity
    ){
        viewModelScope.launch(Dispatchers.IO) {
            //var pdf = GenerarFacturaPDF(mFacturaEntity = mFacturaEntity , context = context)
            var pdf = GenerarFacturaPDF_FV2(mFacturaEntity = mFacturaEntity , context = context)
            pdf.generar()

            //pdf_generado_uri.value = pdf.pdf_generado_uri
            pdf_generado_uri = mutableStateOf(pdf.pdf_generado_uri)
        }
    }

    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mFacturaRepository
                val imagen_corporativa_repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mImagenCorporativaRepository

                FacturaViewModel(
                    application = application,
                    _repository = repository,
                    _imagen_corporativa_repository = imagen_corporativa_repository
                )
            }
        }
    }
}
