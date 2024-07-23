package com.samirquiceno.micuentadecobro.daos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samirquiceno.micuentadecobro.daos.interfaces.IBaseDao
import com.samirquiceno.micuentadecobro.models.CuentaDeCobroContadorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CuentaDeCobroDao(
    val context: Context
):IBaseDao<CuentaDeCobroContadorEntity> {

    lateinit private var _entity: Flow<CuentaDeCobroContadorEntity?>

    private val _NOBRE_DATASTORE = "DatosCuntaDeCobro_DataStore"

    /** instancia de DataStore */
    val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = _NOBRE_DATASTORE)

    /** keys del DataStore */
    val contador_datastore_key = intPreferencesKey("contador_datastore_key")


    override suspend fun insert(entity: CuentaDeCobroContadorEntity) {

        context.dataStore.edit {

            it[contador_datastore_key] = entity.contador

        }
    }

    override suspend fun update(entity: CuentaDeCobroContadorEntity) {

        var current_value:Int

        context.dataStore.edit {

            current_value = it[contador_datastore_key]?:1

            if (current_value != entity.contador){

                it[contador_datastore_key] = entity.contador

            }
        }
    }

    override fun read(id: String): Flow<CuentaDeCobroContadorEntity?> {

        _entity = context.dataStore.data.map { it->

            if ( it[contador_datastore_key] == null) {
                null
            } else {
                CuentaDeCobroContadorEntity(
                    contador = it[contador_datastore_key]!!,
                )
            }
        }

        return _entity
    }

    override fun readAll(): Flow<ArrayList<CuentaDeCobroContadorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: CuentaDeCobroContadorEntity) {
        TODO("Not yet implemented")
    }
}