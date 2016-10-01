package mx.edu.ulsaoaxaca.walkapp.domains

class Dieta {

    static belongsTo = [persona: Persona]
    static hasMany = [insumos: DietaInsumo]

    String nombre
    String descripcion
    Integer cal

    static constraints = {
        nombre(nullable: false, blank: false, maxSize: 100)
        descripcion(nullable: true, blank: true, maxSize: 1000)
        cal(nullable: true, min: 1, max: 5000)
    }
}
