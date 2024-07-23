package com.samirquiceno.micuentadecobro.daos

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.datastore.core.IOException
import java.io.File
import java.io.FileOutputStream

class ImagenDao() {

    suspend fun insert(context: Context, nombre_imagen:String, uri:Uri){

        val imagenBytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        val directorio = context.filesDir // Obtiene el directorio de archivos internos
        val archivoImagen = File(directorio, nombre_imagen)

        try {
            val fos = FileOutputStream(archivoImagen)
            fos.write(imagenBytes)
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun read(context: Context,nombre_imagen: String): Uri? {

        val file = File(context.filesDir,nombre_imagen,)

        val uri : Uri

        if (file.exists()) {
            uri = Uri.fromFile(file)
        } else {
            uri = Uri.parse("android.resource://com.samirquiceno.micuentadecobro/drawable/imagen_corporativa")
        }

        return uri

    }

    fun readAll(context: Context):Array<String>{
        Log.d("_xxx", "context.fileList() = ${context.fileList()}")
        return context.fileList()
    }


}