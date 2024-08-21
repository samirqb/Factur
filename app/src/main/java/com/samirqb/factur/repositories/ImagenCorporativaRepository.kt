package com.samirqb.factur.repositories

import com.samirqb.factur.daos.ImagenCorporativaDao
import com.samirqb.factur.models.ImagenCorporativaEntity
import com.samirqb.factur.repositories.interfaces.IBaseRepository
import kotlinx.coroutines.flow.Flow

class ImagenCorporativaRepository(
    private var mImagenCorporativaDao : ImagenCorporativaDao
):IBaseRepository<ImagenCorporativaEntity> {
    override suspend fun insert(entity: ImagenCorporativaEntity) {
        mImagenCorporativaDao.insert(entity)
    }

    override suspend fun update(entity: ImagenCorporativaEntity) {
        TODO("Not yet implemented")
    }

    override fun read(id: String): Flow<ImagenCorporativaEntity?> {
        TODO("Not yet implemented")
    }
    fun read(entity: ImagenCorporativaEntity): ImagenCorporativaEntity? {
        return mImagenCorporativaDao.read(entity)
    }

    override fun readAll(): Flow<ArrayList<ImagenCorporativaEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: ImagenCorporativaEntity) {
        TODO("Not yet implemented")
    }
}