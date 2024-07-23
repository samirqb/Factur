package com.samirquiceno.micuentadecobro.tools


class RestriccionesDeCamposDeEntradas {

    companion object{

        val numericRegex = Regex("[^0-9]")
        val stringRegex = Regex("[^a-zA-Z]")
        val stringAndIntRegex = Regex("[^a-zA-Z0-9]")

        //@JvmStatic
        fun soloNumeros(it:String):String {
            return numericRegex.replace(it, "")
        }

        fun soloLetras(it:String):String {
            return stringRegex.replace(it, "")
        }

        fun primerCharacterStringOrInt(it: String):String{
            return stringAndIntRegex.replaceFirst(it,"")
        }
    }
}