package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDataBase.Db

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar base de datos
        val room = Room.databaseBuilder(this, Db::class.java,"database-tiendita").allowMainThreadQueries().build()

        //Declaramos las variables con los Id correspondientes

        val til_usuario1 = findViewById<TextInputLayout>(R.id.til_usuario1)
        val til_clave1 = findViewById<TextInputLayout>(R.id.til_clave1)
        val btn_login = findViewById<Button>(R.id.btn_login)
        val btn_registrarAqui = findViewById<Button>(R.id.btn_registrarAqui)

        Log.i("DEBUG VAR","til_usuario1:"+til_usuario1+"til_clave1"+til_clave1)

        //Listener de botones
        btn_login.setOnClickListener {
            val user = til_usuario1.editText?.text.toString()
            val pass = til_clave1.editText?.text.toString()

            //Validacion
            lifecycleScope.launch {
                val response = room.daoUsuario().login(user,pass)
                if (response.size == 1) {
                    Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_LONG).show()
                    val intent = Intent (this@MainActivity,MenuInicio::class.java)
                    startActivity(intent)
                }else til_clave1.error = "Usuario o contraseña incorrecta"
            }


        }

        btn_registrarAqui.setOnClickListener {
            val intent = Intent (this@MainActivity,Registro_usuario::class.java)
            startActivity(intent)
        }


    }



    //Ciclo de vida

    override fun onStart() {
        super.onStart()
        Log.i("Debug VAR","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Debug VAR","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Debug VAR","onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Debug VAR","onDestroy")
    }

}