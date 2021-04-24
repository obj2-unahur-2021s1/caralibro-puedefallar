package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun darMeGusta(publicacion: Publicacion) {
    check(!publicacion.aQuienLeGusta.contains(this)){"Ya le diste me gusta!"}
    publicacion.aQuienLeGusta.add(this)
    publicacion.cantidadMeGusta += 1
  }



  }

