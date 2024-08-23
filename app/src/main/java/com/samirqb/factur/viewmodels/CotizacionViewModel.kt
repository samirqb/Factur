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
import com.samirqb.factur.models.CotizacionContadorEntity
import com.samirqb.factur.models.CotizacionEntity
import com.samirqb.factur.repositories.interfaces.IBaseRepository
import com.samirqb.factur.tools.FechaHoraSistema
import com.samirqb.factur.tools.GenerarCotizacionPDF_FV2
import com.samirqb.factur.ui.states.CotizacionDataState
import com.samirqb.factur.ui.states.CotizacionUiState
import com.samirqb.factur.viewmodels.interfaces.IBaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CotizacionViewModel(
    application: Application,
    private var _repository: IBaseRepository<CotizacionContadorEntity>,
): AndroidViewModel(application), IBaseViewModel<CotizacionContadorEntity> {

    //lateinit var pdf_generado_uri: Uri
    var pdf_generado_uri: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)

    val context = getApplication<mApplication>().applicationContext


    /** CuentaDeCobroDataState State */
    private val _cotizacionDataState = MutableStateFlow(CotizacionDataState())
    val cotizacionDataState: StateFlow<CotizacionDataState> = _cotizacionDataState.asStateFlow()


    /** CuentaDeCobroDataState State */
    private val _cotizacionUiState = MutableStateFlow(CotizacionUiState())
    val cotozacionUiState: StateFlow<CotizacionUiState> = _cotizacionUiState.asStateFlow()


    init {

        actualizarFechaHoraSistema()

    }


    override suspend fun insert(entity: CotizacionContadorEntity) {
        _repository.insert(entity)
    }


    override suspend fun update(entity: CotizacionContadorEntity) {
        _repository.update(entity)
    }


    override fun read(id: String): LiveData<CotizacionContadorEntity?> =
        _repository.read(id).asLiveData()


    override fun readAll(): LiveData<ArrayList<CotizacionContadorEntity>> {
        TODO("Not yet implemented")
    }


    override suspend fun eliminar(entity: CotizacionContadorEntity) {
        TODO("Not yet implemented")
    }


    fun incrementarContadorDocsEmitidos(cotizacionContadorEntity: State<CotizacionContadorEntity?>){

        val nuevo_valor = cotizacionContadorEntity.value?.contador?.plus(1)

        runBlocking {
            insert(CotizacionContadorEntity(contador = nuevo_valor!!))
        }

        _cotizacionDataState.update {
            it.cotizacion_numero = MutableLiveData(it.cotizacion_numero?.value?.plus(1))
            it.copy(
                cotizacion_numero = _cotizacionDataState.value.cotizacion_numero
            )
        }
    }


    fun actualizarContadorDocsEmitidos(cotizacionContadorEntity: State<CotizacionContadorEntity?>){

        _cotizacionDataState.update {currentState ->
            currentState.cotizacion_numero = MutableLiveData(
                if (cotizacionContadorEntity.value?.contador != null) cotizacionContadorEntity.value?.contador else 0
            )
            currentState.copy(
                cotizacion_numero = _cotizacionDataState.value.cotizacion_numero
            )
        }
    }


    fun actualizarFechaHoraSistema(){
        _cotizacionDataState.update {
            it.cotizacion_fecha_expedicion = mutableStateOf(
                FechaHoraSistema().obtenerFecha()
            )

            it.cotizacion_hora_expedicion = mutableStateOf(
                FechaHoraSistema().obtenerHora()
            )

            it.copy(
                cotizacion_fecha_expedicion = _cotizacionDataState.value.cotizacion_fecha_expedicion,
                cotizacion_hora_expedicion = _cotizacionDataState.value.cotizacion_hora_expedicion
            )
        }

    }


    fun actualizarVigencia(nueva_vigencia: Int?){

        _cotizacionDataState.update {
            it.cotizacion_vigencia = mutableStateOf(nueva_vigencia)
            it.copy(
                cotizacion_vigencia = _cotizacionDataState.value.cotizacion_vigencia,
            )
        }
    }


    fun generarCotizacionPDF(
        context: Context
        ,mCotizacionEntity: CotizacionEntity
    ){
        viewModelScope.launch(Dispatchers.IO) {
            var pdf = GenerarCotizacionPDF_FV2(mCotizacionEntity = mCotizacionEntity , context = context)
            pdf.generar()

            //pdf_generado_uri.value = pdf.pdf_generado_uri
            pdf_generado_uri = mutableStateOf(pdf.pdf_generado_uri)
        }
    }

    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mCotizacionRepository
                CotizacionViewModel(
                    application = application,
                    _repository = repository,
                )
            }
        }
    }
}
