package com.samirquiceno.micuentadecobro.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samirquiceno.micuentadecobro.mApplication
import com.samirquiceno.micuentadecobro.models.ClienteEntity
import com.samirquiceno.micuentadecobro.repositories.ClienteRepository
import com.samirquiceno.micuentadecobro.ui.states.ClienteDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClienteViewModel(
    private var _repository: ClienteRepository
) :  ViewModel() {

    private val _clienteDataState = MutableStateFlow(ClienteDataState())
    val clienteDataState: StateFlow<ClienteDataState> = _clienteDataState.asStateFlow()


    suspend fun insert(entity: ClienteEntity) {
        _repository.insert(entity)
    }

    suspend fun update(entity: ClienteEntity) {
        _repository.update(entity)
    }

    fun read(id: String): LiveData<ClienteEntity?> =
        _repository.read(id).asLiveData()

    /*
    fun read(id: String): StateFlow<ClienteDataState?> {

        // var mClienteEntity = clienteDataState

        /*
        var mClienteEntity = ClienteEntity(
            identificacion = clienteDataState.value.cliente_identificacion.toString()
            , nombre = clienteDataState.value.cliente_nombre.toString()
            , telefono = clienteDataState.value.cliente_telefono.toString()
            , ubicacion = clienteDataState.value.cliente_ubicacion.toString()
            , email = clienteDataState.value.cliente_email.toString()
        )
        */

        return clienteDataState
    }

     */

     fun readAll(): LiveData<ArrayList<ClienteEntity>> {
        TODO("Not yet implemented")
    }

    suspend fun eliminar(id: String) {
        TODO("Not yet implemented")
    }

    suspend fun limpiarDatosCliente(){
        _repository.limpiarDatosCliente()
    }



    fun updateClienteDataState(clienteEntity: State<ClienteEntity?>) {

        _clienteDataState.update {currentState ->

            currentState.cliente_identificacion = mutableStateOf(
                if(clienteEntity.value?.identificacion != null) clienteEntity.value?.identificacion else ""
            )

            currentState.cliente_nombre = mutableStateOf(
                if(clienteEntity.value?.nombre != null) clienteEntity.value?.nombre else ""
            )

            currentState.cliente_ubicacion = mutableStateOf(
                if(clienteEntity.value?.ubicacion != null) clienteEntity.value?.ubicacion else ""
            )

            currentState.cliente_telefono = mutableStateOf(
                if(clienteEntity.value?.telefono != null) clienteEntity.value?.telefono else ""
            )

            currentState.cliente_email = mutableStateOf(
                if(clienteEntity.value?.email != null) clienteEntity.value?.email else ""
            )

            currentState.copy(
                cliente_identificacion = _clienteDataState.value.cliente_identificacion
                , cliente_nombre = _clienteDataState.value.cliente_nombre
                , cliente_ubicacion = _clienteDataState.value.cliente_ubicacion
                , cliente_telefono = _clienteDataState.value.cliente_telefono
                , cliente_email = _clienteDataState.value.cliente_email
            )
        }
    }

    fun limpiarClienteDataState() {

        Log.d("_xxx","limpiarClienteDataState( -> )")

        _clienteDataState.update {currentState ->

            Log.d("_xxx","_clienteDataState.update {currentState -> }")

            currentState.cliente_identificacion = mutableStateOf("")
            currentState.cliente_nombre = mutableStateOf("")
            currentState.cliente_ubicacion = mutableStateOf("")
            currentState.cliente_telefono = mutableStateOf("")
            currentState.cliente_email = mutableStateOf("")

            currentState.copy(
                cliente_identificacion = _clienteDataState.value.cliente_identificacion
                , cliente_nombre = _clienteDataState.value.cliente_nombre
                , cliente_ubicacion = _clienteDataState.value.cliente_ubicacion
                , cliente_telefono = _clienteDataState.value.cliente_telefono
                , cliente_email = _clienteDataState.value.cliente_email
            )
        }

        read("")
    }


    companion object{

        val Factory : ViewModelProvider.Factory = viewModelFactory {

            initializer {

                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as mApplication).mClienteRepository

                ClienteViewModel(
                    _repository = repository
                )
            }
        }
    }
}