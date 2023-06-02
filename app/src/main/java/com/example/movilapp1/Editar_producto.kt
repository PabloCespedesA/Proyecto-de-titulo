package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDataBase.Db
import roomDataBase.entity.TipoProducto

class Editar_producto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        val sp_datos_tipos_editar = findViewById<Spinner>(R.id.sp_datos_tipos_editar)
        val til_nombre_producto_editar =
            findViewById<TextInputLayout>(R.id.til_nombre_producto_editar)
        val til_cantidad_editar = findViewById<TextInputLayout>(R.id.til_cantidad_editar)
        val til_precio_editar = findViewById<TextInputLayout>(R.id.til_precio_editar)
        val til_vencimiento_editar = findViewById<TextInputLayout>(R.id.til_vencimiento_editar)
        val til_ubicacion_editar = findViewById<TextInputLayout>(R.id.til_ubicacion_editar)
        val txt_usuarioActual = findViewById<TextView>(R.id.txt_usuarioActual)
        val btn_guardar_editar = findViewById<Button>(R.id.btn_guardar_editar)
        val btn_eliminar_editar = findViewById<Button>(R.id.btn_eliminar_editar)
        val tv_id = findViewById<TextView>(R.id.tv_id)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()
        val cliente: String = intent.getStringExtra("cliente").toString()
        val nombre: String = intent.getStringExtra("nombre").toString()

        var id:Long = 0
        txt_usuarioActual.setText("Usuario ${cliente}")
        tv_id.setText("Producto: ${nombre}")

        //poblar lista
        //Opciones que tendrá la lista

        val lista = TipoProducto.values().map { it.tipo } // Crea una lista de Strings a partir del Enum
        val adaptador = ArrayAdapter(this@Editar_producto, android.R.layout.simple_spinner_dropdown_item, lista)
        sp_datos_tipos_editar.adapter = adaptador


        lifecycleScope.launch {
            val respuesta = room.daoProducto().obtenerProductoPorNombre(nombre, cliente)
            if (respuesta.size == 1) {
                for (elemento in respuesta) {
                    // Establecer el tipo de producto seleccionado en el Spinner
                    val tipoProductoIndex = lista.indexOf(elemento.tipos.toString())
                    //ejecutar la actualización del Spinner
                    runOnUiThread {
                        sp_datos_tipos_editar.setSelection(tipoProductoIndex)
                    }
                    til_nombre_producto_editar.editText?.setText(elemento.nombre.toString())
                    til_cantidad_editar.editText?.setText(elemento.cantidad.toString())
                    til_precio_editar.editText?.setText(elemento.precio.toString())
                    til_vencimiento_editar.editText?.setText(elemento.fecha.toString())
                    til_ubicacion_editar.editText?.setText(elemento.ubicacion.toString())
                    tv_id.setText(elemento.id.toString())
                }
            }
        }
    }
}