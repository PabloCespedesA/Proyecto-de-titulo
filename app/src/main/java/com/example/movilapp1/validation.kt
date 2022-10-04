package com.example.movilapp1

import android.util.Patterns
import java.util.regex.Pattern


class Validate {
    /*validar texto nulo*/
    fun validarNulo (texto: String):Boolean{
        return texto.equals("")||texto.length==0
    }
    /*validar formato nombre con expresion regular*/
    fun validarNombre (nombre: String):Boolean{
        val pattern = Pattern.compile("^[a-zA-Z]+\$")
        return pattern.matcher(nombre).matches()
    }
    /*validar correo*/
    fun validarCorreo (correo: String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }
}