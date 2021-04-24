package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val fotoEnIguazu = Foto(700, 1000)
    val videoGraduacion = Video("SD", 900)
    FactorDeCompresion.setearNuevoFactor(0.3)

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
      it("puede calcular el espacio que ocupan sus publicaciones") {
        juana.agregarPublicacion(fotoEnCuzco) //393216 con compresion en 0.5
        juana.agregarPublicacion(saludoCumpleanios)
        juana.agregarPublicacion(videoGraduacion)
        juana.espacioDePublicaciones().shouldBe(236875)
      }

      it("puede calcular dar me gusta a una publicacion") {
        lisa.agregarPublicacion(fotoEnCuzco) //393216 con compresion en 0.5
        juana.darMeGusta(fotoEnCuzco)
        fotoEnCuzco.cantidadMeGusta.shouldBe(1)
        fotoEnCuzco.aQuienLeGusta.shouldBe(juana)
        ////falta implementar var en clase hija
      }

    }






  }
})
