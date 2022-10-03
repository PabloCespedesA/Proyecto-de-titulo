package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity_confirmacion_eliminar_listado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacion_eliminar_listado)

        val btn_eliminar_listado = findViewById<Button>(R.id.btn_confirmar_eliminar_listado)
        val btn_deshacer_eliminar= findViewById<Button>(R.id.btn_deshacer_eliminar_listado)

        btn_eliminar_listado.setOnClickListener {
            val intent = Intent (this@activity_confirmacion_eliminar_listado,activity_eliminar_listado::class.java)
            startActivity(intent)
        }

        btn_deshacer_eliminar.setOnClickListener {
            val intent = Intent (this@activity_confirmacion_eliminar_listado,agregar_listado_compra::class.java)
            startActivity(intent)
        }
    }
}