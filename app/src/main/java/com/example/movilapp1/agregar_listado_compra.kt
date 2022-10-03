package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class agregar_listado_compra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_listado_compra)


        val btn_guardar = findViewById<Button>(R.id.btn_guardar_agregar_listado_compra)


        btn_guardar.setOnClickListener {
            val intent = Intent (this@agregar_listado_compra,Listado_de_compras::class.java)
            startActivity(intent)
        }
    }
}