package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout

class Editar_producto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)


        val sp_datos_tipos_editar = findViewById<Spinner>(R.id.sp_datos_tipos_editar)
        val til_nombre_producto_editar = findViewById<TextInputLayout>(R.id.til_nombre_producto_editar)
        val til_cantidad_editar = findViewById<TextInputLayout>(R.id.til_cantidad_editar)
        val til_precio_editar = findViewById<TextInputLayout>(R.id.til_precio_editar)
        val til_vencimiento_editar = findViewById<TextInputLayout>(R.id.til_vencimiento_editar)
        val til_ubicacion_editar = findViewById<TextInputLayout>(R.id.til_ubicacion_editar)
        val btn_guardar_editar = findViewById<Button>(R.id.btn_guardar_editar)
        val btn_eliminar_editar = findViewById<Button>(R.id.btn_eliminar_editar)
        //ejemplo para recibir el intent con el dato de la vista anterior y mostrarlo aqui
        val txt_ejemplo =findViewById<TextView>(R.id.txt_ejemplo)
        val intent = getIntent()
        val datoRecibido = intent.getStringExtra("recetaSeleccionada").toString()
        txt_ejemplo.setText("$datoRecibido")


        //poblar lista
        //Opciones que tendrá la lista
        var lista = listOf("Artículos de aseo","Dulces","Fiambrería","Fideos","Arroz","Sal","Azúcar","Dulces","Frutas y Verduras", "Útiles Escolares")
        var adaptador = ArrayAdapter(this@Editar_producto,android.R.layout.simple_spinner_dropdown_item,lista)
        sp_datos_tipos_editar.adapter = adaptador

        //listening

        btn_guardar_editar.setOnClickListener {
            //capturar valores
            val tipo_productos_editar = sp_datos_tipos_editar.selectedItem.toString() //obtener valor del spinner
            val nombre_producto_editar = til_nombre_producto_editar.editText?.text.toString()
            val cantidad_producto_editar = til_cantidad_editar.editText?.text.toString()
            val precio_producto_editar = til_precio_editar.editText?.text.toString()
            val vencimiento_editar = til_vencimiento_editar.editText?.text.toString()
            val ubicacion_producto_editar = til_ubicacion_editar.editText?.text.toString()
            Log.i("DEBUG VAR","tipo_productos: "+tipo_productos_editar+"nombre_producto: "+nombre_producto_editar+"cantidad_producto: "+cantidad_producto_editar+"precio_producto: "+precio_producto_editar+"fecha_vencimiento: "+vencimiento_editar+"ubicacion_producto: "+ubicacion_producto_editar)

            //Validaciones
            val validate = Validate()
            if(validate.validarNombre(nombre_producto_editar)) til_nombre_producto_editar.error = getString(R.string.error_formato_string) else til_nombre_producto_editar.error = ""
            if(validate.validarNulo(nombre_producto_editar)) til_nombre_producto_editar.error = getString(R.string.error_campo_vacio) else til_nombre_producto_editar.error = ""
            if(validate.validarNulo(cantidad_producto_editar)) til_cantidad_editar.error = getString(R.string.error_campo_vacio) else til_cantidad_editar.error = ""
            if(validate.validarNulo(precio_producto_editar)) til_precio_editar.error = getString(R.string.error_campo_vacio) else til_precio_editar.error = ""
            if(validate.validarNulo(vencimiento_editar)) til_vencimiento_editar.error = getString(R.string.error_campo_vacio) else til_vencimiento_editar.error = ""
            if(validate.validarNulo(ubicacion_producto_editar)) til_ubicacion_editar.error = getString(R.string.error_campo_vacio) else til_ubicacion_editar.error = ""

            if (!validate.validarNulo(nombre_producto_editar) && !validate.validarNulo(cantidad_producto_editar) && !validate.validarNulo(precio_producto_editar) && !validate.validarNulo(vencimiento_editar) && !validate.validarNulo(ubicacion_producto_editar) && validate.validarNombre(nombre_producto_editar)){
                val intent = Intent (this@Editar_producto,Inventario_todos_los_productos::class.java)
                startActivity(intent)}
        }

        btn_eliminar_editar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro de eliminarlo?")
            builder.setPositiveButton("¿Eliminar?") {
                dialog,
                    //Acción a realizar
                    wich ->
                Toast.makeText(this,"Elemento eliminado",Toast.LENGTH_LONG).show()//Modificar esto con la base de datos implementada
            }
            builder.setNegativeButton("Cancelar", null)
            builder.setNeutralButton("No estoy seguro",null)
            builder.show()
        }

        til_vencimiento_editar.editText?.setOnClickListener { v -> showDatePickerDialog() }
    }



    //Función para poner la fecha seleccionada en el campo de texto
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day,month,year) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    //Función para poner la fecha seleccionada en el campo de texto
    private fun onDateSelected(day:Int,month:Int,year:Int){
        val til_vencimiento_editar = findViewById<TextInputLayout>(R.id.til_vencimiento_editar)
        var daySelected = "$day"
        var monthSelected = (month+1).toString()
        if(day<10){daySelected = "0$day"}
        if((month+1)<10){monthSelected = "0"+(month+1).toString()}
        til_vencimiento_editar.editText?.setText("$daySelected/$monthSelected/$year")
    }

    }
