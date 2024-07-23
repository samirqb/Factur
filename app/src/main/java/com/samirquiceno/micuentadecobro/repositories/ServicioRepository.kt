package com.samirquiceno.micuentadecobro.repositories

import com.samirquiceno.micuentadecobro.daos.interfaces.IBaseDao
import com.samirquiceno.micuentadecobro.models.ServicioEntity
import com.samirquiceno.micuentadecobro.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

class ServicioRepository(
    var dao: IBaseDao<ServicioEntity>
):IBaseRepository<ServicioEntity> {

    override suspend fun insert(entity: ServicioEntity) {
        dao.insert(entity)
    }

    override suspend fun update(entity: ServicioEntity) {
        dao.update(entity)
    }

    override fun read(id: String): Flow<ServicioEntity?> {
        return dao.read(id)
    }

    override fun readAll(): Flow<ArrayList<ServicioEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: ServicioEntity) {
        TODO("Not yet implemented")
    }
}