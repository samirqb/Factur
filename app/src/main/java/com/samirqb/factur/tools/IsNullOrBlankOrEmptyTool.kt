package com.samirqb.factur.tools

fun IsNullOrBlankOrEmptyTool(isNullOrBlankOrEmpty:String):Boolean{
    return if (
        isNullOrBlankOrEmpty.isNullOrEmpty() ||
        isNullOrBlankOrEmpty.isNullOrBlank() ) false else true
}