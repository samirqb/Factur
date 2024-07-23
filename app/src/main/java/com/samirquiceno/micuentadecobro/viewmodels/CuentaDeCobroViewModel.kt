package com.samirquiceno.micuentadecobro.viewmodels

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
import com.samirquiceno.micuentadecobro.mApplication
import com.samirquiceno.micuentadecobro.models.CuentaDeCobroContadorEntity
import com.samirquiceno.micuentadecobro.models.CuentaDeCobroEntity
import com.samirquiceno.micuentadecobro.repositories.ImagenRepository
import com.samirquiceno.micuentadecobro.repositories.interfaces.IBaseRepository
import com.samirquiceno.micuentadecobro.tools.FechaHoraSistema
import com.samirquiceno.micuentadecobro.tools.GenerarPdf
import com.samirquiceno.micuentadecobro.ui.states.CuentaDeCobroDataState
import com.samirquiceno.micuentadecobro.ui.states.CuentaDeCobroUiState
import com.samirquiceno.micuentadecobro.viewmodels.interfaces.IBaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CuentaDeCobroViewModel(
    application: Application,
    private var _repository: IBaseRepository<CuentaDeCobroContadorEntity>,
    private var _imagen_repository: ImagenRepository
): AndroidViewModel(application), IBaseViewModel<CuentaDeCobroContadorEntity> {

    //lateinit var pdf_generado_uri: Uri
    var pdf_generado_uri: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)

    val context = getApplication<mApplication>().applicationContext


    /** CuentaDeCobroDataState State */
    private val _cuentaDeCobroDataState = MutableStateFlow(CuentaDeCobroDataState())
    val cuentaDeCobroDataState: StateFlow<CuentaDeCobroDataState> = _cuentaDeCobroDataState.asStateFlow()


    /** CuentaDeCobroDataState State */
    private val _cuentaDeCobroUiState = MutableStateFlow(CuentaDeCobroUiState())
    val cuentaDeCobroUiState: StateFlow<CuentaDeCobroUiState> = _cuentaDeCobroUiState.asStateFlow()


    init {
        actualizarFechaHoraSistema()
        updateImagenStatusFromRepo(
            readImagen(context = context, nombre_imagen = "imagen_corporativa")
        )
    }


    override suspend fun insert(entity: CuentaDeCobroContadorEntity) {
        _repository.insert(entity)
    }


    override suspend fun update(entity: CuentaDeCobroContadorEntity) {
        _repository.update(entity)
    }


    override fun read(id: String): LiveData<CuentaDeCobroContadorEntity?> =
        _repository.read(id).asLiveData()


    override fun readAll(): LiveData<ArrayList<CuentaDeCobroContadorEntity>> {
        TODO("Not yet implemented")
    }


    override suspend fun eliminar(entity: CuentaDeCobroContadorEntity) {
        TODO("Not yet implemented")
    }


    fun incrementarContadorDocsEmitidos(cuentaDeCobroContadorEntity: State<CuentaDeCobroContadorEntity?>){

        val nuevo_valor = cuentaDeCobroContadorEntity.value?.contador?.plus(1)

        runBlocking {
            insert(CuentaDeCobroContadorEntity(contador = nuevo_valor!!))
        }

        _cuentaDeCobroDataState.update {
            it.cuenta_de_cobro_numero = MutableLiveData(it.cuenta_de_cobro_numero?.value?.plus(1))
            it.copy(
                cuenta_de_cobro_numero = _cuentaDeCobroDataState.value.cuenta_de_cobro_numero
            )
        }
    }


    fun actualizarContadorDocsEmitidos(cuentaDeCobroContadorEntity: State<CuentaDeCobroContadorEntity?>){

        _cuentaDeCobroDataState.update {currentState ->
            currentState.cuenta_de_cobro_numero = MutableLiveData(
                if (cuentaDeCobroContadorEntity.value?.contador != null) cuentaDeCobroContadorEntity.value?.contador else 0
            )
            currentState.copy(
                cuenta_de_cobro_numero = _cuentaDeCobroDataState.value.cuenta_de_cobro_numero
            )
        }
    }


    fun actualizarFechaHoraSistema(){
        _cuentaDeCobroDataState.update {
            it.cuenta_de_cobro_fecha_expedicion = mutableStateOf(
                FechaHoraSistema().obtenerFecha()
            )

            it.cuenta_de_cobro_hora_expedicion = mutableStateOf(
                FechaHoraSistema().obtenerHora()
            )

            it.copy(
                cuenta_de_cobro_fecha_expedicion = _cuentaDeCobroDataState.value.cuenta_de_cobro_fecha_expedicion,
                cuenta_de_cobro_hora_expedicion = _cuentaDeCobroDataState.value.cuenta_de_cobro_hora_expedicion
            )
        }

    }


    suspend fun insertarImagen(context: Context, nombre_imagen:String, uri: Uri){

        _imagen_repository.insert(context = context, nombre_imagen= nombre_imagen, uri=uri)

        _cuentaDeCobroDataState.update {
            it.imagen_corporativa = mutableStateOf(readImagen(context = context, nombre_imagen= nombre_imagen))
            it.copy(
                imagen_corporativa = _cuentaDeCobroDataState.value.imagen_corporativa,
            )
        }
    }


    fun updateImagenStatusFromRepo(uri:Uri?){

        _cuentaDeCobroDataState.update {
            it.imagen_corporativa = mutableStateOf(uri)
            it.copy(
                imagen_corporativa = _cuentaDeCobroDataState.value.imagen_corporativa,
            )
        }
    }


    fun readImagen(context: Context, nombre_imagen:String):Uri?{

        val uri = _imagen_repository.read(context = context, nombre_imagen= nombre_imagen)

        return uri
    }


    fun generarPDF(
        context: Context
        ,mCuentaDeCobroEntity:CuentaDeCobroEntity
    ){
        viewModelScope.launch(Dispatchers.IO) {
            var pdf = GenerarPdf(mCuentaDeCobroEntity = mCuentaDeCobroEntity , context = context)
            pdf.generar()

            //pdf_generado_uri.value = pdf.pdf_generado_uri
            pdf_generado_uri = mutableStateOf(pdf.pdf_generado_uri)
        }
    }

    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mCuentaDeCobroRepository
                val imagen_repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mImagenRepository

                CuentaDeCobroViewModel(
                    application = application,
                    _repository = repository,
                    _imagen_repository = imagen_repository
                )
            }
        }
    }
}
