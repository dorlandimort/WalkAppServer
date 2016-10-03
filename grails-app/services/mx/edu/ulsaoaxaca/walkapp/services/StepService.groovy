package mx.edu.ulsaoaxaca.walkapp.services

import mx.edu.ulsaoaxaca.walkapp.domains.Bitacora
import mx.edu.ulsaoaxaca.walkapp.domains.GeoPoint
import mx.edu.ulsaoaxaca.walkapp.domains.Persona
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject


class StepService {

    static final double R = 6371; // Radius of the earth
    static final double STEP_DISTANCE = 0.9


    def calculateSeps(GeoPoint start, GeoPoint end) {
        double elapsedTime = timeToHours(start.date, end.date) // H
        double distance = coordinatesToKm(start, end) // KM
        double speed = distance / elapsedTime // KM/H
        int steps = 0
        if (speed <= 15) {
            // convertir distancia a metros para calcular pasos
            steps = (distance * 1000) / STEP_DISTANCE
        }
        return steps
    }

    def timeToHours(Date start, Date end) {
        return (end.time - start.time) / (1000 * 60 * 60)
    }

    double coordinatesToKm(GeoPoint start, GeoPoint end) {
        Double latDistance = Math.toRadians(end.lat -  start.lat);
        Double lonDistance = Math.toRadians(end.ln - start.ln);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
             Math.cos(Math.toRadians(start.lat)) * Math.cos(Math.toRadians(end.lat)) *
             Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        double distance = R * c  // kilometros
        //double height = start.alt - end.alt;
        //distance = Math.pow(distance, 2) //+ Math.pow(height, 2);
        return distance;
    }

    double getTotalCaloriesFromSevenDays(Persona persona, Date date) {
        def c = Bitacora.executeQuery("Select sum(cal) from Bitacora where fecha >= ? and fecha <= ? and persona = ?",
                [(date - 7).format("yyyy-MM-dd"), date.format("yyyy-MM-dd"), persona])
        return c.get(0)
    }

    double getTodayCalories(Persona persona, Date date) {
        def c = Bitacora.executeQuery("Select sum(cal) from Bitacora where fecha = ? and persona = ?",
                [date.format("yyyy-MM-dd"), persona])
        return c.get(0)
    }

    JSONArray getBitacorasJSONArrayFromSevenDays(Persona persona, Date date) {
        def bit = Bitacora.executeQuery("Select  max(fecha) as fecha, sum(cal) as calorias, sum(pasos) as pasos from Bitacora " +
                "where fecha >= ? and fecha <= ? and persona = ? group by fecha", [(date - 7).format("yyyy-MM-dd"), date.format("yyyy-MM-dd"), persona])
        JSONArray bitacoras = new JSONArray()
        bit.each { it ->
            JSONObject json = new JSONObject()
            json.put("fecha", it.getAt(0))
            json.put("calorias", it.getAt(1))
            json.put("pasos", it.getAt(2))
            bitacoras.put(json)
        }
        return bitacoras
    }

    double getTotalStepsFromSevenDays(Persona persona, Date date) {
        def c = Bitacora.executeQuery("Select sum(pasos) from Bitacora where fecha >= ? and fecha <= ? and persona = ?",
                [(date - 7).format("yyyy-MM-dd"), date.format("yyyy-MM-dd"), persona])
        return c.get(0)
    }

    int getTodaySteps(Persona persona, Date date) {
        def c = Bitacora.executeQuery("Select sum(pasos) from Bitacora where fecha = ? and persona = ?",
                [date.format("yyyy-MM-dd"), persona])
        return c.get(0)
    }


}
