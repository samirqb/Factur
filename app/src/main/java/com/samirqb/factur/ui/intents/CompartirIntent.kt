package com.samirqb.factur.ui.intents

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File


// Función para compartir el archivo PDF
// a continiacion un Android Sharesheet
fun CompartiIntent(context: Context, pdfFileUri: Uri?){

    val pdfFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),pdfFileUri?.lastPathSegment!!)

    val pdfUri = FileProvider.getUriForFile(context, "com.samirqb.factur.fileprovider", pdfFile)

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, pdfUri)
    }

    val shareIntent = Intent.createChooser(sendIntent,"Compartir PDF")

    //context.startActivity(Intent.createChooser(shareIntent, "Compartir PDF"))
    context.startActivity(shareIntent)
}