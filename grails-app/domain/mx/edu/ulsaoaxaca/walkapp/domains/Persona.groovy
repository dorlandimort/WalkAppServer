package mx.edu.ulsaoaxaca.walkapp.domains

class Persona {

    static hasOne = [cuenta: Cuenta]
    static hasMany = [bitacoras: Bitacora]

    String nombre
    String sexo
    Integer edad
    Double peso // kilogramos
    Integer estatura // centimetros
    Double imc
    Dieta dieta

    static constraints = {
        nombre(nullable: false, blank: false, maxSize: 120)
        sexo(nullable: false, blank: false, minSize: 1, maxSize: 1)
        edad(nullable: false, min: 14, max: 120)
        peso(nullable: false, min: 30d, max: 250d)
        estatura(nullable: false, min: 100, max: 300)
        imc(nullable: true, min: 0d, max: 50d)
        cuenta(nullable: true)
        dieta(nullable: true)

    }

    static  mapping = {
        version false
    }

    Double getImc() {
        return peso / (Math.pow((estatura / 100), 2))
    }
}
