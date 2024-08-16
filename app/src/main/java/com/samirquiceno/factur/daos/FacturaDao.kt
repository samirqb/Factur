package com.samirquiceno.factur.daos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.FacturaContadorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FacturaDao(
    val context: Context
): IBaseDao<FacturaContadorEntity> {

    lateinit private var _entity: Flow<FacturaContadorEntity?>

    private val _NOBRE_DATASTORE = "DatosFactura_DataStore"

    /** instancia de DataStore */
    val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = _NOBRE_DATASTORE)

    /** keys del DataStore */
    val contador_datastore_key = intPreferencesKey("contador_datastore_key")


    override suspend fun insert(entity: FacturaContadorEntity) {

        context.dataStore.edit {

            it[contador_datastore_key] = entity.contador

        }
    }

    override suspend fun update(entity: FacturaContadorEntity) {

        var current_value:Int

        context.dataStore.edit {

            current_value = it[contador_datastore_key]?:1

            if (current_value != entity.contador){

                it[contador_datastore_key] = entity.contador

            }
        }
    }

    override fun read(id: String): Flow<FacturaContadorEntity?> {

        _entity = context.dataStore.data.map { it->

            if ( it[contador_datastore_key] == null) {
                null
            } else {
                FacturaContadorEntity(
                    contador = it[contador_datastore_key]!!,
                )
            }
        }

        return _entity
    }

    override fun readAll(): Flow<ArrayList<FacturaContadorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: FacturaContadorEntity) {
        TODO("Not yet implemented")
    }
}