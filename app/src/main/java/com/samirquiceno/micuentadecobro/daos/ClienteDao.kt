package com.samirquiceno.micuentadecobro.daos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samirquiceno.micuentadecobro.daos.interfaces.IBaseDao
import com.samirquiceno.micuentadecobro.models.ClienteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClienteDao(val context: Context) : IBaseDao<ClienteEntity> {


    lateinit private var _entity: Flow<ClienteEntity?>


    private val _NOBRE_DATASTORE = "DatosCliente_DataStore"

    val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = _NOBRE_DATASTORE)


    val identificacion_datastore_key = stringPreferencesKey("identificacion_datastore_key")
    val nombre_datastore_key         = stringPreferencesKey("nombre_datastore_key")
    val telefono_datastore_key       = stringPreferencesKey("telefono_datastore_key")
    val email_datastore_key          = stringPreferencesKey("email_datastore_key")
    val ubicacion_datastore_key      = stringPreferencesKey("ubicacion_datastore_key")

    override suspend fun insert(entity: ClienteEntity) {
        context.dataStore.edit {it ->
            it[identificacion_datastore_key] = entity.identificacion
            it[nombre_datastore_key] = entity.nombre
            it[telefono_datastore_key] = entity.telefono
            it[email_datastore_key] = entity.email
            it[ubicacion_datastore_key] = entity.ubicacion
        }
    }

    override suspend fun update(entity: ClienteEntity) {

        var current_value = ""
        context.dataStore.edit {
            current_value = it[identificacion_datastore_key]?:""
            if (current_value != entity.identificacion){
                it[identificacion_datastore_key] = entity.identificacion
            }
        }

    }

    override fun read(id: String): Flow<ClienteEntity?> {

        _entity = context.dataStore.data.map { it->

            if ( it[identificacion_datastore_key] == null) {
                null
            } else {
                ClienteEntity(
                    identificacion = it[identificacion_datastore_key].toString(),
                    nombre =         it[nombre_datastore_key].toString(),
                    telefono =       it[telefono_datastore_key].toString(),
                    email =          it[email_datastore_key].toString(),
                    ubicacion =      it[ubicacion_datastore_key].toString(),
                )
            }
        }

        return _entity
    }

    override fun readAll(): Flow<ArrayList<ClienteEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: ClienteEntity) {
        TODO("Not yet implemented")
    }

    suspend fun limpiarDatosCliente(){
        context.dataStore.edit {it ->
            it[identificacion_datastore_key] = ""
            it[nombre_datastore_key] = ""
            it[telefono_datastore_key] = ""
            it[email_datastore_key] = ""
            it[ubicacion_datastore_key] = ""
        }
    }

}