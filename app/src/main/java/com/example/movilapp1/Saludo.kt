package com.example.movilapp1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Saludo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saludo)

        val tv_saludo = findViewById<TextView>(R.id.tv_saludo)

        val nombreUsuario = intent.getStringExtra("user").toString()

        tv_saludo.text = "Bienvenido $nombreUsuario"

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@Saludo, MenuInicio::class.java)
            intent.putExtra("user", nombreUsuario)
            startActivity(intent)
            finish()
        }, 3000) // Redirige al usuario al menú después de 3 segundos
    }
}
