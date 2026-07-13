Proyecto basado en funcionamiento real de un hospital, trabajado de manera basica pero funcional. Integrantes: Llancamil FLores, Manuel Duran y Aron Tapia. 
Listado de microservicios implementados: 
*Paciente 
*Empleado
*Hospital(ApiGateway)
*EurekaServer(Autodescubrimiento)
*Auth(Seguridad) 
*CitaMedica *Facturacion
*Habitacion
*HistorialClinico 
*InventarioFarmacia 
*NotificacionPaciente

Para la ejecucion del proyecto se debe contar con algun gestor de endpoints como Postman o Swagger, primero se debe arrancar EurekaServer, luego auth para que la seguridad este activa, despues los microservicios que se deseen y finalmente el ApiGateway para poder usarlo de manera correcta y eficiente.
