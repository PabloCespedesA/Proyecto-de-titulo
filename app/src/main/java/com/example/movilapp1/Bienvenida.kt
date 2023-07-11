package com.example.movilapp1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout

class Bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        val view_agregar_listado = findViewById<ConstraintLayout>(R.id.view_bienvenida)
        view_agregar_listado.setOnTouchListener { v, event ->
            if (currentFocus != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }
            v.performClick()
            true
        }

        val tilBienvenida = findViewById<TextInputLayout>(R.id.til_bienvenida)
        val btnIngresar = findViewById<Button>(R.id.btn_ingresar)

        btnIngresar.setOnClickListener {
            val nombreUsuario = tilBienvenida.editText?.text.toString()
            val intent = Intent(this, Saludo::class.java) // Aquí está el cambio, en lugar de MenuInicio, debe ser Saludo
            intent.putExtra("user", nombreUsuario)
            startActivity(intent)
            finish() // Cierra la actividad de Bienvenida una vez que el usuario ha ingresado su nombre
        }
    }
}
