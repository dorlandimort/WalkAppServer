package mx.edu.ulsaoaxaca.walkapp.domains

class DietaInsumo {

    Dieta dieta
    Insumo insumo
    Double cantidad

    static constraints = {
        cantidad(nullable: true, min: 0d)
        dieta(nullable: true)
        insumo(nullable: true)
    }

    static  mapping = {
        version false
    }
}
