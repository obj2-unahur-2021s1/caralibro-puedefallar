package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    /////PUBLICACIONES
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz", Publico)
    val fotoEnCuzco = Foto(768, 1024, Publico)
    val fotoEnIguazu = Foto(700, 1000, Publico)
    val videoGraduacion = Video("SD", 900, Publico)
    val videoNavidad = Video("HD1080p", 2000, PublicoConExcluidos)
    val textoAmigos = Texto("Me quedé dormida, no le digan a mi jefe :)", SoloAmigos)
    val videoPartido = Video("SD", 9500, PublicoConExcluidos)
    val textoFamilia = Texto("Hola a todos", SoloAmigos)
    val videoRacing = Video("HD1080p", 5400, Publico)
    val textoComunion = Texto("Estan invitados a mi comunión el sábado a las 8AM", SoloAmigos)
    val fotoEnElCilindro = Foto(650, 1200, SoloAmigos)
    FactorDeCompresion.setearNuevoFactor(0.3)
    /////USUARIOS
    val rodri = Usuario()
    val noe = Usuario()
    val hernan = Usuario()
    val tiaMetida = Usuario()
    val jefeDeNoe = Usuario()
    val silvia = Usuario()
    val gustavo = Usuario()
    val miriam = Usuario()

    describe("Una publicación") {

      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
        it(" factor de compresion cambia para todas las fotos") {
          fotoEnCuzco.factorDeCompresion.shouldBe(0.3)
          fotoEnIguazu.factorDeCompresion.shouldBe(0.3)
          fotoEnCuzco.espacioQueOcupa().shouldBe(235930)
        }
      }
      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }

      describe("de tipo video SD") {
        it("ocupa tantos bytes como su duracion") {
          videoGraduacion.espacioQueOcupa().shouldBe(900)
        }
      }

      describe("de tipo video HD720p") {
        it("ocupa tantos bytes como el triple de su duracion") {
          videoGraduacion.setearCalidadDeVideo("HD720p")
          videoGraduacion.espacioQueOcupa().shouldBe(2700)
        }
      }

      describe("de tipo video HD 1080p") {
        it("ocupa el doble de bytes que en calidad HD720p") {
          videoGraduacion.setearCalidadDeVideo("HD1080p")
          videoGraduacion.espacioQueOcupa().shouldBe(5400)
        }
      }
    }

    describe("Un usuario") {
      val juana = Usuario()
      val lisa = Usuario()
      val lucho = Usuario()
      rodri.agregarAmigo(silvia)
      rodri.agregarAmigo(gustavo)
      rodri.agregarAmigo(noe)
      rodri.agregarAmigo(hernan)
      rodri.excluirUsuario(noe)
      rodri.excluirUsuario(hernan)
      noe.agregarAmigo(miriam)
      noe.agregarAmigo(rodri)
      rodri.agregarPublicacion(textoFamilia)
      rodri.agregarPublicacion(videoPartido)
      silvia.agregarPublicacion(fotoEnIguazu)
      it("puede calcular el espacio que ocupan sus publicaciones") {
        juana.agregarPublicacion(fotoEnCuzco) //393216 con compresion en 0.5
        juana.agregarPublicacion(saludoCumpleanios)
        juana.agregarPublicacion(videoGraduacion)
        juana.espacioDePublicaciones().shouldBe(236875)
      }

      it("puede dar me gusta a una publicacion una sola vez") {
        lisa.agregarPublicacion(fotoEnCuzco) //393216 con compresion en 0.5
        juana.darMeGusta(fotoEnCuzco)
        lucho.darMeGusta(fotoEnCuzco)
        fotoEnCuzco.cantidadMeGusta.shouldBe(2)
        fotoEnCuzco.aQuienLeGusta.shouldContain(juana)
        fotoEnCuzco.aQuienLeGusta.shouldContain(lucho)
        shouldThrowAny{ juana.darMeGusta(fotoEnCuzco) }
      }
      it("es mas amistoso que otro") {
        rodri.esMasAmistosoQue(noe).shouldBe(true)
        noe.esMasAmistosoQue(rodri).shouldBe(false)
      }
      it("mejores amigos de rodri") {
        rodri.esMejorAmigo(silvia).shouldBe(true)
        rodri.esMejorAmigo(hernan).shouldBe(false)
        rodri.mejoresAmigos().shouldContainAll(silvia, gustavo)
        rodri.mejoresAmigos().shouldNotContainAll(hernan, noe)
      }
      it("Amigo más popular") {
        noe.agregarAmigo(rodri)
        noe.agregarAmigo(hernan)
        noe.agregarAmigo(tiaMetida)
        noe.agregarPublicacion(videoNavidad)
        noe.agregarPublicacion(textoAmigos)
        rodri.darMeGusta(videoNavidad)
        hernan.darMeGusta(videoNavidad)
        rodri.darMeGusta(textoAmigos)
        hernan.darMeGusta(textoAmigos)
        silvia.agregarAmigo(gustavo)
        gustavo.darMeGusta(fotoEnIguazu)
        rodri.amigoMasPopular().shouldBe(noe)
        rodri.amigoMasPopular().shouldNotBe(hernan)
      }
      it("usuario es stalker de otro") {
        rodri.agregarPublicacion(textoComunion)
        rodri.agregarPublicacion(videoRacing)
        rodri.agregarPublicacion(fotoEnElCilindro)
        silvia.darMeGusta(textoComunion)
        silvia.darMeGusta(videoRacing)
        silvia.darMeGusta(textoFamilia)
        silvia.darMeGusta(fotoEnElCilindro)
        silvia.darMeGusta(videoPartido)
        gustavo.darMeGusta(videoPartido)
        rodri.cantidadDeMeGustaDe(silvia).shouldBe(5)
        rodri.cantidadDeMeGustaDe(gustavo).shouldBe(1)
        silvia.esStalkerDe(rodri).shouldBe(true)
        gustavo.esStalkerDe(rodri).shouldBe(false)

      }


    }
    describe("Permisos") {
      noe.agregarAmigo(rodri)
      noe.agregarAmigo(hernan)
      noe.agregarAmigo(tiaMetida)
      noe.excluirUsuario(tiaMetida)
      noe.agregarPublicacion(videoNavidad)
      noe.agregarPublicacion(textoAmigos)
      rodri.darMeGusta(videoNavidad)
      it("Quien puede ver publicaciones de noe") {
        videoNavidad.puedeSerVistaPor(noe, tiaMetida).shouldBe(false)
        videoNavidad.puedeSerVistaPor(noe, rodri).shouldBe(true)
        videoNavidad.puedeSerVistaPor(noe, hernan).shouldBe(true)
        textoAmigos.puedeSerVistaPor(noe, noe).shouldBe(true)
        videoNavidad.puedeSerVistaPor(noe, noe).shouldBe(true)
        tiaMetida.puedeVerPublicacion(videoNavidad, noe).shouldBe(false)
        hernan.puedeVerPublicacion(textoAmigos, noe).shouldBe(true)
        jefeDeNoe.puedeVerPublicacion(textoAmigos, noe).shouldBe(false)
        tiaMetida.puedeDarMeGusta(videoNavidad).shouldBe(false)
        rodri.puedeDarMeGusta(videoNavidad).shouldBe(false)
        shouldThrowAny{ tiaMetida.darMeGusta(videoNavidad) }
      }
      it("cambiar el permiso de una publicación") {
        textoAmigos.modificarPermiso(PrivadoConPermitidos)
        noe.agregarPermitido(gustavo)
        gustavo.puedeVerPublicacion(textoAmigos, noe).shouldBe(true)
      }
    }

  }
})
