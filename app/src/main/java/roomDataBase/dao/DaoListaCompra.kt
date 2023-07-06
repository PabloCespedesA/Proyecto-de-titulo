package roomDataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import roomDataBase.entity.ListaCompra


@Dao

interface DaoListaCompra {
    //metodo para obtener info
    @Query("SELECT * FROM ListaCompra")
    //suspend son las corrutinas, estas son una de las caraacteristicas mas impresionantes de kotlin: is simply a function that can be paused and resume
    suspend fun obtenerListaCompra(): List<ListaCompra>

    @Query("SELECT * FROM ListaCompra WHERE user=:user")
    suspend fun obtenerListaCompra (user: String): List<ListaCompra>

    @Query("SELECT * FROM ListaCompra WHERE nombre=:nombre AND user=:user")
    suspend fun obtenerListaCompraPorNombre(nombre: String,user: String): List<ListaCompra>

    @Insert
    suspend fun agregarListaCompra (ListaCompra: ListaCompra):Long

    @Query("UPDATE ListaCompra SET nombre=:nombre,detalle=:detalle WHERE id=:id")
    suspend fun actualizarListaCompra (nombre:String,detalle:String,id:Long): Int

    @Query("DELETE FROM ListaCompra WHERE id=:id")
    suspend fun borrarListaCompra (id:Long): Int


}