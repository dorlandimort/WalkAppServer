class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
        "/persona/auth"(controller: 'persona', action: 'auth')
        "/persona/prueba"(controller: 'persona', action: 'prueba')
        "/bitacora/submit"(controller: 'bitacora', action: 'submitPoints')

        "/$controller/"( {
            action = [POST: 'create', GET: 'list']
        })
        "/$controller/$id"({
            action = [PUT: 'update', DELETE: 'delete', GET: 'get']
        })

	}
}
