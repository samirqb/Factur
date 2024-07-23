package com.samirquiceno.micuentadecobro.viewmodels.interfaces

import androidx.lifecycle.LiveData
import com.samirquiceno.micuentadecobro.models.Interfaces.IBaseEntity

interface IBaseViewModel<IBaseEntity> {
    suspend fun insert(entity : IBaseEntity)
    suspend fun update(entity : IBaseEntity)
    fun read(id: String) : LiveData<IBaseEntity?>
    fun readAll() : LiveData<ArrayList<IBaseEntity>>
    suspend fun eliminar(entity: IBaseEntity)
}
