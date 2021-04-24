package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

object FactorDeCompresion {
    var factor: Double = 0.7
    fun setearNuevoFactor(nuevoFactor : Double) {
        this.factor = nuevoFactor
    }
}
abstract class Publicacion {
    abstract val aQuienLeGusta: MutableList<Usuario>
    abstract var cantidadMeGusta: Int
    abstract fun espacioQueOcupa(): Int

}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
    val factorDeCompresion= FactorDeCompresion.factor
    override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(var calidad: String, val duracion: Int) : Publicacion() {
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
