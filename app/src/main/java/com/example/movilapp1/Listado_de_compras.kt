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

class Listado_de_compras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_de_compras)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()

        //referencias id
        val lv_todasListaCompras = findViewById<ListView>(R.id.lv_todasListaCompras)
        //Recuperamos la variable del intent
        val cliente:String=intent.getStringExtra("cliente").toString()
        val btn_atras_listado_compras = findViewById<Button>(R.id.btn_atras_listado_compras)

        btn_atras_listado_compras.setOnClickListener {
            val intent = Intent (this@Listado_de_compras,MenuInicio::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
        }

        //Llenado de lista
        var arrayAdapterListView: ArrayAdapter<*>
        val listaCompras = ArrayList<String>()
        lifecycleScope.launch {
            val respuesta = room.daoListaCompra().obtenerListaCompra(cliente)
            for (indice in respuesta.indices){
                listaCompras.add(respuesta[indice].nombre.toString())
            }
            arrayAdapterListView= ArrayAdapter(this@Listado_de_compras, android.R.layout.simple_list_item_1,listaCompras)
            lv_todasListaCompras.adapter = arrayAdapterListView
        }

        //generar accion al presionar
        lv_todasListaCompras.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Listado_de_compras,"Escogido ${listaCompras[position]}", Toast.LENGTH_SHORT).show()
                //Redireccionar al presionar
                val intent = Intent(this@Listado_de_compras,Detalle_listado_compras::class.java)
                intent.putExtra("nombre","${listaCompras[position]}")
                intent.putExtra("cliente",cliente)
                startActivity(intent)
            }
        }

        val btn_agregarListaCompra = findViewById<Button>(R.id.btn_agregarListaCompra)

        btn_agregarListaCompra.setOnClickListener {
            val intent = Intent (this@Listado_de_compras,agregar_listado_compra::class.java)
            startActivity(intent)
        }


    }
}