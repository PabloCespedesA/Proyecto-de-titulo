package roomDataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class TipoProducto(val tipo: String) {
    BEBIDAS("Bebidas"),
    CONGELADOS("Congelados"),
    DULCES("Dúlces"),
    FRUTAS_Y_VERDURAS("Frutas y verduras"),
    HELADOS("Helados"),
    LACTEOS("Lácteos"),
    NO_PERECIBLES("No perecibles"),
    OTROS("Otros"),
    SNACK("Snack"),
    UTILES_ESCOLARES("Útiles escolares");

    //solucionar problema de La diferencia de mayusculas y minusculas al actualizar el producto
    companion object {
        fun fromTipo(tipo: String): TipoProducto? {
            return values().find { it.tipo == tipo }
        }
    }
}
@Entity
class Producto {
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
    var tipos: TipoProducto? = null
    var nombre: String? = null
    var cantidad: String? = null
    var precio: String? = null
    var fecha: String? = null
    var ubicacion: String? = null
    var user: String? = null
    var imagen_uri: String? = null

    constructor(
        tipos: TipoProducto?,
        nombre: String?,
        cantidad: String,
        precio: String,
        fecha: String?,
        ubicacion: String?,
        user: String?,
        imagen_uri: String?
    ) {
        this.tipos = tipos
        this.nombre = nombre
        this.cantidad = cantidad
        this.precio = precio
        this.fecha = fecha
        this.ubicacion = ubicacion
        this.user = user
        this.imagen_uri = imagen_uri
    }

    override fun toString(): String {
        return "Producto(id=$id, tipos=$tipos, nombre=$nombre, cantidad=$cantidad, precio=$precio, fecha=$fecha, ubicacion=$ubicacion, user=$user, imagen_uri=$imagen_uri)"
    }

}