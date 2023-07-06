package roomDataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity

class ListaCompra {
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
    var nombre: String? = null
    var detalle: String? = null
    var user: String? = null

    constructor(
        nombre: String?,
        detalle: String?,
        user: String?
    ) {
        this.nombre = nombre
        this.detalle = detalle
        this.user = user
    }

    override fun toString(): String {
        return "ListaCompra(id=$id, nombre=$nombre, detalle=$detalle, user=$user)"
    }

}