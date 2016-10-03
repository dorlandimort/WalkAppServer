import grails.converters.JSON
import mx.edu.ulsaoaxaca.walkapp.domains.Dieta
import mx.edu.ulsaoaxaca.walkapp.domains.DietaInsumo
import mx.edu.ulsaoaxaca.walkapp.domains.Insumo
import mx.edu.ulsaoaxaca.walkapp.domains.Persona
import org.codehaus.groovy.grails.web.json.JSONObject

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Persona) {
            def JSONObject json = new JSONObject()
            json.put("id", it.id)
            json.put("nombre", it.nombre)
            json.put("sexo", it.sexo)
            json.put("peso", it.peso)
            json.put("estatura", it.estatura)
            json.put("imc", it.imc)
            json.put("dieta", it.dieta)
            return json
        }

        JSON.registerObjectMarshaller(Dieta) {
            def JSONObject json = new JSONObject()
            json.put("id", it.id)
            json.put("nombre", it.nombre)
            json.put("descripcion", it.descripcion)
            json.put("insumos", it.getInsumos())
            return json
        }

        JSON.registerObjectMarshaller(DietaInsumo) {
            def JSONObject json = new JSONObject()
            json.put("insumo", it.insumo.nombre)
            json.put("cantidad", it.cantidad)
            return json
        }

        def  p = new Persona(
            nombre: "Giovanni",
            sexo: 'M',
            edad: 21,
            peso: 60,
            estatura: 164
        ).save()

        def  p2 = new Persona(
                nombre: "Orlando",
                sexo: 'M',
                edad: 21,
                peso: 60,
                estatura: 164
        ).save()


        def fresa = new Insumo(nombre: 'Fresa', descripcion: 'Fresas rojas', cal: 100).save()
        def pollo = new Insumo(nombre: 'Pollo', descripcion: 'Pollo fresco', cal: 100).save()
        def res = new Insumo(nombre: 'Res', descripcion: 'Carne de lomo de res', cal: 100).save()
        def aguacate = new Insumo(nombre: 'Aguacate', descripcion: 'Aguacate hass', cal: 100).save()
        def aceite = new Insumo(nombre: 'Aceite', descripcion: 'Aceite de oliva extra virgen', cal: 100).save()
        def sal = new Insumo(nombre: 'Sal', descripcion: 'Sal yodada', cal: 100).save()

        def dieta1 = new Dieta(nombre: 'Dieta uno', descripcion: 'dieta para bajar de peso').save(flush: true)
        p.dieta = dieta1
        p.save()
        def dieta2 = new Dieta(nombre: 'Dieta dos', descripcion: 'dieta para subir de peso').save(flush: true)
        p2.dieta = dieta2
        p2.save()

        def DietaInsumo di1 = new DietaInsumo(dieta: dieta1, insumo: fresa, cantidad: 2).save()

        def DietaInsumo di2 = new DietaInsumo(dieta: dieta1, insumo: pollo, cantidad: 1).save()

        def DietaInsumo di3 = new DietaInsumo(dieta: dieta1, insumo: res, cantidad: 3).save()

        def DietaInsumo di4 = new DietaInsumo(dieta: dieta1, insumo: aceite, cantidad: 6).save()

        def DietaInsumo di5 = new DietaInsumo(dieta: dieta2, insumo: aguacate, cantidad: 4).save()
        def DietaInsumo di6 = new DietaInsumo(dieta: dieta2, insumo: sal, cantidad: 9).save()
        def DietaInsumo di7 = new DietaInsumo(dieta: dieta2, insumo: fresa, cantidad: 1).save()
        def DietaInsumo di8 = new DietaInsumo(dieta: dieta2, insumo: pollo, cantidad: 2).save()

    }
    def destroy = {
    }
}
