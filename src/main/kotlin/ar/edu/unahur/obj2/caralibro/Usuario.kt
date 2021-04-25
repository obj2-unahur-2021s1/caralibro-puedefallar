package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()
  val listaPermitidos = mutableListOf<Usuario>()
  val listaExcluidos = mutableListOf<Usuario>()

  fun puedeVerPublicacion(publicacion: Publicacion, autor:Usuario): Boolean {
    return this.publicaciones.contains(publicacion) || publicacion.puedeSerVistaPor(autor, this)
  }
  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
    publicacion.autor = this
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

  fun puedeDarMeGusta(publicacion: Publicacion) :Boolean {
    return !(publicacion.aQuienLeGusta.contains(this)) && publicacion.puedeSerVistaPor(publicacion.autor, this)
  }

  fun darMeGusta(publicacion: Publicacion) {
    check(!(publicacion.aQuienLeGusta.contains(this)) &&  publicacion.puedeSerVistaPor(publicacion.autor, this)) {
      "Error" }
      publicacion.aQuienLeGusta.add(this)
      publicacion.cantidadMeGusta += 1
  }
  fun cantAmigos() : Int = this.amigos.size

  fun esMasAmistosoQue(usuario: Usuario) :Boolean {
    return this.cantAmigos() > usuario.cantAmigos()
  }
  fun esMejorAmigo(amigo: Usuario) :Boolean {
    return this.publicaciones.all { it.puedeSerVistaPor(it.autor, amigo) }
  }
  fun mejoresAmigos() : List<Usuario> {
    return amigos.filter { this.esMejorAmigo(it) }
  }


  }

