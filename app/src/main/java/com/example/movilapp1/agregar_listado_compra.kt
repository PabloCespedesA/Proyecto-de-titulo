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
import roomDataBase.entity.ListaCompra

class agregar_listado_compra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_listado_compra)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()

        val til_listaCompraNueva = findViewById<TextInputLayout>(R.id.til_listaCompraNueva)
        val til_detalleListadoCompra = findViewById<TextInputLayout>(R.id.til_detalleListadoCompra)
        val btn_guardar_agregar_listado_compra = findViewById<Button>(R.id.btn_guardar_agregar_listado_compra)
        val btn_atras_agregar_listado_compra = findViewById<Button>(R.id.btn_atras_agregar_listado_compra)


        //Recuperamos la variable del intent
        val cliente:String=intent.getStringExtra("cliente").toString()

        btn_atras_agregar_listado_compra.setOnClickListener {
            val intent = Intent (this@agregar_listado_compra,Listado_de_compras::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
            finish()
        }

        btn_guardar_agregar_listado_compra.setOnClickListener {
            // Capturar valores

            val nombre_listadoCompra = til_listaCompraNueva.editText?.text.toString()
            val detalle_listadoCompra = til_detalleListadoCompra.editText?.text.toString()
            var id: Long = 0
            val listadoCompra = ListaCompra(nombre_listadoCompra, detalle_listadoCompra, cliente)

            Log.i(
                "DEBUG VAR",
                "nombre_listadoCompra :" + nombre_listadoCompra + "detalle_listadoCompra: " + detalle_listadoCompra + "cliente: " + cliente
            )

            //Validaciones
            val validate = Validate()
            if(validate.validarNombre(nombre_listadoCompra)) til_listaCompraNueva.error = getString(R.string.error_formato_string) else til_listaCompraNueva.error = ""
            if(validate.validarNulo(detalle_listadoCompra)) til_detalleListadoCompra.error = getString(R.string.error_campo_vacio) else til_detalleListadoCompra.error = ""

            if (!validate.validarNulo(nombre_listadoCompra) && !validate.validarNulo(detalle_listadoCompra)) {
                lifecycleScope.launch {
                    id = room.daoListaCompra().agregarListaCompra(listadoCompra)
                    if (id > 0) {
                        Toast.makeText(
                            this@agregar_listado_compra,
                            "Lista registrada correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@agregar_listado_compra, Listado_de_compras::class.java)
                        intent.putExtra("cliente", cliente)
                        startActivity(intent)
                    }
                }
            }

        }
    }
}