package com.samirquiceno.micuentadecobro.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samirquiceno.micuentadecobro.mApplication
import com.samirquiceno.micuentadecobro.models.ProveedorServiciosEntity
import com.samirquiceno.micuentadecobro.repositories.interfaces.IBaseRepository
import com.samirquiceno.micuentadecobro.ui.states.MainScreenUiState
import com.samirquiceno.micuentadecobro.ui.states.ProveedorServiciosDataState
import com.samirquiceno.micuentadecobro.viewmodels.interfaces.IBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProveedorServiciosViewModel(
    private var _repository: IBaseRepository<ProveedorServiciosEntity>
): IBaseViewModel<ProveedorServiciosEntity>, ViewModel() {


    /** *********************** S T A T E S *************************/

        /** UI State */
    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

        /** ProveedorServiciosDataState State */
    private val _proveedorServiciosState = MutableStateFlow(ProveedorServiciosDataState())
    val proveedorServiciosDataState: StateFlow<ProveedorServiciosDataState> = _proveedorServiciosState.asStateFlow()



    /** CRUD para tabla "proveedor_serviocios" */
    override suspend fun insert(entity: ProveedorServiciosEntity) {
        _repository.insert(entity)
    }



    override suspend fun update(entity: ProveedorServiciosEntity) {
        _repository.update(entity)
    }



    override fun read(id: String): LiveData<ProveedorServiciosEntity?> =
        _repository.read(id).asLiveData()



    override fun readAll(): LiveData<ArrayList<ProveedorServiciosEntity>> =
        _repository.readAll().asLiveData()



    override suspend fun eliminar(entity: ProveedorServiciosEntity) {
        TODO("Not yet implemented")
    }



    fun updateProveedorServiciosDataState(proveedorServiciosEntity: State<ProveedorServiciosEntity?>) {

        _proveedorServiciosState.update {currentState ->

            currentState.proovedor_servicio_identificacion = mutableStateOf(
                    if(proveedorServiciosEntity.value?.identificacion != null) proveedorServiciosEntity.value?.identificacion else ""
            )

            currentState.proovedor_servicio_nombre = mutableStateOf(
                if(proveedorServiciosEntity.value?.nombre != null) proveedorServiciosEntity.value?.nombre else ""
            )

            currentState.proovedor_servicio_ubicacion = mutableStateOf(
                if(proveedorServiciosEntity.value?.ubicacion != null) proveedorServiciosEntity.value?.ubicacion else ""
            )

            currentState.proovedor_servicio_telefono = mutableStateOf(
                if(proveedorServiciosEntity.value?.telefono != null) proveedorServiciosEntity.value?.telefono else ""
            )

            currentState.proovedor_servicio_email = mutableStateOf(
                if(proveedorServiciosEntity.value?.email != null) proveedorServiciosEntity.value?.email else ""
            )

            currentState.copy(
                proovedor_servicio_identificacion = _proveedorServiciosState.value.proovedor_servicio_identificacion
                , proovedor_servicio_nombre = _proveedorServiciosState.value.proovedor_servicio_nombre
                , proovedor_servicio_ubicacion = _proveedorServiciosState.value.proovedor_servicio_ubicacion
                , proovedor_servicio_telefono = _proveedorServiciosState.value.proovedor_servicio_telefono
                , proovedor_servicio_email = _proveedorServiciosState.value.proovedor_servicio_email
            )
        }
    }


    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val repository = (this[APPLICATION_KEY] as mApplication).mProveedorServiciosRepository

                ProveedorServiciosViewModel(
                    _repository = repository
                )
            }
        }
    }
}