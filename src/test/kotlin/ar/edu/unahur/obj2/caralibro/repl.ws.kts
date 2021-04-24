import ar.edu.unahur.obj2.caralibro.FactorDeCompresion
import ar.edu.unahur.obj2.caralibro.Foto

// Pueden usar este archivo para hacer pruebas rápidas,
// de la misma forma en que usaban el REPL de Wollok.

// OJO: lo que esté aquí no será tenido en cuenta
// en la corrección ni reemplaza a los tests.

listOf(1, 8, 10).average()

val fotoEnCuzco = Foto(768, 1024)
fotoEnCuzco.factorDeCompresion
FactorDeCompresion.setearNuevoFactor(0.9)
FactorDeCompresion.factor
FactorDeCompresion.setearNuevoFactor(0.5)
FactorDeCompresion.factor
fotoEnCuzco.factorDeCompresion

