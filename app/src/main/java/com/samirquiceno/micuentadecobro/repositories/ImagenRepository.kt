package com.samirquiceno.micuentadecobro.repositories

import android.content.Context
import android.net.Uri
import com.samirquiceno.micuentadecobro.daos.ImagenDao

class ImagenRepository(private var imagenDao:ImagenDao) {

    suspend fun insert(context: Context, nombre_imagen:String, uri: Uri){
        imagenDao.insert(context = context, nombre_imagen= nombre_imagen,uri=uri)
    }



    fun read(context: Context,nombre_imagen: String): Uri? {

        return imagenDao.read(context = context, nombre_imagen= nombre_imagen)
    }

}