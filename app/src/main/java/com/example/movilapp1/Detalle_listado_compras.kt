package com.example.movilapp1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDataBase.Db
import roomDataBase.entity.TipoProducto

class Detalle_listado_compras : AppCompatActivity() {

    var listacompraId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_listado_compras)

        val view_agregar_listado = findViewById<ConstraintLayout>(R.id.view_detalle_listado)
        view_agregar_listado.setOnTouchListener { v, event ->
            if (currentFocus != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }
            v.performClick()
            true
        }

        val til_editar_titulo = findViewById<TextInputLayout>(R.id.til_editar_titulo)
        val til_editar_detalle = findViewById<TextInputLayout>(R.id.til_editar_detalle)
        val btn_guardar_detalle = findViewById<Button>(R.id.btn_guardar_detalle_listado_compras)
        val btn_eliminar_detalle = findViewById<Button>(R.id.btn_deshacer_eliminar_listado)
        val btn_atras_detalle_listado = findViewById<Button>(R.id.btn_atras_detalle_listado)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()
        val cliente: String = intent.getStringExtra("cliente").toString()
        val nombre: String = intent.getStringExtra("nombre").toString()

        btn_atras_detalle_listado.setOnClickListener {
            val intent = Intent (this@Detalle_listado_compras,Listado_de_compras::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
            finish()
        }

        //poblar lista

        lifecycleScope.launch {
            val respuesta = room.daoListaCompra().obtenerListaCompraPorNombre(nombre, cliente)
            if (respuesta.size == 1) {
                for (elemento in respuesta) {

                    til_editar_titulo.editText?.setText(elemento.nombre.toString())
                    til_editar_detalle.editText?.setText(elemento.detalle.toString())
                    listacompraId = elemento.id
                }
            }
        }



        btn_guardar_detalle.setOnClickListener {
            // Recoge los valores del formulario
            val nombre = til_editar_titulo.editText?.text.toString()
            val detalle = til_editar_detalle.editText?.text.toString()

            lifecycleScope.launch {
                // Llama a la función de actualización
                var result = room.daoListaCompra().actualizarListaCompra(nombre, detalle, listacompraId)

                // Comprueba si la actualización fue exitosa
                if (result > 0) {
                    Toast.makeText(this@Detalle_listado_compras, "Lista actualizada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Detalle_listado_compras, "Error al actualizar la lista", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent (this@Detalle_listado_compras,Listado_de_compras::class.java)
                intent.putExtra("cliente",cliente)
                startActivity(intent)
            }
        }

        btn_eliminar_detalle.setOnClickListener {

            // Crear el diálogo de confirmación
            AlertDialog.Builder(this)
                .setTitle("Eliminar Lista")
                .setMessage("¿Estás seguro de que quieres eliminar esta Lista?")
                .setPositiveButton("Sí") { dialog, _ ->

                    // Si el usuario hace clic en "Sí", se procede a la eliminación
                    lifecycleScope.launch {
                        val result = room.daoListaCompra().borrarListaCompra(listacompraId)

                        // Comprobar si la eliminación fue exitosa
                        if (result > 0) {
                            Toast.makeText(this@Detalle_listado_compras, "Lista eliminada con éxito", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Detalle_listado_compras, "Error al eliminar la Lista", Toast.LENGTH_SHORT).show()
                        }

                        // Redirigir al inventario después de eliminar el producto
                        val intent = Intent (this@Detalle_listado_compras, Listado_de_compras::class.java)
                        intent.putExtra("cliente", cliente)
                        startActivity(intent)
                    }

                    dialog.dismiss() // Cierra el diálogo
                }
                .setNegativeButton("No") { dialog, _ -> // Si el usuario hace clic en "No", simplemente se cierra el diálogo
                    dialog.dismiss()
                }
                .create()
                .show() // Muestra el diálogo

        }
    }
}