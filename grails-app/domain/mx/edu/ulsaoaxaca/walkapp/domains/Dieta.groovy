package mx.edu.ulsaoaxaca.walkapp.domains

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class Dieta {


    String nombre
    String descripcion
    Integer cal

    static constraints = {
        nombre(nullable: false, blank: false, maxSize: 100)
        descripcion(nullable: true, blank: true, maxSize: 1000)
        cal(nullable: true, min: 1, max: 5000)
    }

    static  mapping = {
        version false
    }

    JSONArray getInsumos() {

        JSONArray insumosJson = new JSONArray()
        def insumos = DietaInsumo.where {
            dieta == this
        }.findAll()
        insumos.each { it ->
            JSONObject json = new JSONObject()
            json.put("insumo", it.insumo.nombre)
            json.put("cantidad", it.cantidad)
            insumosJson.put(json)
        }
    }
}
