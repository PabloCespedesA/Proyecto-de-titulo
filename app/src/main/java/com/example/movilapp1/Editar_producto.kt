package com.example.movilapp1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDataBase.Db
import roomDataBase.entity.TipoProducto
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout

class Editar_producto : AppCompatActivity() {

    var productoId: Long = 0
    var imageUri: Uri? = null
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri

            // Tomamos permisos persistentes en la URI
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)


            val ivEditarImagenProducto = findViewById<ImageView>(R.id.iv_editar_imagen_producto)
            ivEditarImagenProducto.setImageURI(imageUri)
        }
    }
    private val TAG = "EditarProducto"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        val view_agregar_listado = findViewById<ConstraintLayout>(R.id.view_editar_producto)
        view_agregar_listado.setOnTouchListener { v, event ->
            if (currentFocus != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }
            v.performClick()
            true
        }

        // Verificar permisos de almacenamiento
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permiso otorgado, puedes continuar con la lógica relacionada con el almacenamiento
            Log.v(TAG, "Permission is granted")
        } else {
            // Permiso no otorgado, debes solicitarlo al usuario
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        val sp_datos_tipos_editar = findViewById<Spinner>(R.id.sp_datos_tipos_editar)
        val til_nombre_producto_editar =
            findViewById<TextInputLayout>(R.id.til_nombre_producto_editar)
        val til_cantidad_editar = findViewById<TextInputLayout>(R.id.til_cantidad_editar)
        val til_precio_editar = findViewById<TextInputLayout>(R.id.til_precio_editar)
        val til_vencimiento_editar = findViewById<TextInputLayout>(R.id.til_vencimiento_editar)
        val til_ubicacion_editar = findViewById<TextInputLayout>(R.id.til_ubicacion_editar)
        //val txt_usuarioActual = findViewById<TextView>(R.id.txt_usuarioActual)
        val btn_guardar_editar = findViewById<Button>(R.id.btn_guardar_editar)
        val btn_eliminar_editar = findViewById<Button>(R.id.btn_eliminar_editar)
        val btn_atras_editar_producto = findViewById<Button>(R.id.btn_atras_editar_producto)
        val tv_id = findViewById<TextView>(R.id.tv_id)

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()
        val cliente: String = intent.getStringExtra("cliente").toString()
        val nombre: String = intent.getStringExtra("nombre").toString()


        val newFragment = DatePickerFragment  { date: String ->
            // En la función lambda, simplemente estableces la fecha seleccionada en el campo de texto
            til_vencimiento_editar.editText?.setText(date)
        }

        // Cuando se hace clic en el campo de fecha de vencimiento, se muestra el DatePickerDialog
        til_vencimiento_editar.editText?.setOnClickListener {
            newFragment.show(supportFragmentManager, "datePicker")
        }


        btn_atras_editar_producto.setOnClickListener {
            val intent = Intent (this@Editar_producto,Inventario_todos_los_productos::class.java)
            intent.putExtra("cliente",cliente)
            startActivity(intent)
            finish()
        }

        val btnEditarImagenProducto = findViewById<Button>(R.id.btn_editar_imagen_producto)
        btnEditarImagenProducto.setOnClickListener {
            getContent.launch("image/*")
        }

        val ivEditarImagenProducto = findViewById<ImageView>(R.id.iv_editar_imagen_producto)

        var id:Long = 0
        //txt_usuarioActual.setText("Usuario ${cliente}")
        tv_id.setText("Producto: ${nombre}")

        //poblar lista
        //Opciones que tendrá la lista

        val lista = TipoProducto.values().map { it.tipo } // Crea una lista de Strings a partir del Enum
        val adaptador = ArrayAdapter(this@Editar_producto, android.R.layout.simple_spinner_dropdown_item, lista)
        sp_datos_tipos_editar.adapter = adaptador


        lifecycleScope.launch {
            val respuesta = room.daoProducto().obtenerProductoPorNombre(nombre, cliente)
            if (respuesta.size == 1) {
                for (elemento in respuesta) {

                    // Imprimir en los logs el valor del tipo de producto
                    Log.d("MyApp", "Producto tipos: ${elemento.tipos}")

                    // Encontrar el índice del tipo de producto en la lista
                    val tipoProductoIndex = lista.indexOf(elemento.tipos?.tipo)

                    // Imprimir en los logs el índice que se encontró
                    Log.d("MyApp", "Tipos index: $tipoProductoIndex")

                    // Establecer el tipo de producto seleccionado en el Spinner
                    runOnUiThread {
                        sp_datos_tipos_editar.setSelection(tipoProductoIndex)
                    }
                    til_nombre_producto_editar.editText?.setText(elemento.nombre.toString())
                    til_cantidad_editar.editText?.setText(elemento.cantidad.toString())
                    til_precio_editar.editText?.setText(elemento.precio.toString())
                    til_vencimiento_editar.editText?.setText(elemento.fecha.toString())
                    til_ubicacion_editar.editText?.setText(elemento.ubicacion.toString())
                    productoId = elemento.id

                    // Convertimos el String a Uri y lo asignamos a imageUri
                    imageUri = Uri.parse(elemento.imagen_uri)

                    // Tomamos persistencia sobre los permisos de la URI
                    try {
                        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        contentResolver.takePersistableUriPermission(imageUri!!, takeFlags)
                        ivEditarImagenProducto.setImageURI(imageUri)
                    } catch (e: SecurityException) {
                        Log.e("MyApp", "Failed to take persistable uri permission", e)
                    }
                }
            }
        }

        // Este método nos permitirá tomar permisos persistentes para la URI de la imagen.
        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    try {
                        contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        imageUri = uri
                        ivEditarImagenProducto.setImageURI(imageUri)

                        val hasPermission = contentResolver.persistedUriPermissions.any { uriPermission ->
                            uriPermission.uri == imageUri
                        }

                        if (hasPermission) {
                            Log.d("MyApp", "Permiso persistente otorgado para la URI: $imageUri")
                        } else {
                            Log.d("MyApp", "No se pudo obtener el permiso persistente para la URI: $imageUri")
                        }

                        Log.d("MyApp", "URI de la imagen: $imageUri")
                    } catch (e: Exception) {
                        Log.e("MyApp", "Failed to take persistable uri permission", e)
                    }
                }
            }
        }

        btnEditarImagenProducto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            getContent.launch(intent)
        }

        btn_guardar_editar.setOnClickListener {
            // Recoge los valores del formulario
            val tipos = TipoProducto.fromTipo(sp_datos_tipos_editar.selectedItem.toString())!!
            val nombre = til_nombre_producto_editar.editText?.text.toString()
            val cantidad = til_cantidad_editar.editText?.text.toString()
            val precio = til_precio_editar.editText?.text.toString()
            val fecha = if (til_vencimiento_editar.editText?.text.toString().isNullOrEmpty()) {
                "31/12/9999"
            } else {
                til_vencimiento_editar.editText?.text.toString()
            }
            val ubicacion = til_ubicacion_editar.editText?.text.toString()

            val imagen = imageUri.toString() // Convertimos el Uri a String para guardarlo en la base de datos



            //Validaciones
            val validate = Validate()

            if (!validate.validarNombre(nombre)) {
                til_nombre_producto_editar.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_nombre_producto_editar.error = ""
            }

            if (validate.validarNulo(nombre)) {
                til_nombre_producto_editar.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_nombre_producto_editar.error = ""
            }

            if (!validate.validarCantidad(cantidad)) {
                til_cantidad_editar.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_cantidad_editar.error = ""
            }

            if (!validate.validarCantidad(precio)) {
                til_precio_editar.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_precio_editar.error = ""
            }


            if (fecha.isNotEmpty() && !isDateFormatValid(fecha)) {
                til_vencimiento_editar.error = "El formato de la fecha no es válido."
                return@setOnClickListener
            }

            if (validate.validarNulo(ubicacion)) {
                til_ubicacion_editar.error = getString(R.string.error_campo_vacio)
                return@setOnClickListener
            } else {
                til_ubicacion_editar.error = ""
            }


            lifecycleScope.launch {
                // Llama a la función de actualización
                val result = room.daoProducto().actualizarProductos(tipos, nombre, cantidad, precio, fecha, ubicacion, imagen, productoId)

                // Comprueba si la actualización fue exitosa
                if (result > 0) {
                    Toast.makeText(this@Editar_producto, "Producto actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Editar_producto, "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent (this@Editar_producto,Inventario_todos_los_productos::class.java)
                intent.putExtra("cliente",cliente)
                startActivity(intent)
            }

            til_vencimiento_editar.editText?.setOnClickListener {
                // Comprueba si el campo de fecha está vacío o si el formato de la fecha es correcto antes de mostrar el DatePickerDialog.
                if (til_vencimiento_editar.editText?.text.toString().isEmpty() ||
                    isDateFormatValid(til_vencimiento_editar.editText?.text.toString()!!)
                ) {
                    showDatePickerDialog()
                } else {
                    til_vencimiento_editar.error = "El formato de la fecha no es válido."
                }
            }
        }

        btn_eliminar_editar.setOnClickListener {

            // Crear el diálogo de confirmación
            AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Estás seguro de que quieres eliminar este producto?")
                .setPositiveButton("Sí") { dialog, _ ->

                    // Si el usuario hace clic en "Sí", se procede a la eliminación
                    lifecycleScope.launch {
                        val result = room.daoProducto().borrarProducto(productoId)

                        // Comprobar si la eliminación fue exitosa
                        if (result > 0) {
                            Toast.makeText(this@Editar_producto, "Producto eliminado con éxito", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Editar_producto, "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
                        }

                        // Redirigir al inventario después de eliminar el producto
                        val intent = Intent (this@Editar_producto, Inventario_todos_los_productos::class.java)
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
    private fun isDateFormatValid(date: String): Boolean {
        val datePattern = """^\d{1,2}/\d{1,2}/\d{4}$""".toRegex()
        return datePattern.matches(date)
    }
    //Función para poner la fecha seleccionada en el campo de texto
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{ date -> onDateSelected(date) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    //Función para poner la fecha seleccionada en el campo de texto
    private fun onDateSelected(date:String){
        val til_fecha_vencimiento = findViewById<TextInputLayout>(R.id.til_fecha_vencimiento)
        til_fecha_vencimiento.editText?.setText(date)
    }

}