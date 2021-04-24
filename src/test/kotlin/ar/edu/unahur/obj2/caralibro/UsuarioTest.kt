package ar.edu.unahur.obj2.caralibro

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    /////PUBLICACIONES
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz", Publico)
    val fotoEnCuzco = Foto(768, 1024, Publico)
    val fotoEnIguazu = Foto(700, 1000, Publico)
    val videoGraduacion = Video("SD", 900, Publico)
    val videoNavidad = Video("HD1080", 2000, PublicoConExcluidos)
    FactorDeCompresion.setearNuevoFactor(0.3)
    /////USUARIOS
    val rodri = Usuario()
    val noe = Usuario()
    val hernan = Usuario()
    val tiaMetida = Usuario()

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



    }
    describe("Permisos") {
      noe.agregarAmigo(rodri)
      noe.agregarAmigo(hernan)
      noe.excluirUsuario(tiaMetida)
      noe.agregarPublicacion(videoNavidad)
      it("Quien puede ver publicaciones de noe") {
        videoNavidad.puedeSerVistaPor(noe, tiaMetida).shouldBe(false)
        videoNavidad.puedeSerVistaPor(noe, rodri).shouldBe(true)
        videoNavidad.puedeSerVistaPor(noe, hernan).shouldBe(true)

      }
    }





  }
})
