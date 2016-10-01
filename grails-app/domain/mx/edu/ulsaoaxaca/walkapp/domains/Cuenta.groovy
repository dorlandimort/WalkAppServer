package mx.edu.ulsaoaxaca.walkapp.domains

class Cuenta {

    static belongsTo = [persona: Persona]

    String usuario
    String password
    boolean status

    static constraints = {
        usuario(nullable: false, blank: false, maxSize: 40)
        password(nullable: false, blank: false, maxSize: 50)
    }

    static mapping = {
        status defaultValue: true
    }
}
