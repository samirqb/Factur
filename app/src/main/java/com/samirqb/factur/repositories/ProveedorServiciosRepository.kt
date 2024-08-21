package com.samirqb.factur.repositories

import com.samirqb.factur.daos.interfaces.IBaseDao
import com.samirqb.factur.models.ProveedorServiciosEntity
import com.samirqb.factur.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

class ProveedorServiciosRepository(
    var dao : IBaseDao<ProveedorServiciosEntity>
) : IBaseRepository<ProveedorServiciosEntity> {



    override suspend fun insert(entity: ProveedorServiciosEntity) {
        dao.insert(entity)
    }



    override suspend fun update(entity: ProveedorServiciosEntity) {
        dao.update(entity)
    }



    override fun read(id: String): Flow<ProveedorServiciosEntity?> {
        return dao.read(id)
    }



    override fun readAll(): Flow<ArrayList<ProveedorServiciosEntity>> {
        TODO("Not yet implemented")
    }



    override suspend fun eliminar(entity: ProveedorServiciosEntity) {
        TODO("Not yet implemented")
    }

}