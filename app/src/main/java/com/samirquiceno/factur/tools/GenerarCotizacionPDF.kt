package com.samirquiceno.factur.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.samirquiceno.factur.R
import com.samirquiceno.factur.models.CotizacionEntity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GenerarCotizacionPDF(
    private var context: Context,
    private var mCotizacionEntity: CotizacionEntity,
) {

    var pdf_generado_uri: Uri = Uri.EMPTY

    fun generar():Boolean{

        var mNumFormat = NumberFormat.getCurrencyInstance() //.format()
        var document = PdfDocument()

        /*** A4 size ***/
        var page_width_points = 595
        var page_height_points = 842
        var page_number = 1
        var pageInfo = PdfDocument.PageInfo.Builder(page_width_points, page_height_points, page_number).create()

        var page: PdfDocument.Page = document.startPage(pageInfo)
        var contentPag: Canvas = page.getCanvas()


        /** IMAGEN CORPORATIVA PARA REPORTE **/
        var set_drawable = Paint()
        //var imagen_png: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cctq_logo_pdf)
        //var imagen_png: Bitmap = BitmapFactory.decodeFile(mCotizacionEntity.imagen_corporativa_uri.path)
        /** si la uri de la imagen a imprimir es null o vacia, el sistema toma una imagen predeterminada
         * del sistema (drawable/factur_arte_isologotipo_140px_x_100px) y la imprime como Imagen corporativa  */
        /*
        var imagen_png: Bitmap = if ( mCotizacionEntity.imagen_corporativa_uri.path.isNullOrEmpty()
            && mCotizacionEntity.imagen_corporativa_uri.path.isNullOrBlank()){
            BitmapFactory.decodeFile(mCotizacionEntity.imagen_corporativa_uri.path)
        } else{
            BitmapFactory.decodeResource(context.resources, R.drawable.factur_arte_isologotipo_140px_x_100px)
        }
        */

        var imagen_png: Bitmap
        var scaledbmp: Bitmap
        var escala = 3

        if( mCotizacionEntity.imagen_corporativa_uri.lastPathSegment == "imagen_corporativa"){
            imagen_png = BitmapFactory.decodeFile(mCotizacionEntity.imagen_corporativa_uri.path)
            scaledbmp = Bitmap.createScaledBitmap(imagen_png, imagen_png.width, imagen_png.height, false)
            //contentPag.drawBitmap(scaledbmp,53F,53F,set_drawable)
        } else{
            imagen_png = BitmapFactory.decodeResource(context.resources, R.drawable.factur_arte_isologotipo_140px_x_100px)
            scaledbmp = Bitmap.createScaledBitmap(imagen_png, imagen_png.width / escala , imagen_png.height / escala , false)
            //contentPag.drawBitmap(scaledbmp,53F,53F,set_drawable)
        }

        contentPag.drawBitmap(scaledbmp,53F,53F,set_drawable)

        /*
        //var escala = 3

        var scaledbmp = Bitmap.createScaledBitmap(imagen_png, imagen_png.width/* /escala */, imagen_png.height/* /escala */, false)
        contentPag.drawBitmap(scaledbmp,53F,53F,set_drawable)
        */
        /******************************************/

        /** Set style font to tittle **/
        var set_string = Paint()

        set_string.setTextSize(18F)
        set_string.fontVariationSettings
        //set_string.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD)
        )
        set_string.setColor(Color.BLACK)
        set_string.textAlign = Paint.Align.RIGHT

        /** DATOS DEL REPORTE **/
        var tipo_reporte = mCotizacionEntity.tipo_reporte
        var eje_x = 530F
        var eje_y = 60F
        contentPag.drawText(tipo_reporte,eje_x,eje_y,set_string)


        set_string.setTextSize(12F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.NORMAL)
        )


        /** DATOS PRESTADOR DEL SERVICIO **/
        var nombre_ps = mCotizacionEntity.mDatosPrestadorServicioEntity.nombre
        eje_y = 85F
        contentPag.drawText(nombre_ps,eje_x,eje_y,set_string)

        /** UBICACION PRESTADOR DE SERVICIO **/
        var ubicacion_ps = mCotizacionEntity.mDatosPrestadorServicioEntity.ubicacion
        eje_y = 100F
        contentPag.drawText(ubicacion_ps,eje_x,eje_y,set_string)

        /** TELEFONO PRESTADOR DE SERVICIO **/
        var telefono_ps = mCotizacionEntity.mDatosPrestadorServicioEntity.telefono
        eje_y = 115F
        contentPag.drawText(telefono_ps,eje_x,eje_y,set_string)

        /** EMAIL PRESTADOR DE SERVICIO **/
        var email_ps = mCotizacionEntity.mDatosPrestadorServicioEntity.email
        eje_y = 130F
        contentPag.drawText(email_ps,eje_x,eje_y,set_string)

        /** IDENTIFICACION PRESTADOR DE SERVICIO **/
        var identificacion_ps = mCotizacionEntity.mDatosPrestadorServicioEntity.identificacion
        eje_y = 145F
        contentPag.drawText(identificacion_ps,eje_x,eje_y,set_string)

        /**********************************/



        /** Set style font to tittle **/
        //set_string.textAlign = Paint.Align.LEFT
        //set_string.setTextSize(12F)
        //set_string.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))


        set_string.setTextSize(15F)
        set_string.textAlign = Paint.Align.LEFT
        set_string.fontVariationSettings
        //set_string.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD)
        )

        /** LABELS DATOS RECEPTOR DEL SERVICIO **/

        var titulo_datos_cliente = "Datos del Cliente "
        eje_x = 43F
        eje_y = 200F
        contentPag.drawText(titulo_datos_cliente,eje_x,eje_y,set_string)

        set_string.setTextSize(12F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.NORMAL)
        )

        /** LABEL - FECHA Y HORA DEL REPORTE **/
        var label_fecha_hora_reporte = "Fecha y hora: "
        eje_x = 43F
        eje_y = 220F
        contentPag.drawText(label_fecha_hora_reporte,eje_x,eje_y,set_string)

        /** LABEL - NOMBRE RECEPTOR SERVICIO **/
        var label_nombre_rs = "Nombre del cliente: "
        eje_y = 235F
        contentPag.drawText(label_nombre_rs,eje_x,eje_y,set_string)

        // LABEL - IDENTIFICACION RECEPTOR DE SERVICIO
        var label_identificacion_rs = "Identificacion del cliente: "
        eje_y = 250F
        contentPag.drawText(label_identificacion_rs,eje_x,eje_y,set_string)

        // LABEL - MARCA VEHICULO RECEPTOR DE SERVICIO
        var label_vehiculo_marca_rs = "Telefono del cliente: "
        eje_y = 265F
        contentPag.drawText(label_vehiculo_marca_rs,eje_x,eje_y,set_string)

        // LABEL - MODELO VEHICULO RECEPTOR DE SERVICIO
        var label_vehiculo_modelo_rs = "Ubicacion del cliente: "
        eje_y = 280F
        contentPag.drawText(label_vehiculo_modelo_rs,eje_x,eje_y,set_string)

        // LABEL - MATRICULA VEHICULO RECEPTOR DE SERVICIO
        var label_vehiculo_matricula_rs = "Email del cliente: "
        eje_y = 295F
        contentPag.drawText(label_vehiculo_matricula_rs,eje_x,eje_y,set_string)

        // LABEL - VALOR TOTAL A PAGAR
        var label_valor_total_rs = "Valor total a pagar:"
        eje_y = 310F
        contentPag.drawText(label_valor_total_rs,eje_x,eje_y,set_string)


        /** DATOS RECEPTOR DEL SERVICIO **/
        //FECHA Y HORA REPORTE
        var fecha_hora_reporte = mCotizacionEntity.fecha_hora_generacion_reporte
        eje_x = 240F
        eje_y = 220F
        contentPag.drawText(fecha_hora_reporte,eje_x,eje_y,set_string)

        // NOMBRE RECEPTOR SERVICIO
        var nombre_rs = mCotizacionEntity.mDatosReceptorServicioEntity.nombre
        eje_y = 235F
        contentPag.drawText(nombre_rs,eje_x,eje_y,set_string)

        // IDENTIFICACION RECEPTOR DE SERVICIO
        var identificacion_rs = mCotizacionEntity.mDatosReceptorServicioEntity.identificacion
        eje_y = 250F
        contentPag.drawText(identificacion_rs,eje_x,eje_y,set_string)

        // MARCA VEHICULO RECEPTOR DE SERVICIO
        var vehiculo_marca_rs = mCotizacionEntity.mDatosReceptorServicioEntity.telefono
        eje_y = 265F
        contentPag.drawText(vehiculo_marca_rs,eje_x,eje_y,set_string)

        // MODELO VEHICULO RECEPTOR DE SERVICIO
        var vehiculo_modelo_rs = mCotizacionEntity.mDatosReceptorServicioEntity.ubicacion
        eje_y = 280F
        contentPag.drawText(vehiculo_modelo_rs,eje_x,eje_y,set_string)

        // MATRICULA VEHICULO RECEPTOR DE SERVICIO
        var vehiculo_matricula_rs = mCotizacionEntity.mDatosReceptorServicioEntity.email
        eje_y = 295F
        contentPag.drawText(vehiculo_matricula_rs,eje_x,eje_y,set_string)

        // VALOR TOTAL A PAGAR
        var total_suma_servicios_rs1 = mNumFormat.format(mCotizacionEntity.total_suma_servicios)
        eje_y = 310F
        contentPag.drawText(total_suma_servicios_rs1,eje_x,eje_y,set_string)


        /*********************************/


        /** Set style font to tittle **/
        set_string.textAlign = Paint.Align.CENTER
        set_string.setTextSize(15F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD)
        )


        /** LABELS - DATOS SERVICIOS CONTRATADOS RECEPTOR DEL SERVICIO **/
        /** CABECERAS **/

        var label_cantidad_rs = "Cant"
        eje_x = 45F
        eje_y = 360F
        contentPag.drawText(label_cantidad_rs,eje_x,eje_y,set_string)

        // IDENTIFICACION RECEPTOR DE SERVICIO
        var label_descripcion_servicio_rs = "Descripcion"
        eje_x = 200F
        contentPag.drawText(label_descripcion_servicio_rs,eje_x,eje_y,set_string)

        // MARCA VEHICULO RECEPTOR DE SERVICIO
        var label_valor_unitario_servicio_rs = "Vlr unidad"
        eje_x = 400F
        contentPag.drawText(label_valor_unitario_servicio_rs,eje_x,eje_y,set_string)

        // MODELO VEHICULO RECEPTOR DE SERVICIO
        var label_subtotal_servicio_rs = "Subtotal"
        eje_x = 520F
        contentPag.drawText( label_subtotal_servicio_rs,eje_x,eje_y,set_string )

        /*---------- linea separadora cabecera ----------*/
        var set_draw = Paint()
        set_draw.setColor(Color.BLACK)
        contentPag.drawLine(30F,365F,570F,365F,set_draw)


        /** REGISTROS - DATOS SERVICIOS CONTRATADOS RECEPTOR DEL SERVICIO **/
        /** SERVICIOS CONTRATADOS **/


        /** Set style font to tittle **/
        set_string.textAlign = Paint.Align.CENTER
        set_string.setTextSize(12F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.NORMAL
            ))


        eje_y = 390F
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.NORMAL)
        )

        mCotizacionEntity.mServicioEntity.forEach {

            // CANTIDAD UNIDADES SERVICIO
            var cantidad_rs = it?.cantidad.toString()
            eje_x = 50F
            set_string.textAlign = Paint.Align.RIGHT
            contentPag.drawText(cantidad_rs,eje_x,eje_y,set_string)

            // DESCRIPCION DEL SERVICIO
            var descripcion_servicio_rs = it?.descripcion_servicio.toString()
            eje_x = 90F
            set_string.textAlign = Paint.Align.LEFT
            contentPag.drawText(descripcion_servicio_rs,eje_x,eje_y,set_string)

            // VALOR UNITARIO DEL SERVICIO
            var valor_unitario_servicio_rs = mNumFormat.format(it?.valor_unitario_del_servicio)
            eje_x = 440F
            set_string.textAlign = Paint.Align.RIGHT
            contentPag.drawText(valor_unitario_servicio_rs,eje_x,eje_y,set_string)

            // SUBTOTAL SERVICIO
            var subtotal_servicio_rs = mNumFormat.format(it?.valor_total_del_servicio)
            eje_x = 570F
            set_string.textAlign = Paint.Align.RIGHT
            contentPag.drawText( subtotal_servicio_rs,eje_x,eje_y,set_string )

            eje_y += 15
        }

        /*---------- linea separadora final de lista servicios ----------*/
        var linea_final:Float = eje_y - 5
        set_draw.setColor(Color.BLACK)
        contentPag.drawLine(30F,linea_final,570F,linea_final,set_draw)


        /** Set style font to tittle **/
        set_string.textAlign = Paint.Align.CENTER
        set_string.setTextSize(15F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD)
        )


        // LABEL - TOTAL A PAGAR
        var label_total_pager_rs = "TOTAL A PAGAR: "
        eje_x = 400F
        eje_y += 10
        set_string.textAlign = Paint.Align.RIGHT
        contentPag.drawText( label_total_pager_rs,eje_x,eje_y,set_string )

        // TOTAL A PAGAR
        var total_suma_servicios_rs = mNumFormat.format(mCotizacionEntity.total_suma_servicios)
        eje_x = 570F
        set_string.textAlign = Paint.Align.RIGHT
        contentPag.drawText( total_suma_servicios_rs,eje_x,eje_y,set_string )

        /************ inicio disclainer **************/

        /** Set style font to tittle **/
        set_string.textAlign = Paint.Align.CENTER
        set_string.setTextSize(9F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD_ITALIC
            ))


        /*
        var disclaimer_txt_l1 = "Para efectos de retención en la fuente solicito se me aplique la tabla de retención establecida"
        eje_x = 45F
        eje_y = 745F
        set_string.textAlign = Paint.Align.LEFT
        contentPag.drawText( disclaimer_txt_l1,eje_x,eje_y,set_string )

        var disclaimer_txt_l2 = "en el artículo 383 del E.T, para lo cual certifico bajo la gravedad de juramento que no voy a"
        eje_y = 760F
        contentPag.drawText( disclaimer_txt_l2,eje_x,eje_y,set_string )
        */

        var disclaimer_txt_l3 = "*Este documento NO representa una compra o factura de compra."
        eje_x = 45F
        eje_y = 775F
        set_string.textAlign = Paint.Align.LEFT
        contentPag.drawText( disclaimer_txt_l3,eje_x,eje_y,set_string )

        /************ final disclainer ***************/


        document.finishPage(page)

        var downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        var nombre_archivo_raw = "CT${mCotizacionEntity.numero_consecutivo}_${mCotizacionEntity.fecha_hora_generacion_reporte}.pdf"
        var nombre_archivo_pdf = nombre_archivo_raw.replace("[-: ]".toRegex(),"")
        val file: File = File(downloadDir, nombre_archivo_pdf)



        try {
            var fos = FileOutputStream(file)
            document.writeTo(fos)
            document.close()
            fos.close()
            pdf_generado_uri = file.toUri()
            return true

        }catch (e: Exception) {

            Log.d("_xxx", "class GenerarPDF.generar()")
            Log.d("_xxx", "e: ${e}")
            Log.d("_xxx", "Fail to generate PDF file..")
            return false

        }catch (ioe: IOException){

            Log.d("_xxx", "class GenerarPDF.generar()")
            Log.d("_xxx", "ioe: ${ioe}")
            Log.d("_xxx", "Fail to generate PDF file..")

            return false

        }
    }
}
