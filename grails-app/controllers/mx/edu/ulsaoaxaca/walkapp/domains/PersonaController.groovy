package mx.edu.ulsaoaxaca.walkapp.domains

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

import java.security.MessageDigest

class PersonaController {

    static allowedMethods = [create: "POST", update: "PUT", delete: "DELETE", auth: "POST", get: "GET", list: "GET"]

    def prueba() {
        def dieta = Dieta.get(1)
        JSONObject datos = new JSONObject()
        JSONArray insumos = dieta.getInsumos()
        datos.put("insumos", insumos)
        JSONArray e = datos.getJSONArray("insumos")
        render dieta as JSON
    }



    def create() {
        JSONObject json = request.getJSON()
        JSONObject resp = new JSONObject()

        if (! json.containsKey("nombre") || ! json.containsKey("sexo") || ! json.containsKey("edad") ||
            ! json.containsKey("peso") || ! json.containsKey("estatura") || ! json.containsKey("usuario") ||
            ! json.containsKey("password")) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("status", HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("msg", "Se deben proveer los datos nombre, peso, sexo, estatura, edad, usuario y password")
        } else {
            def cuenta = new Cuenta(usuario: json.getString("usuario"),
                    password: toMD5(json.getString("password")), status: true)
            def persona = new Persona(
                nombre: json.getString("nombre"),
                sexo: json.getString("sexo"),
                edad: json.getDouble("edad"),
                peso: json.getDouble("peso"),
                estatura: json.getDouble("estatura"),
                cuenta: cuenta
            )
            if (persona.save() && ! persona.hasErrors()) {
                resp.put("status", HttpURLConnection.HTTP_OK)
                resp.put("persona", persona)
                render resp as JSON
            }

        }

        render resp as JSON
    }

    String nombre
    String sexo
    Integer edad
    Double peso // kilogramos
    Integer estatura // centimetros
    Double imc

    def update(Long id) {
        JSONObject json = request.getJSON()
        JSONObject resp = new JSONObject()
        if (! json.containsKey("nombre") || ! json.containsKey("sexo") || ! json.containsKey("edad") ||
                ! json.containsKey("peso") || ! json.containsKey("estatura")) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("status", HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("msg", "Se deben proveer los datos: id, nombre, peso, sexo, estatura, edad, usuario y password")
        } else {
            def persona = Persona.get(id)
            if (persona) {
                persona.setProperties(json)
                if (json.containsKey("password"))
                    persona.cuenta.password = toMD5(json.getString("password"))
                if (persona.save() && ! persona.hasErrors()) {
                    resp.put("status", HttpURLConnection.HTTP_OK)
                    resp.put("persona", persona)
                    render resp as JSON
                }
            } else {
                response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
                resp.put("status", HttpURLConnection.HTTP_BAD_REQUEST)
                resp.put("msg", "Cuenta no válida")
            }
        }
    }

    def delete() {
        println "delete"
    }

    def get(Long id) {
        JSONObject resp = new JSONObject()
        def persona = Persona.get(id)
        if (persona) {
            println "Existe"
            resp.put("status", HttpURLConnection.HTTP_OK)
            resp.put("persona", persona)
        } else {
            println "No existe"
            resp.put("status", HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("msg", "La cuenta no existe")
        }
        render resp as JSON
    }

    def auth() {
        JSONObject json = request.getJSON()

        JSONObject resp = new JSONObject()
        JSONObject respSuccess = new JSONObject()
        resp.put("status", HttpURLConnection.HTTP_UNAUTHORIZED)

        if (json.containsKey("usuario")) {
            if(json.containsKey("password")) {
                String p = toMD5(json.getString("password"))
                String u = json.getString("usuario")

                def cuenta = Cuenta.find {
                    usuario == u &&
                            password == p
                }
                if (cuenta) {
                    respSuccess.put("status", HttpURLConnection.HTTP_OK)
                    respSuccess.put("persona", cuenta.persona)
                    render respSuccess as JSON
                } else {
                    resp.put("msg", "Cuenta no válida")
                }
            } else {
                resp.put("msg", "Se debe proporcionar un password")
            }
        } else {
            resp.put("msg", "Se debe proporcionar un nombre de usuario")
        }
        response.status = HttpURLConnection.HTTP_UNAUTHORIZED
        render resp as JSON
    }

    def list() {

    }

    String toMD5(String s) {
        return MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")).encodeHex().toString()
    }



}
