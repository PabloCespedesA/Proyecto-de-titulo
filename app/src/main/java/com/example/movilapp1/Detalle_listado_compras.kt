package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Detalle_listado_compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_listado_compras)

        val btn_guardar_detalle = findViewById<Button>(R.id.btn_guardar_detalle_listado_compras)
        val btn_eliminar_detalle = findViewById<Button>(R.id.btn_deshacer_eliminar_listado)

        btn_guardar_detalle.setOnClickListener {
            val intent = Intent (this@Detalle_listado_compras,Listado_de_compras::class.java)
            startActivity(intent)
        }

        btn_eliminar_detalle.setOnClickListener {
            val intent = Intent (this@Detalle_listado_compras,activity_confirmacion_eliminar_listado::class.java)
            startActivity(intent)
        }
    }
}