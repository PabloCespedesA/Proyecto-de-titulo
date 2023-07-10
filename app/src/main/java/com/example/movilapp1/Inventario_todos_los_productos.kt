package com.example.movilapp1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import roomDataBase.Db
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException
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
        val tv_user = findViewById<TextView>(R.id.tv_user_inventario_todos_los_productos)
        //Recuperamos la variable del intent
        cliente = intent.getStringExtra("cliente").toString()
        tv_user.setText("Hola Usuario ${cliente}")

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

        lifecycleScope.launch {
            actualizarListaProductos("")
        }

        btn_atras_inventario_todos.setOnClickListener {
            val intent = Intent (this@Inventario_todos_los_productos,MenuInicio::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
            finish()
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

    override fun onResume() {
        super.onResume()
        //Inicializar la base de datos
        room = Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
            .build()
        actualizarListaProductos("")
    }

    private fun actualizarListaProductos(textoBusqueda: String) {
        productos.clear()
        lifecycleScope.launch {
            val respuesta = room.daoProducto().obtenerProductosPorBusqueda(cliente, textoBusqueda)

            // Separar y ordenar los productos próximos a vencer y los que no lo están
            val nearToExpireProducts = respuesta.filter { it.fecha?.let { fecha -> isNearToExpire(fecha) } ?: false }.sortedBy { it.nombre }

            val notNearToExpireProducts = respuesta.filter { it.fecha?.let { fecha -> !isNearToExpire(fecha) } ?: true }.sortedBy { it.nombre }

            val sortedProducts = nearToExpireProducts + notNearToExpireProducts

            // Llenar la lista con los nombres de los productos
            productos.clear()
            for (producto in sortedProducts) {
                productos.add(producto.nombre.toString())
            }

            // Crear y asignar el adaptador
            val arrayAdapterListView = object : ArrayAdapter<String>(this@Inventario_todos_los_productos, android.R.layout.simple_list_item_1, productos) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    val textView = view.findViewById<TextView>(android.R.id.text1)

                    if (isNearToExpire(sortedProducts[position].fecha ?: "01/01/2100")) {
                        textView.setTextColor(Color.RED)
                    } else {
                        textView.setTextColor(Color.BLACK)
                    }

                    return view
                }
            }
            lv_todos.adapter = arrayAdapterListView
        }
    }
    fun isNearToExpire(expirationDate: String): Boolean {
        val format = SimpleDateFormat("dd/MM/yyyy")
        try {
            val expireDate = format.parse(expirationDate)
            val today = Date()
            val nextWeek = Date(today.time + 7 * 24 * 60 * 60 * 1000)
            return expireDate.before(nextWeek)
        } catch (e: ParseException) {
            // Aquí puedes manejar el error como prefieras. Por ejemplo, puedes imprimir el error en el log:
            Log.e("Error", "Fecha no válida: $expirationDate", e)
            // Y puedes decidir qué hacer en caso de error. Aquí simplemente estoy retornando false:
            return false
        }
    }

}

