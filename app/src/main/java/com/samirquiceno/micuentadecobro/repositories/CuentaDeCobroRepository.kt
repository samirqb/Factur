package com.samirquiceno.micuentadecobro.repositories

import com.samirquiceno.micuentadecobro.daos.interfaces.IBaseDao
import com.samirquiceno.micuentadecobro.models.CuentaDeCobroContadorEntity
import com.samirquiceno.micuentadecobro.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

class CuentaDeCobroRepository(
    var dao: IBaseDao<CuentaDeCobroContadorEntity>
): IBaseRepository<CuentaDeCobroContadorEntity> {

    override suspend fun insert(entity: CuentaDeCobroContadorEntity) {
        dao.insert(entity)
    }

    override suspend fun update(entity: CuentaDeCobroContadorEntity) {
        dao.update(entity)
    }

    override fun read(id: String): Flow<CuentaDeCobroContadorEntity?> {
        return dao.read(id)
    }

    override fun readAll(): Flow<ArrayList<CuentaDeCobroContadorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: CuentaDeCobroContadorEntity) {
        TODO("Not yet implemented")
    }
}