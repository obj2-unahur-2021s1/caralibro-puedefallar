package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

object FactorDeCompresion {
    var factor: Double = 0.7
    fun setearNuevoFactor(nuevoFactor : Double) {
        this.factor = nuevoFactor
    }
}
/////////////////////////PERMISOS///////////////////////////////////
abstract class Permiso {
    abstract fun permiteVerPubli(autor:Usuario, visitante:Usuario) :Boolean
}

object Publico :Permiso() {
    override fun permiteVerPubli(autor: Usuario, visitante: Usuario): Boolean {
        return true
    }

}
object SoloAmigos :Permiso() {
    override fun permiteVerPubli(autor: Usuario, visitante: Usuario): Boolean {
        return autor.amigos.contains(visitante) || autor == visitante
    }

}
object PrivadoConPermitidos :Permiso() {
    override fun permiteVerPubli(autor: Usuario, visitante: Usuario): Boolean {
        return autor.listaPermitidos.contains(visitante) || autor == visitante
    }

}
object PublicoConExcluidos :Permiso() {
    override fun permiteVerPubli(autor: Usuario, visitante: Usuario): Boolean {
        return !autor.listaExcluidos.contains(visitante)
    }


}
/////////////////////////PUBLICACION///////////////////////////////////
abstract class Publicacion(var permiso:Permiso) {
    lateinit var autor: Usuario
    val aQuienLeGusta = mutableListOf<Usuario>()
    fun quienDioLike() = aQuienLeGusta
    var cantidadMeGusta: Int = 0
    abstract fun espacioQueOcupa(): Int

    fun puedeSerVistaPor(autor: Usuario, visitante: Usuario) : Boolean {
        return this.permiso.permiteVerPubli(autor, visitante)
    }
    fun modificarPermiso(permisoNuevo: Permiso) {
        this.permiso = permisoNuevo
    }
}

class Foto(val alto: Int, val ancho: Int, permiso: Permiso) : Publicacion(permiso) {
    val factorDeCompresion = FactorDeCompresion.factor
    override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String, permiso: Permiso) : Publicacion(permiso) {
  override fun espacioQueOcupa() = contenido.length
}

class Video(var calidad: String, val duracion: Int, permiso: Permiso) : Publicacion(permiso) {
  fun setearCalidadDeVideo(calidadNueva: String) {
    this.calidad = calidadNueva
  }
  override fun espacioQueOcupa(): Int {
    var duracionSegunCalidad : Int = 0
    when(calidad) {
        "SD" -> {
          duracionSegunCalidad = duracion
        }
        "HD720p" -> {
          duracionSegunCalidad = duracion * 3
        }
        "HD1080p" -> {
          duracionSegunCalidad = (duracion * 3) * 2
        }
    }
    return duracionSegunCalidad
  }
}
