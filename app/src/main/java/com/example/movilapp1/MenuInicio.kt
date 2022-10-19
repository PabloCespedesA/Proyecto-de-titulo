package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_inicio)

        //Declaramos las variables con los Id correspondientes


        val btn_registrarProducto = findViewById<Button>(R.id.btn_registrarProducto)
        val btn_gestionar = findViewById<Button>(R.id.btn_gestionar)
        val btn_listado = findViewById<Button>(R.id.btn_listado)


        btn_registrarProducto.setOnClickListener {
            val intent = Intent (this@MenuInicio,Registrar_producto::class.java)
            startActivity(intent)
        }

        btn_gestionar.setOnClickListener {
            val intent = Intent (this@MenuInicio,Menu_inventario_productos::class.java)
            startActivity(intent)
        }

        btn_listado.setOnClickListener {
            val intent = Intent (this@MenuInicio,Listado_de_compras::class.java)
            startActivity(intent)
        }

    }
}