package roomDataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import roomDataBase.entity.Usuario

@Dao
interface DaoUsuario {
    //metodo para obtener info
    @Query("SELECT * FROM Usuario")
    //suspend son las corrutinas, estas son una de las caraacteristicas mas impresionantes de kotlin: is simply a function that can be paused and resume
    suspend fun obtenerUsuario(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE usuario=:usuario")
    suspend fun obtenerUsuario(usuario: String):Usuario

    @Query("SELECT * FROM Usuario WHERE usuario=:usuario AND contrasena=:contrasena")
    suspend fun login(usuario: String, contrasena: String): List <Usuario>

    @Insert
    suspend fun agregarUsuario(usuario: Usuario):Long

    @Query("UPDATE Usuario SET negocio=:negocio,email=:email, contrasena=:contrasena WHERE usuario=:usuario")
    suspend fun actualizarUsuario(usuario: String,negocio:String,email:String,contrasena:String): Int

    @Query("DELETE FROM Usuario WHERE usuario=:usuario")
    suspend fun borrarUsuario(usuario: String)

}