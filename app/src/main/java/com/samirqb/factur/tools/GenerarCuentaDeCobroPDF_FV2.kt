package com.samirqb.factur.tools

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
import com.samirqb.factur.R
import com.samirqb.factur.models.CuentaDeCobroEntity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GenerarCuentaDeCobroPDF_FV2(
    private var context: Context,
    private var mCuentaDeCobroEntity: CuentaDeCobroEntity,
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
        var imagen_png: Bitmap
        var scaledbmp: Bitmap
        var escala = 3

        /** si la uri de la imagen a imprimir es null o vacia, el sistema toma una imagen predeterminada
         * del sistema (drawable/factur_arte_isologotipo_140px_x_100px) y la imprime como Imagen corporativa  */
        if( mCuentaDeCobroEntity.imagen_corporativa_uri.lastPathSegment == "imagen_corporativa"){
            imagen_png = BitmapFactory.decodeFile(mCuentaDeCobroEntity.imagen_corporativa_uri.path)
            scaledbmp = Bitmap.createScaledBitmap(imagen_png, imagen_png.width, imagen_png.height, false)
        } else{
            imagen_png = BitmapFactory.decodeResource(context.resources, R.drawable.factur_arte_isologotipo_140px_x_100px)
            scaledbmp = Bitmap.createScaledBitmap(imagen_png, imagen_png.width / escala , imagen_png.height / escala , false)
        }

        var eje_x = 50F
        var eje_y = 45F
        contentPag.drawBitmap(scaledbmp,eje_x,eje_y,set_drawable)


        /******************************************/

        /** Set style font to tittle **/
        var set_string = Paint()

        set_string.setTextSize(21F)
        set_string.fontVariationSettings
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD)
        )
        set_string.setColor(Color.BLACK)
        //set_string.textAlign = Paint.Align.RIGHT
        set_string.textAlign = Paint.Align.LEFT

        /***** D A T O S / C A B E C E R A   D E L  R E P O R T E *****/
        /** TITULO **/
        var tipo_reporte = mCuentaDeCobroEntity.tipo_reporte
        eje_x = 220F
        eje_y = 60F
        contentPag.drawText(tipo_reporte,eje_x,eje_y,set_string)

        /** LABEL - FECHA Y HORA DEL REPORTE **/
        var fecha_hora_reporte = mCuentaDeCobroEntity.fecha_hora_generacion_reporte
        eje_y = 75F
        set_string.setTextSize(11F)
        contentPag.drawText("Fecha y hora: ${fecha_hora_reporte}",eje_x,eje_y,set_string)





        /** DATOS PRESTADOR DEL SERVICIO **********************************************************/
        set_string.setTextSize(12F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.NORMAL)
        )

        /** UBICACION PRESTADOR DE SERVICIO **/
        var nombre_ps = "Yo "+mCuentaDeCobroEntity.mDatosPrestadorServicioEntity.nombre+","
        eje_x = 40F
        eje_y = 185F
        contentPag.drawText(nombre_ps,eje_x,eje_y,set_string)

        /** IDENTIFICACION PRESTADOR DE SERVICIO **/
        var identificacion_ps = "identificado/a con "+mCuentaDeCobroEntity.mDatosPrestadorServicioEntity.identificacion
        eje_y = 200F
        contentPag.drawText(identificacion_ps,eje_x,eje_y,set_string)

        //domiciliado/a en:
        var ubicacion_ps = "domiciliado/a en "+mCuentaDeCobroEntity.mDatosPrestadorServicioEntity.ubicacion
        eje_y = 215F
        contentPag.drawText(ubicacion_ps,eje_x,eje_y,set_string)


        //manifiesto que
        var nombre_rs = "manifiesto que "+mCuentaDeCobroEntity.mDatosReceptorServicioEntity.nombre
        eje_y = 245F
        contentPag.drawText(nombre_rs,eje_x,eje_y,set_string)

        //identificada con
        var identificacion_rs = "identificado/a con "+mCuentaDeCobroEntity.mDatosReceptorServicioEntity.identificacion+","
        eje_y = 260F
        contentPag.drawText(identificacion_rs,eje_x,eje_y,set_string)

        //domiciliado/a en:
        var ubicacion_rs = "domiciliado/a en "+mCuentaDeCobroEntity.mDatosReceptorServicioEntity.ubicacion
        eje_y = 275F
        contentPag.drawText(ubicacion_rs,eje_x,eje_y,set_string)

        //me adeuda la suma de:
        var valor_total_cc = "me adeuda la suma de "+mNumFormat.format(mCuentaDeCobroEntity.total_suma_servicios)
        eje_y = 290F
        contentPag.drawText(valor_total_cc,eje_x,eje_y,set_string)

        //por los siguientes conceptos
        var servicios_contratados_cc = "por los siguientes conceptos: "
        eje_y = 305F
        contentPag.drawText(servicios_contratados_cc,eje_x,eje_y,set_string)


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
        eje_x = 55F
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

        mCuentaDeCobroEntity.mServicioEntity.forEach {

            // CANTIDAD UNIDADES SERVICIO
            var cantidad_rs = it?.cantidad.toString()
            eje_x = 65F
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
        var total_suma_servicios_rs = mNumFormat.format(mCuentaDeCobroEntity.total_suma_servicios)
        eje_x = 570F
        set_string.textAlign = Paint.Align.RIGHT
        contentPag.drawText( total_suma_servicios_rs,eje_x,eje_y,set_string )




        /************ FIRMA PROVEEDOR SERVICIOS **************/

        /** Set style font to tittle **/
        set_string.textAlign = Paint.Align.LEFT
        set_string.setTextSize(11F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD
            ))


        /** NOMBRE PROVEEDOR SERVICIOS **/
        nombre_ps = mCuentaDeCobroEntity.mDatosPrestadorServicioEntity.nombre
        eje_x = 45F
        eje_y = 730F
        contentPag.drawText(nombre_ps,eje_x,eje_y,set_string)

        set_string.setTextSize(9F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.NORMAL
            ))

        /** TELEFONO PRESTADOR DE SERVICIO **/
        var telefono_ps = "Tel: "+mCuentaDeCobroEntity.mDatosPrestadorServicioEntity.telefono
        eje_y = 740F
        contentPag.drawText(telefono_ps,eje_x,eje_y,set_string)

        /** EMAIL PRESTADOR DE SERVICIO **/
        var email_ps = "Email: "+mCuentaDeCobroEntity.mDatosPrestadorServicioEntity.email
        eje_y = 750F
        contentPag.drawText(email_ps,eje_x,eje_y,set_string)



        /*************************** D I S C L A I M E R ******************************************/

        /** Set style font to tittle **/
        //set_string.textAlign = Paint.Align.CENTER
        set_string.textAlign = Paint.Align.LEFT
        set_string.setTextSize(9F)
        set_string.setTypeface(
            Typeface.create(
                Typeface.MONOSPACE,
                Typeface.BOLD_ITALIC
            ))


        var disclaimer_txt_l1 = "Para efectos de retención en la fuente solicito se me aplique la tabla de retención establecida"
        //eje_x = 45F
        eje_y = 785F
        contentPag.drawText( disclaimer_txt_l1,eje_x,eje_y,set_string )

        var disclaimer_txt_l2 = "en el artículo 383 del E.T, para lo cual certifico bajo la gravedad de juramento que no voy a"
        eje_y = 795F
        contentPag.drawText( disclaimer_txt_l2,eje_x,eje_y,set_string )

        var disclaimer_txt_l3 = "vincular costos al ingreso generado por esta prestación de servicio."
        eje_y = 805F
        contentPag.drawText( disclaimer_txt_l3,eje_x,eje_y,set_string )

        /************ final disclainer ***************/


        document.finishPage(page)

        var downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        var nombre_archivo_raw = "CC${mCuentaDeCobroEntity.numero_consecutivo}_${mCuentaDeCobroEntity.fecha_hora_generacion_reporte}.pdf"
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
