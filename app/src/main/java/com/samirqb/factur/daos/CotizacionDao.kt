package com.samirqb.factur.daos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samirqb.factur.daos.interfaces.IBaseDao
import com.samirqb.factur.models.CotizacionContadorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CotizacionDao(
    val context: Context
): IBaseDao<CotizacionContadorEntity> {

    lateinit private var _entity: Flow<CotizacionContadorEntity?>

    private val _NOBRE_DATASTORE = "DatosCotizacion_DataStore"

    /** instancia de DataStore */
    val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = _NOBRE_DATASTORE)

    /** keys del DataStore */
    val contador_datastore_key = intPreferencesKey("contador_datastore_key")


    override suspend fun insert(entity: CotizacionContadorEntity) {

        context.dataStore.edit {

            it[contador_datastore_key] = entity.contador

        }
    }

    override suspend fun update(entity: CotizacionContadorEntity) {

        var current_value:Int

        context.dataStore.edit {

            current_value = it[contador_datastore_key]?:1

            if (current_value != entity.contador){

                it[contador_datastore_key] = entity.contador

            }
        }
    }

    override fun read(id: String): Flow<CotizacionContadorEntity?> {

        _entity = context.dataStore.data.map { it->

            if ( it[contador_datastore_key] == null) {
                null
            } else {
                CotizacionContadorEntity(
                    contador = it[contador_datastore_key]!!,
                )
            }
        }

        return _entity
    }

    override fun readAll(): Flow<ArrayList<CotizacionContadorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: CotizacionContadorEntity) {
        TODO("Not yet implemented")
    }
}