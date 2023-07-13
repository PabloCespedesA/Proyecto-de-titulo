package com.example.movilapp1

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import roomDataBase.Db
import roomDataBase.entity.Producto
import roomDataBase.entity.TipoProducto
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import kotlin.math.ceil


class Registrar_producto : AppCompatActivity() {

    // Inicializar la variable para almacenar la URI de la imagen
    var imageUri: Uri? = null

    // Crear un ActivityResultContract para seleccionar una imagen
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Este código se ejecutará después de seleccionar la imagen
            val uri = result.data?.data
            if (uri != null) {
                try {
                    // Tomar permiso persistente
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                    imageUri = uri
                    val ivProducto = findViewById<ImageView>(R.id.iv_product_image)
                    ivProducto.setImageURI(imageUri)

                    // Comprobar si ya se tienen los permisos
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

    // Función para abrir el selector de imágenes
    fun openImagePicker(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        getContent.launch(intent)
    }


    private val TAG = "Registrar_producto"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)

        val view_agregar_listado = findViewById<ConstraintLayout>(R.id.view_registrar_producto)
        view_agregar_listado.setOnTouchListener { v, event ->
            if (currentFocus != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }
            v.performClick()
            true
        }

        //Inicializar la base de datos
        val room =
            Room.databaseBuilder(this, Db::class.java, "database-tiendita").allowMainThreadQueries()
                .build()

        val sp_datos_tipos = findViewById<Spinner>(R.id.sp_datos_tipos)
        val til_nombre_producto = findViewById<TextInputLayout>(R.id.til_nombre_producto)
        val til_cantidad = findViewById<TextInputLayout>(R.id.til_cantidad)
        val til_precio_producto = findViewById<TextInputLayout>(R.id.til_precio_producto)
        val til_fecha_vencimiento = findViewById<TextInputLayout>(R.id.til_fecha_vencimiento)
        val til_ubicacion_producto = findViewById<TextInputLayout>(R.id.til_ubicacion_producto)
        val btn_agregarProducto = findViewById<Button>(R.id.btn_agregarProducto)
        val btn_atras_registrar_producto = findViewById<Button>(R.id.btn_atras_registrar_producto)
        val tv_user_agregar_producto = findViewById<TextView>(R.id.tv_user_agregar_producto)
        val precioSugeridoTextView = findViewById<TextView>(R.id.precioSugeridoTextView) // Cambia esto a tu ID real



        //Recuperamos la variable del intent
        val cliente: String = intent.getStringExtra("cliente").toString()
        tv_user_agregar_producto.setText("Hola Usuario ${cliente}")

        btn_atras_registrar_producto.setOnClickListener {
            val intent = Intent(this@Registrar_producto, Inventario_todos_los_productos::class.java)
            intent.putExtra("cliente", cliente)
            startActivity(intent)
            finish()
        }

        //poblar lista
        //Opciones que tendrá la lista

        val lista =
            TipoProducto.values().map { it.tipo } // Crea una lista de Strings a partir del Enum
        val adaptador = ArrayAdapter(
            this@Registrar_producto,
            android.R.layout.simple_spinner_dropdown_item,
            lista
        )
        sp_datos_tipos.adapter = adaptador

        // Solicitar permisos de almacenamiento
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted")
            } else {
                Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted")
        }

        //seleccionar imagen
        val btnSeleccionarImagen = findViewById<Button>(R.id.btn_seleccionar_imagen)
        btnSeleccionarImagen.setOnClickListener { view ->
            openImagePicker(view)
        }

        // Añade un TextWatcher a tu EditText
        til_precio_producto.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // No necesitamos hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // No necesitamos hacer nada aquí
            }

            override fun afterTextChanged(s: Editable) {
                // Cuando el texto cambia, actualizamos el TextView de precio sugerido
                val precio = s.toString().toDoubleOrNull()
                if (precio != null) {
                    val precioSugerido = precio * 1.3 // Añade un 30% al precio
                    val precioSugeridoRedondeado = ceil(precioSugerido / 10) * 10 // Redondea al próximo múltiplo de 10
                    precioSugeridoTextView.text = precioSugeridoRedondeado.toInt().toString() // Actualiza el TextView con el precio sugerido sin decimales
                } else {
                    precioSugeridoTextView.text = "" // Si el precio no es un número válido, limpia el TextView
                }
            }
        })

        btn_agregarProducto.setOnClickListener {
            // Capturar valores
            val tipoProductoIndex = sp_datos_tipos.selectedItemPosition
            val tipo_productos = TipoProducto.values()[tipoProductoIndex]
            val nombre_producto = til_nombre_producto.editText?.text.toString()
            val cantidad_producto = til_cantidad.editText?.text.toString()
            val precio_producto = til_precio_producto.editText?.text.toString()

            val vencimiento_producto = if (til_fecha_vencimiento.editText?.text.toString().isNullOrEmpty()) {
                "31/12/9999"
            } else {
                til_fecha_vencimiento.editText?.text.toString()
            }

            val ubicacion_producto = til_ubicacion_producto.editText?.text.toString()
            val imagen_producto_uri =
                imageUri.toString()  // Convertimos la Uri de la imagen a String
            var id: Long = 0
            val producto = Producto(
                tipo_productos,
                nombre_producto,
                cantidad_producto,
                precio_producto,
                vencimiento_producto,
                ubicacion_producto,
                cliente,
                imagen_producto_uri
            ) // Añadimos el URI de la imagen al construir el Producto

            Log.i(
                "DEBUG VAR",
                "tipo_productos :" + tipo_productos + "nombre_producto :" + nombre_producto + "cantidad_producto :" + cantidad_producto + "precio_producto :" + precio_producto + "fecha_vencimiento: " + vencimiento_producto + "ubicacion_producto: " + ubicacion_producto + "cliente: " + cliente
            )


            //Validaciones
            val validate = Validate()

            if (!validate.validarNombre(nombre_producto)) {
                til_nombre_producto.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_nombre_producto.error = ""
            }

            if (validate.validarNulo(nombre_producto)) {
                til_nombre_producto.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_nombre_producto.error = ""
            }

            if (!validate.validarCantidad(cantidad_producto)) {
                til_cantidad.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_cantidad.error = ""
            }

            if (!validate.validarCantidad(precio_producto)) {
                til_precio_producto.error = "Campo vacío o carácteres no validos"
                return@setOnClickListener
            } else {
                til_precio_producto.error = ""
            }


            if (vencimiento_producto.isNotEmpty() && !isDateFormatValid(vencimiento_producto)) {
                til_fecha_vencimiento.error = "El formato de la fecha no es válido."
                return@setOnClickListener
            }

            if (validate.validarNulo(ubicacion_producto)) {
                til_ubicacion_producto.error = getString(R.string.error_campo_vacio)
                return@setOnClickListener
            } else {
                til_ubicacion_producto.error = ""
            }

            // Si todas las validaciones son correctas, entonces se puede agregar el producto
            lifecycleScope.launch {
                id = room.daoProducto().agregarProducto(producto)
                if (id > 0) {
                    Toast.makeText(
                        this@Registrar_producto,
                        "Producto registrado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent =
                        Intent(this@Registrar_producto, Inventario_todos_los_productos::class.java)
                    intent.putExtra("cliente", cliente)
                    startActivity(intent)
                }
            }
        }

        til_fecha_vencimiento.editText?.setOnClickListener {
            // Comprueba si el campo de fecha está vacío o si el formato de la fecha es correcto antes de mostrar el DatePickerDialog.
            if (til_fecha_vencimiento.editText?.text.toString().isEmpty() ||
                isDateFormatValid(til_fecha_vencimiento.editText?.text.toString()!!)
            ) {
                showDatePickerDialog()
            } else {
                til_fecha_vencimiento.error = "El formato de la fecha no es válido."
            }
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