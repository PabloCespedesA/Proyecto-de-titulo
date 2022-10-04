package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaramos las variables con los Id correspondientes

        val til_usuario1 = findViewById<TextInputLayout>(R.id.til_usuario1)
        val til_clave1 = findViewById<TextInputLayout>(R.id.til_clave1)
        val btn_login = findViewById<Button>(R.id.btn_login)
        val btn_registrarAqui = findViewById<Button>(R.id.btn_registrarAqui)

        //Listener de botones
        btn_login.setOnClickListener {
            var user = til_usuario1.editText?.text.toString()
            var pass = til_clave1.editText?.text.toString()
            Toast.makeText(this@MainActivity,user+" "+pass,Toast.LENGTH_SHORT).show()

            //Validaciones
            val validate = Validate()
            if(validate.validarNulo(user)) til_usuario1.error = getString(R.string.error_campo_vacio) else til_usuario1.error = ""
            if(validate.validarNulo(pass)) til_clave1.error = getString(R.string.error_campo_vacio) else til_clave1.error = ""

            val intent = Intent (this@MainActivity,MenuInicio::class.java)
            startActivity(intent)

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