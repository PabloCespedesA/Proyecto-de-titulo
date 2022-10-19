package roomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import roomDataBase.dao.DaoUsuario
import roomDataBase.entity.Usuario

@Database(
    entities = [Usuario::class],//agregar todas las entidades
version = 1
)
abstract  class  Db:RoomDatabase(){
    abstract fun  daoUsuario():DaoUsuario
}