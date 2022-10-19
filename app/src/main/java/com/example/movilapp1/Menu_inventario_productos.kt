package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu_inventario_productos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_inventario_productos)

        //Declaración de variables con los Id correspondientes

        val btn_productos = findViewById<Button>(R.id.btn_productos)
        val btn_tipo_producto = findViewById<Button>(R.id.btn_tipo_producto)


        btn_productos.setOnClickListener {
            val intent =
                Intent(this@Menu_inventario_productos, Inventario_todos_los_productos::class.java)
            startActivity(intent)
        }

        btn_tipo_producto.setOnClickListener {
            val intent = Intent(this@Menu_inventario_productos, inventario_tipo::class.java)
            startActivity(intent)
        }
    }
}