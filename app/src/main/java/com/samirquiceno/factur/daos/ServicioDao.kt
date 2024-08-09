package com.samirquiceno.factur.daos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.ServicioEntity
import kotlinx.coroutines.flow.Flow

class ServicioDao(var context: Context):IBaseDao<ServicioEntity> {

    private val _NOBRE_DATASTORE = "DatosServicios_DataStore"

    val Context.dataStore : DataStore<Preferences> by
    preferencesDataStore(name = _NOBRE_DATASTORE)

    /** keys del DataStore */
    val insert_datastore_key = stringPreferencesKey("insert_datastore_key")

    override suspend fun insert(entity: ServicioEntity) {

        /*
        context.dataStore.edit {it ->
            it[insert_datastore_key] = entity
        }*/

    }


    override suspend fun update(entity: ServicioEntity) {
        TODO("Not yet implemented")
    }

    override fun read(id: String): Flow<ServicioEntity?> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Flow<ArrayList<ServicioEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: ServicioEntity) {
        TODO("Not yet implemented")
    }
}