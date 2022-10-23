package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import roomDataBase.Db

class Inventario_todos_los_productos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario_todos_los_productos)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()

        //referencias id
        val lv_todos = findViewById<ListView>(R.id.lv_todos)
        //Recuperamos la variable del intent
        val mail:String=intent.getStringExtra("mail").toString()

        var arrayAdapterListView: ArrayAdapter<*>
        val productos = ArrayList<String>()
        lifecycleScope.launch {
            var respuesta=room.daoProducto().obtenerProductoUsuario(mail)
            for (indice in respuesta.indices){
                productos.add(respuesta[indice].nombre.toString())
            }
            arrayAdapterListView=ArrayAdapter(this@Inventario_todos_los_productos, android.R.layout.simple_list_item_1,productos)
            lv_todos.adapter = arrayAdapterListView
        }

        //generar accion al presionar
        lv_todos.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Inventario_todos_los_productos,"Escogido ${productos[position]}", Toast.LENGTH_SHORT).show()
                //Redireccionar al presionar
                val intent = Intent(this@Inventario_todos_los_productos,Editar_producto::class.java)
                intent.putExtra("productoSeleccionado","${productos[position]}")
                intent.putExtra("mail",mail)
                startActivity(intent)
            }
        }
    }
}