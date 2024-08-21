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
import com.samirqb.factur.models.CuentaDeCobroContadorEntity
import com.samirqb.factur.models.CuentaDeCobroEntity
import com.samirqb.factur.repositories.ImagenCorporativaRepository
import com.samirqb.factur.repositories.interfaces.IBaseRepository
import com.samirqb.factur.tools.FechaHoraSistema
import com.samirqb.factur.tools.GenerarCuentaDeCobroPDF_FV2
import com.samirqb.factur.ui.states.CuentaDeCobroDataState
import com.samirqb.factur.ui.states.CuentaDeCobroUiState
import com.samirqb.factur.viewmodels.interfaces.IBaseViewModel
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
    private var _imagen_corporativa_repository: ImagenCorporativaRepository
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

    fun updateImagenStatusFromRepo(uri:Uri?){

        _cuentaDeCobroDataState.update {
            it.imagen_corporativa = mutableStateOf(uri)
            it.copy(
                imagen_corporativa = _cuentaDeCobroDataState.value.imagen_corporativa,
            )
        }
    }


    fun generarPDF(
        context: Context
        ,mCuentaDeCobroEntity:CuentaDeCobroEntity
    ){
        viewModelScope.launch(Dispatchers.IO) {
            var pdf = GenerarCuentaDeCobroPDF_FV2(mCuentaDeCobroEntity = mCuentaDeCobroEntity , context = context)
            pdf.generar()

            pdf_generado_uri = mutableStateOf(pdf.pdf_generado_uri)
        }
    }

    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mCuentaDeCobroRepository
                val imagen_corporativa_repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mImagenCorporativaRepository

                CuentaDeCobroViewModel(
                    application = application,
                    _repository = repository,
                    _imagen_corporativa_repository = imagen_corporativa_repository
                )
            }
        }
    }
}
