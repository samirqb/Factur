package com.samirquiceno.micuentadecobro.tools

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class FechaHoraSistema {

    @SuppressLint("SimpleDateFormat")
    fun obtenerTimestamp():String {

        //val formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ms", Locale.ENGLISH)
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)
        val fecha_hora = LocalDateTime.now().format(formato)

        return fecha_hora
    }

    @SuppressLint("SimpleDateFormat")
    fun obtenerFecha():String {

        //val formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ms", Locale.ENGLISH)
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
        val fecha_hora = LocalDateTime.now().format(formato)

        return fecha_hora
    }

    @SuppressLint("SimpleDateFormat")
    fun obtenerHora():String {

        val formato = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)
        val fecha_hora = LocalDateTime.now().format(formato)

        return fecha_hora
    }


}