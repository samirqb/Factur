package com.samirquiceno.micuentadecobro.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samirquiceno.micuentadecobro.mApplication
import com.samirquiceno.micuentadecobro.models.ServicioEntity
import com.samirquiceno.micuentadecobro.repositories.interfaces.IBaseRepository
import com.samirquiceno.micuentadecobro.tools.OperacionesMetematicas
import com.samirquiceno.micuentadecobro.ui.states.ServicioDataState
import com.samirquiceno.micuentadecobro.viewmodels.interfaces.IBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServicioViewModel(
    private var _repository : IBaseRepository<ServicioEntity>
):IBaseViewModel<ServicioEntity>,ViewModel() {

    /*** STATUS ***/
    private var _servicioDataStore = MutableStateFlow(ServicioDataState())
    var servicioDataStore : StateFlow<ServicioDataState> = _servicioDataStore.asStateFlow()

    override suspend fun insert(entity: ServicioEntity) {
        //_repository.insert(entity)
        _servicioDataStore.value.listaServicioEntity.add(entity)
    }

    suspend fun update(index:Int,entity: ServicioEntity) {
        //_repository.update(entity)
        _servicioDataStore.update {currentState ->
            var lista = currentState.listaServicioEntity
            lista[index] = entity
            currentState.copy(
                listaServicioEntity = lista
            )
        }
    }

    override suspend fun update(entity: ServicioEntity) {
        TODO("Not yet implemented")
    }

    override fun read(id: String): LiveData<ServicioEntity?> {
        return _repository.read(id).asLiveData()
    }

    override fun readAll(): LiveData<ArrayList<ServicioEntity>> {
        return _repository.readAll().asLiveData()
    }

    override suspend fun eliminar(entity: ServicioEntity) {
        _repository.eliminar(entity)
    }

    fun eliminar(indice: Int){
        _servicioDataStore.update {currentState ->
            currentState.listaServicioEntity.removeAt(indice)
            currentState.copy(
                listaServicioEntity = _servicioDataStore.value.listaServicioEntity
            )
        }
    }

    fun sumaTotalServicios():Int{
        return OperacionesMetematicas().sumarTodosLosServicios(servicioDataStore.value.listaServicioEntity)
    }

    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as mApplication).mServicioRepository

                ServicioViewModel(_repository = repository)

            }
        }
    }
}