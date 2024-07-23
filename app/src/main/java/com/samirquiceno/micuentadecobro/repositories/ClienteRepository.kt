package com.samirquiceno.micuentadecobro.repositories

import com.samirquiceno.micuentadecobro.daos.ClienteDao
import com.samirquiceno.micuentadecobro.models.ClienteEntity
import com.samirquiceno.micuentadecobro.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

class ClienteRepository(
    var dao: ClienteDao
):IBaseRepository<ClienteEntity> {

    override suspend fun insert(entity: ClienteEntity) {
        dao.insert(entity)
    }

    override suspend fun update(entity: ClienteEntity) {
        dao.update(entity)
    }

    override fun read(id: String): Flow<ClienteEntity?> {
        return dao.read(id)
    }

    override fun readAll(): Flow<ArrayList<ClienteEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: ClienteEntity) {
        TODO("Not yet implemented")
    }

    suspend fun limpiarDatosCliente(){
        dao.limpiarDatosCliente()
    }
}