package mx.edu.ulsaoaxaca.walkapp.domains

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject


class BitacoraController {

    static final double CAL_PER_STEP = 0.05

    static allowedMethods = [submitPoints: "POST"]

    def stepService

    def submitPoints() {
        def JSONObject json = request.getJSON()
        def JSONObject resp = new JSONObject()

        if (json.containsKey("lat1") && json.containsKey("lat2") &&
            json.containsKey("ln1") && json.containsKey("ln2") &&
            json.containsKey("id") && json.containsKey("hora1") &&
            json.containsKey("hora2")) {
            Persona persona = Persona.get(json.getDouble("id"))
            if (persona) {
                GeoPoint start = new GeoPoint()
                start.lat = json.getDouble("lat1")
                start.ln = json.getDouble("ln1")
                start.date = new Date().parse("yyyy-MM-dd HH:mm:ss", json.getString("hora1"))
                GeoPoint end = new GeoPoint()
                end.lat = json.getDouble("lat2")
                end.ln = json.getDouble("ln2")
                end.date = new Date().parse("yyyy-MM-dd HH:mm:ss", json.getString("hora2"))
                int steps = stepService.calculateSeps(start, end)
                double cals = CAL_PER_STEP * steps
                Bitacora bitacora = new Bitacora(persona: persona, fecha: start.date.format("yyyy-MM-dd"),
                    latInicial: start.lat, lnInicial: start.ln, latFinal: end.lat, lnFinal: end.ln, pasos: steps, cal: cals)
                if (bitacora.save() && ! bitacora.hasErrors()) {
                    double weekCalories = stepService.getTotalCaloriesFromSevenDays(persona, start.date)
                    JSONArray history =  stepService.getBitacorasJSONArrayFromSevenDays(persona, start.date)
                    double todayCalories = stepService.getTodayCalories(persona, start.date)
                    double weekSteps = stepService.getTotalStepsFromSevenDays(persona, start.date)
                    double todaySteps = stepService.getTodaySteps(persona, start.date)
                    resp.put("status", HttpURLConnection.HTTP_OK)
                    resp.put("id", persona.id)
                    resp.put("pasos", steps)
                    resp.put("calorias", cals)
                    resp.put("totalCaloriasHoy", todayCalories)
                    resp.put("totalPasosHoy", todaySteps)
                    resp.put("totalCaloriasSemana", weekCalories)
                    resp.put("totalPasosSemana", weekSteps)
                    resp.put("historialSemana", history)
                    render resp as JSON
                } else {
                    response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
                    response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
                    resp.put("msg", "Error al guardar, por favor verifique los datos y vuelva a intentarlo.")

                }
            } else {
                response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
                resp.put("status", HttpURLConnection.HTTP_BAD_REQUEST)
                resp.put("msg", "La cuenta no es válida")
            }
        } else {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("status", HttpURLConnection.HTTP_BAD_REQUEST)
            resp.put("msg", "Debe proveer los siguentes datos: lat1, lat2, ln1, ln2, alt1, alt2, hora1, hora2")
        }
        render resp as JSON
    }

}
