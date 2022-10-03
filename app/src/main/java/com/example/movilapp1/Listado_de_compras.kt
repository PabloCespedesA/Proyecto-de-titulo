package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Listado_de_compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_de_compras)

        val btn_lis1 = findViewById<Button>(R.id.btn_lis1)
        val btn_plus = findViewById<Button>(R.id.btn_plus)


        btn_lis1.setOnClickListener {
            val intent = Intent (this@Listado_de_compras,detalle_listado_compras::class.java)
            startActivity(intent)
        }

        btn_plus.setOnClickListener {
            val intent = Intent (this@Listado_de_compras,agregar_listado_compra::class.java)
            startActivity(intent)
        }

    }
}