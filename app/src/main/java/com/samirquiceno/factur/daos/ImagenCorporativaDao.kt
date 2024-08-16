package com.samirquiceno.factur.daos

import android.net.Uri
import androidx.datastore.core.IOException
import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.ImagenCorporativaEntity
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream

class ImagenCorporativaDao:IBaseDao<ImagenCorporativaEntity> {
    override suspend fun insert(entity: ImagenCorporativaEntity) {

        val context = entity.context
        val nombre_imagen = entity.nombre
        val uri = entity.uri!!

        val imagenBytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        /** Obtiene el directorio de archivos internos de la app donde guardara la imagen */
        val directorio = context.filesDir

        val archivoImagen = File(directorio, nombre_imagen)

        try {
            val fos = FileOutputStream(archivoImagen)
            fos.write(imagenBytes)
            fos.close()

            /*
            val fos = withContext(Dispatchers.IO) {
                FileOutputStream(archivoImagen)
            }
            withContext(Dispatchers.IO) {
                fos.write(imagenBytes)
            }
            withContext(Dispatchers.IO) {
                fos.close()
            }
            */

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override suspend fun update(entity: ImagenCorporativaEntity) {
        TODO("Not yet implemented")
    }

    override fun read(id: String): Flow<ImagenCorporativaEntity?> {
        TODO("Not yet implemented")
    }

    //fun read(entity: ImagenCorporativaEntity): Flow<ImagenCorporativaEntity?> {
    fun read(entity: ImagenCorporativaEntity): ImagenCorporativaEntity? {

        val context = entity.context
        val nombre_imagen = entity.nombre

        val file = File(context.filesDir, nombre_imagen)

        val uri : Uri

        if (file.exists()) {
            /** este camino (IF) es tomado SIEMPRE, despues que el usuario cargue una
             *  imagen por primera vez */

            uri = Uri.fromFile(file)

        } else {
            /** este camino (ELSE) solo es tomado cuando por primera vez se inicia la app, pues no existe
              * una imagen cargada por el usuario y por enede se toma una por defecto de resources */

            uri = Uri.parse("android.resource://com.samirquiceno.factur/drawable/factur_arte_isologotipo_140px_x_100px")

        }

        return ImagenCorporativaEntity(
            context = context,
            nombre = nombre_imagen,
            uri = uri
        )
    }

    override fun readAll(): Flow<ArrayList<ImagenCorporativaEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun eliminar(entity: ImagenCorporativaEntity) {
        TODO("Not yet implemented")
    }

}