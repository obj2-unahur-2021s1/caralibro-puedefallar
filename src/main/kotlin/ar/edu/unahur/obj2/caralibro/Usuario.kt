package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()
  val listaPermitidos = mutableListOf<Usuario>()
  val listaExcluidos = mutableListOf<Usuario>()

  fun esAutorDePublicacion(publi: Publicacion) = publicaciones.contains(publi)

  /*fun puedeVerPublicacion(publicacion: Publicacion): Boolean {
    return publicacion.puedeSerVistaPor()
  }*/
  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun agregarAmigo(amigo: Usuario) {
    amigos.add(amigo)
  }
  fun agregarPermitido(usuario: Usuario) {
    listaPermitidos.add(usuario)
  }
  fun excluirUsuario(usuario: Usuario) {
    listaExcluidos.add(usuario)
  }
  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun darMeGusta(publicacion: Publicacion) {
    check(!publicacion.aQuienLeGusta.contains(this)) {
      "Ya le diste me gusta!" }
      publicacion.aQuienLeGusta.add(this)
      publicacion.cantidadMeGusta += 1
  }



  }

