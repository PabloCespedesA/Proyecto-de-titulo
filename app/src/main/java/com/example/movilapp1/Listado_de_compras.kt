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

    private lateinit var room: Db
    private lateinit var cliente: String
    private lateinit var lv_todasListaCompras: ListView
    private val listaCompras = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_de_compras)

        //Inicializar la base de datos
        //room = Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries().build()

        //referencias id
        val btn_atras_listado_compras = findViewById<Button>(R.id.btn_atras_listado_compras)

        lv_todasListaCompras = findViewById<ListView>(R.id.lv_todasListaCompras)

        //Recuperamos la variable del intent
        cliente = intent.getStringExtra("cliente").toString()

        btn_atras_listado_compras.setOnClickListener {
            val intent = Intent (this@Listado_de_compras,MenuInicio::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
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

       // cargarListaCompras()

    }
    override fun onResume() {
        super.onResume()
        // Inicializar la base de datos
        room = Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries().build()
        cargarListaCompras()
    }
    private fun cargarListaCompras() {
        listaCompras.clear()
        lifecycleScope.launch {
            try {
                val respuesta = room.daoListaCompra().obtenerListaCompra(cliente)
                for (indice in respuesta.indices) {
                    listaCompras.add(respuesta[indice].nombre.toString())
                }
                val arrayAdapterListView = ArrayAdapter(this@Listado_de_compras, android.R.layout.simple_list_item_1, listaCompras)
                lv_todasListaCompras.adapter = arrayAdapterListView
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@Listado_de_compras, "Error al cargar la lista de compras", Toast.LENGTH_SHORT).show()
            }
        }
    }


}