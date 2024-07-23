package com.samirquiceno.micuentadecobro.repositories.interfaces

import com.samirquiceno.micuentadecobro.models.Interfaces.IBaseEntity
import kotlinx.coroutines.flow.Flow

interface IBaseRepository<IBaseEntity> {
    suspend fun insert(entity : IBaseEntity)
    suspend fun update(entity : IBaseEntity)
    fun read(id: String) : Flow<IBaseEntity?>
    fun readAll() : Flow<ArrayList<IBaseEntity>>
    suspend fun eliminar(entity : IBaseEntity)
}