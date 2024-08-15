package com.samirquiceno.factur.repositories

import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.CotizacionContadorEntity
import com.samirquiceno.factur.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

class CotizacionRepository(
    var dao: IBaseDao<CotizacionContadorEntity>
): IBaseRepository<CotizacionContadorEntity> {

    override suspend fun insert(entity: CotizacionContadorEntity) {
        dao.insert(entity)
    }

    override suspend fun update(entity: CotizacionContadorEntity) {
        dao.update(entity)
    }

    override fun read(id: String): Flow<CotizacionContadorEntity?> {
        return dao.read(id)
    }

    override fun readAll(): Flow<ArrayList<CotizacionContadorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: CotizacionContadorEntity) {
        TODO("Not yet implemented")
    }
}