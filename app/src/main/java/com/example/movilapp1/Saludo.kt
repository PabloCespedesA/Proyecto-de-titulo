package com.example.movilapp1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class Saludo : AppCompatActivity() {

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saludo)

        val tv_saludo = findViewById<TextView>(R.id.tv_saludo)
        val saludoLayout = findViewById<ConstraintLayout>(R.id.saludoLayout)

        val nombreUsuario = intent.getStringExtra("user").toString()

        tv_saludo.text = "Bienvenido/a $nombreUsuario"

        handler = Handler(Looper.getMainLooper())

        runnable = Runnable {
            val intent = Intent(this@Saludo, MenuInicio::class.java)
            intent.putExtra("user", nombreUsuario)
            startActivity(intent)
            finish()
        }

        handler?.postDelayed(runnable!!, 3000) // Redirige al usuario al menú después de 3 segundos

        saludoLayout.setOnTouchListener { v, event ->
            handler?.removeCallbacks(runnable!!) // Quita la tarea en caso de que se toque la pantalla
            val intent = Intent(this@Saludo, MenuInicio::class.java)
            intent.putExtra("user", nombreUsuario)
            startActivity(intent)
            finish()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable!!) // Es importante eliminar la tarea cuando la actividad es destruida
    }
}
