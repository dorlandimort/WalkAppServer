package mx.edu.ulsaoaxaca.walkapp.domains

class Bitacora {

    static belongsTo = [persona: Persona]

    String fecha
    Double latInicial
    Double lnInicial
    Double latFinal
    Double lnFinal
    Integer pasos
    Double cal // calorias quemadas

    static constraints = {
        fecha(nullable: false)
        latInicial(nullable: false)
        latFinal(nullable: false)
        lnInicial(nullable: false)
        lnFinal(nullable: false)
        pasos(nullable: true, min: 0)
        cal(nullable: true, min: 0d)
    }

    static  mapping = {
        version false
    }
}
