package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Registro_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        val btn_registrame = findViewById<Button>(R.id.btn_registrame)
        val btn_inicia_sesion = findViewById<Button>(R.id.btn_inicia_sesion)

        btn_registrame.setOnClickListener {
            val intent = Intent (this@Registro_usuario,MenuInicio::class.java)
            startActivity(intent)
        }

        btn_inicia_sesion.setOnClickListener {
            val intent = Intent (this@Registro_usuario,MainActivity::class.java)
            startActivity(intent)
        }

    }
}