package roomDataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import roomDataBase.entity.Producto
import roomDataBase.entity.TipoProducto

@Dao

interface DaoProducto {
    //metodo para obtener info
    @Query("SELECT * FROM Producto")
    //suspend son las corrutinas, estas son una de las caracteristicas mas impresionantes de kotlin: is simply a function that can be paused and resume
    suspend fun obtenerProducto(): List<Producto>

    @Query("SELECT * FROM Producto WHERE user=:user")
    suspend fun obtenerProductoUsuario (user: String): List<Producto>

    @Query("SELECT * FROM Producto WHERE nombre=:nombre AND user=:user")
    suspend fun obtenerProductoPorNombre(nombre: String,user: String): List<Producto>

    @Insert
    suspend fun agregarProducto (Producto: Producto):Long

    @Query("UPDATE producto SET tipos = :tipos, nombre = :nombre, cantidad = :cantidad, precio = :precio, fecha = :fecha, ubicacion = :ubicacion, imagen_uri = :imageUrl WHERE id = :id")
    suspend fun actualizarProductos(tipos: TipoProducto, nombre: String, cantidad: String, precio: String, fecha: String, ubicacion: String, imageUrl: String, id: Long): Int

    @Query("DELETE FROM Producto WHERE id=:id")
    suspend fun borrarProducto (id:Long): Int

    @Query("SELECT * FROM Producto WHERE user=:user AND nombre LIKE '%' || :search || '%'")
    suspend fun obtenerProductosPorBusqueda(user: String, search: String): List<Producto>

}