package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import roomDataBase.Db

class MenuInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_inicio)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()

        //Declaramos las variables con los Id correspondientes

        val btn_registrarProducto = findViewById<Button>(R.id.btn_registrarProducto)
        val btn_gestionar = findViewById<Button>(R.id.btn_gestionar)
        val btn_listado = findViewById<Button>(R.id.btn_listado)
        val tv_user = findViewById<TextView>(R.id.tv_user)
        var id:Long = 0

        val cliente:String = intent.getStringExtra("user").toString()
        tv_user.setText("Hola Usuario ${cliente}")


        btn_registrarProducto.setOnClickListener {
            val intent = Intent (this@MenuInicio,Registrar_producto::class.java)
         //   intent.putExtra("cliente",cliente)
            startActivity(intent)
        }

        btn_gestionar.setOnClickListener {
            val intent = Intent (this@MenuInicio,Inventario_todos_los_productos::class.java)
         //   intent.putExtra("cliente",cliente)
            startActivity(intent)
        }

        btn_listado.setOnClickListener {
            val intent = Intent (this@MenuInicio,Listado_de_compras::class.java)
        //    intent.putExtra("cliente",cliente)
            startActivity(intent)
        }
    }
}