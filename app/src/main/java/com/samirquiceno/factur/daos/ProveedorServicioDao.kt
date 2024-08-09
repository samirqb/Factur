package com.samirquiceno.factur.daos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.ProveedorServiciosEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProveedorServicioDao(
    val context: Context
) : IBaseDao<ProveedorServiciosEntity> {

    lateinit private var _entity: Flow<ProveedorServiciosEntity?>

    private val _NOBRE_DATASTORE = "DatosProveedorServicios_DataStore"

    /** instancia de DataStore */
    val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = _NOBRE_DATASTORE)

    /** keys del DataStore */
    val identificacion_datastore_key = stringPreferencesKey("identificacion_datastore_key")
    val nombre_datastore_key         = stringPreferencesKey("nombre_datastore_key")
    val telefono_datastore_key       = stringPreferencesKey("telefono_datastore_key")
    val email_datastore_key          = stringPreferencesKey("email_datastore_key")
    val ubicacion_datastore_key      = stringPreferencesKey("ubicacion_datastore_key")


    override suspend fun insert(entity: ProveedorServiciosEntity) {

        context.dataStore.edit {it ->
            it[identificacion_datastore_key] = entity.identificacion
            it[nombre_datastore_key] = entity.nombre
            it[telefono_datastore_key] = entity.telefono
            it[email_datastore_key] = entity.email
            it[ubicacion_datastore_key] = entity.ubicacion
        }
    }

    override suspend fun update(entity: ProveedorServiciosEntity) {

        var current_value = ""

        context.dataStore.edit {

            current_value = it[identificacion_datastore_key]?:""

            if (current_value != entity.identificacion){
                it[identificacion_datastore_key] = entity.identificacion
            }
        }
    }

    override fun read(id: String): Flow<ProveedorServiciosEntity?> {

        _entity = context.dataStore.data.map { it->

            if ( it[identificacion_datastore_key] == null) {
                null
            } else {
                ProveedorServiciosEntity(
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

    override fun readAll(): Flow<ArrayList<ProveedorServiciosEntity>> {
        TODO()
    }

    override suspend fun eliminar(entity: ProveedorServiciosEntity) {
        TODO()
    }
}