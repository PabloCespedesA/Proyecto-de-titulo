package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class Registro_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        /*Declaracion de variables */
        val til_usuario_registro = findViewById<TextInputLayout>(R.id.til_nombre_usuario_registro)
        val til_negocio_registro = findViewById<TextInputLayout>(R.id.til_nombre_negocio_registro)
        val til_email = findViewById<TextInputLayout>(R.id.til_email)
        val til_pass_registro = findViewById<TextInputLayout>(R.id.til_pass_registro)
        val til_pass_registro_confirmacion =
            findViewById<TextInputLayout>(R.id.til_confirmar_contraseña)
        val btn_registrame = findViewById<Button>(R.id.btn_registrame)
        val btn_inicia_sesion = findViewById<Button>(R.id.btn_inicia_sesion)

        btn_registrame.setOnClickListener {
            var usuario_registro = til_usuario_registro.editText?.text.toString()
            var negocio_registro = til_negocio_registro.editText?.text.toString()
            var email_registro = til_email.editText?.text.toString()
            var pass_registro = til_pass_registro.editText?.text.toString()
            var pass_confirm_registro = til_pass_registro_confirmacion.editText?.text.toString()
            Log.i("DEBUG VAR","usuario: "+usuario_registro+"negocio: "+negocio_registro+"email: "+email_registro+"password: "+pass_registro+"pass_confirm: "+pass_confirm_registro)

            //Validaciones
            val validate = Validate()
            if (validate.validarNulo(usuario_registro)) til_usuario_registro.error =
                getString(R.string.error_campo_vacio) else til_usuario_registro.error = ""
            if (validate.validarNulo(negocio_registro)) til_negocio_registro.error =
                getString(R.string.error_campo_vacio) else til_negocio_registro.error = ""
            if (validate.validarNulo(email_registro)) til_email.error =
                getString(R.string.error_campo_vacio) else til_email.error = ""
            if (validate.validarNulo(pass_registro)) til_pass_registro.error =
                getString(R.string.error_campo_vacio) else til_pass_registro.error = ""
            if (validate.validarNulo(pass_confirm_registro)) til_pass_registro_confirmacion.error =
                getString(R.string.error_campo_vacio) else til_pass_registro_confirmacion.error = ""

            if (!validate.validarNulo(usuario_registro))
                if (!validate.validarNulo(negocio_registro))
                    if (!validate.validarNulo(email_registro) && validate.validarCorreo(email_registro))
                        if (!validate.validarNulo(pass_registro))
                            if (!validate.validarNulo(pass_confirm_registro)) {
                                val intent = Intent(this@Registro_usuario, MenuInicio::class.java)
                                startActivity(intent)
                            }

            btn_inicia_sesion.setOnClickListener {
                val intent = Intent(this@Registro_usuario, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}