package com.samirqb.factur.repositories.interfaces

import kotlinx.coroutines.flow.Flow

interface IBaseRepository<IBaseEntity> {
    suspend fun insert(entity : IBaseEntity)
    suspend fun update(entity : IBaseEntity)
    fun read(id: String) : Flow<IBaseEntity?>
    fun readAll() : Flow<ArrayList<IBaseEntity>>
    suspend fun eliminar(entity : IBaseEntity)
}