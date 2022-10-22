package roomDataBase

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import roomDataBase.dao.DaoProducto
import roomDataBase.dao.DaoUsuario
import roomDataBase.entity.Producto
import roomDataBase.entity.Usuario

@Database(
    entities = [Usuario::class,Producto::class],//agregar todas las entidades
version = 2
)
abstract  class  Db:RoomDatabase(){
    abstract fun  daoUsuario():DaoUsuario
    abstract fun  daoProducto():DaoProducto
}