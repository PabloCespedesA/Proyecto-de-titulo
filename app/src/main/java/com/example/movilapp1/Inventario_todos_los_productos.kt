package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Text

class Inventario_todos_los_productos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario_todos_los_productos)

        //referencias id
        val lv_todos = findViewById<ListView>(R.id.lv_todos)




        val arrayAdapter: ArrayAdapter<*>
        val recetas = arrayOf("Receta1", "Receta2", "Receta3", "Receta4" )
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,recetas)
        lv_todos.adapter = arrayAdapter

        //generar accion al presionar
        lv_todos.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Inventario_todos_los_productos,"Escogido ${recetas[position]}", Toast.LENGTH_SHORT).show()
                //Redireccionar al presionar
                val intent = Intent(this@Inventario_todos_los_productos,Editar_producto::class.java)
                intent.putExtra("recetaSeleccionada","${recetas[position]}")
                startActivity(intent)
            }
        }
    }
}