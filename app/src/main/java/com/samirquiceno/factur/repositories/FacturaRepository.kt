package com.samirquiceno.factur.repositories

import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.FacturaContadorEntity
import com.samirquiceno.factur.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

data class FacturaRepository(
    var dao: IBaseDao<FacturaContadorEntity>
): IBaseRepository<FacturaContadorEntity> {

    override suspend fun insert(entity: FacturaContadorEntity) {
        dao.insert(entity)
    }

    override suspend fun update(entity: FacturaContadorEntity) {
        dao.update(entity)
    }

    override fun read(id: String): Flow<FacturaContadorEntity?> {
        return dao.read(id)
    }

    override fun readAll(): Flow<ArrayList<FacturaContadorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: FacturaContadorEntity) {
        TODO("Not yet implemented")
    }
}