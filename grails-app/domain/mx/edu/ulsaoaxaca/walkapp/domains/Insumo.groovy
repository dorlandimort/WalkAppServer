package mx.edu.ulsaoaxaca.walkapp.domains

class Insumo {

    static hasMany = [details: DietaInsumo]

    String nombre
    String descripcion
    Integer cal

    static constraints = {
        nombre(nullable: false, blank: false, maxSize: 100)
        descripcion(nullable: true, blank: true, maxSize: 1000)
        cal(nullable: true, min: 0)
    }
}
