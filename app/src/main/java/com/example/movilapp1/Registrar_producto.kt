package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.google.android.material.textfield.TextInputLayout

class Registrar_producto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)

        val sp_datos_tipos = findViewById<Spinner>(R.id.sp_datos_tipos)
        val til_nombre_producto = findViewById<TextInputLayout>(R.id.til_nombre_producto)
        val til_cantidad = findViewById<TextInputLayout>(R.id.til_cantidad)
        val til_precio_producto = findViewById<TextInputLayout>(R.id.til_precio_producto)
        val til_fecha_vencimiento = findViewById<TextInputLayout>(R.id.til_fecha_vencimiento)
        val til_ubicacion_producto = findViewById<TextInputLayout>(R.id.til_ubicacion_producto)
        val btn_agregarProducto = findViewById<Button>(R.id.btn_agregarProducto)

        //poblar lista
        //Opciones qeu tendrá la lista
        var lista = listOf("Seleccione una opción","Artículos de aseo","Dulces","Fiambrería","Fideos","Arroz","Sal","Azúcar","Dulces","Frutas y Verduras", "Útiles Escolares")
        var adaptador = ArrayAdapter(this@Registrar_producto,android.R.layout.simple_spinner_dropdown_item,lista)
                sp_datos_tipos.adapter = adaptador



        //listening

        btn_agregarProducto.setOnClickListener {
            //capturar valores
            var tipo_productos = sp_datos_tipos.selectedItem.toString() //obtener valor del spinner
            var nombre_producto = til_nombre_producto.editText?.text.toString()
            var cantidad_producto = til_cantidad.editText?.text.toString()
            var precio_producto = til_precio_producto.editText?.text.toString()
            var vencimiento_producto = til_fecha_vencimiento.editText?.text.toString()
            var ubicacion_producto = til_ubicacion_producto.editText?.text.toString()
            Log.i("DEBUG VAR","tipo_productos:"+tipo_productos+"nombre_producto:"+nombre_producto+"cantidad_producto:"+cantidad_producto+"precio_producto:"+precio_producto+"fecha_vencimiento:"+vencimiento_producto+"ubicacion_producto:"+ubicacion_producto)

            val intent = Intent (this@Registrar_producto,inventario_todos_los_productos::class.java)
            startActivity(intent)
        }

        til_fecha_vencimiento.editText?.setOnClickListener { v -> showDatePickerDialog() }
    }

    //Función para poner la fecha seleccionada en el campo de texto
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day,month,year) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    //Función para poner la fecha seleccionada en el campo de texto
    private fun onDateSelected(day:Int,month:Int,year:Int){
        val til_fecha_vencimiento = findViewById<TextInputLayout>(R.id.til_fecha_vencimiento)
        var daySelected = "$day"
        var monthSelected = (month+1).toString()
        if(day<10){daySelected = "0$day"}
        if((month+1)<10){monthSelected = "0"+(month+1).toString()}
        til_fecha_vencimiento.editText?.setText("$daySelected/$monthSelected/$year")
    }
}