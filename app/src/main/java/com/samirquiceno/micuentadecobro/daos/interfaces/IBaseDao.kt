package com.samirquiceno.micuentadecobro.daos.interfaces

import kotlinx.coroutines.flow.Flow

interface IBaseDao <IBaseEntity> {
    suspend fun insert(entity : IBaseEntity)
    suspend fun update(entity : IBaseEntity)
    fun read(id: String) : Flow<IBaseEntity?>
    fun readAll() : Flow<ArrayList<IBaseEntity>>
    suspend fun eliminar(entity : IBaseEntity)
}