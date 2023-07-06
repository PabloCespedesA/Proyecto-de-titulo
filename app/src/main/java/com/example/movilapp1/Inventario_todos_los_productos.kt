package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import roomDataBase.Db

class Inventario_todos_los_productos : AppCompatActivity() {

    private lateinit var room: Db
    private lateinit var cliente: String
    private lateinit var lv_todos: ListView
    private val productos = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario_todos_los_productos)

        //Inicializar la base de datos
                room = Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                    .build()


        //referencias id

        val btn_atras_inventario_todos = findViewById<Button>(R.id.btn_atras_inventario_todos)
        val btn_agregar_producto_listado = findViewById<Button>(R.id.btn_agregar_producto_listado)
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.seach_buscador_productos)

        //Recuperamos la variable del intent
        cliente = intent.getStringExtra("cliente").toString()

        lv_todos = findViewById<ListView>(R.id.lv_todos)

        //Buscador
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                actualizarListaProductos(newText)
                return true
            }
        })

        actualizarListaProductos("")

        btn_atras_inventario_todos.setOnClickListener {
            val intent = Intent (this@Inventario_todos_los_productos,MenuInicio::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
        }

        btn_agregar_producto_listado.setOnClickListener {
            val intent = Intent (this@Inventario_todos_los_productos,Registrar_producto::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
        }


        //generar accion al presionar
        lv_todos.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Inventario_todos_los_productos,"Escogido ${productos[position]}", Toast.LENGTH_SHORT).show()
                //Redireccionar al presionar
                val intent = Intent(this@Inventario_todos_los_productos,Editar_producto::class.java)
                intent.putExtra("nombre","${productos[position]}")
                intent.putExtra("cliente",cliente)
                startActivity(intent)
            }
        }
    }

    private fun actualizarListaProductos(textoBusqueda: String) {
        productos.clear()
        lifecycleScope.launch {
            val respuesta = room.daoProducto().obtenerProductosPorBusqueda(cliente, textoBusqueda)
            for (indice in respuesta.indices){
                productos.add(respuesta[indice].nombre.toString())
            }
            val arrayAdapterListView = ArrayAdapter(this@Inventario_todos_los_productos, android.R.layout.simple_list_item_1, productos)
            lv_todos.adapter = arrayAdapterListView
        }
    }

}