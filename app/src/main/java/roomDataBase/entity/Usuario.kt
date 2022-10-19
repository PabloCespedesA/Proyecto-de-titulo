package roomDataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

class Usuario {
    @PrimaryKey
    var usuario: String
    var negocio: String? = null
    var email: String? = null
    var contrasena: String? = null

    constructor(usuario:String,negocio:String ,email:String, contrasena:String){
        this.usuario = usuario
        this.negocio = negocio
        this.email = email
        this.contrasena = contrasena
    }
}