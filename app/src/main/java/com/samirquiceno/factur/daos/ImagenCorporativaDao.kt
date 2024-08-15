package com.samirquiceno.factur.daos

import android.net.Uri
import android.util.Log
import androidx.datastore.core.IOException
import com.samirquiceno.factur.daos.interfaces.IBaseDao
import com.samirquiceno.factur.models.ImagenCorporativaEntity
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream

class ImagenCorporativaDao:IBaseDao<ImagenCorporativaEntity> {
    override suspend fun insert(entity: ImagenCorporativaEntity) {

        val context       = entity.context
        val nombre_imagen = entity.nombre
        val uri           = entity.uri!!

        Log.d("_xxx","ImagenCorporativaDao.insert -> context: ${context}")
        Log.d("_xxx","ImagenCorporativaDao.insert -> nombre_imagen: ${nombre_imagen}")
        Log.d("_xxx","ImagenCorporativaDao.insert -> uri: ${uri}")

        val imagenBytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        val directorio = context.filesDir // Obtiene el directorio de archivos internos
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

        Log.d("_xxx","ImagenCorporativaDao.read -> context: ${context}")
        Log.d("_xxx","ImagenCorporativaDao.read -> nombre_imagen: ${nombre_imagen}")

        val file = File(context.filesDir, nombre_imagen)

        val uri : Uri

        if (file.exists()) {
            Log.d("_xxx","ImagenCorporaticaDao.read -> if")
            uri = Uri.fromFile(file)
        } else {
            Log.d("_xxx","ImagenCorporaticaDao.read -> else")
            //uri = Uri.parse("android.resource://com.samirquiceno.factur/drawable/imagen_corporativa")
            //BitmapFactory.decodeResource(context.resources, R.drawable.factur_arte_isologotipo_140px_x_100px)
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