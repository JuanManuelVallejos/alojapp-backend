from locust import HttpLocust, seq_task, TaskSequence

class AdminTest(TaskSequence):

    username = "admin@gmail.com"
    password = "12345678"
    authToken = "Basic YWRtaW5AZ21haWwuY29tOjEyMzQ1Njc4"

    @seq_task(1)
    def login(self):
        self.client.post("/usuario/login", json={"username":self.username, "password":self.password})

    @seq_task(2)
    def getAlojamientos(self):
        headers = { 'Authorization' : self.authToken }
        self.client.get("/alojamiento", headers = headers)

    @seq_task(3)
    def getAlojamientoParaEditar(self):
        headers = { 'Authorization' : self.authToken }
        self.client.get("/alojamiento/23", headers = headers)

    @seq_task(4)
    def aceptarAlojamiento(self):
        headers = { 'Authorization' : self.authToken }
        self.client.post("/alojamiento/check/23")
    
    @seq_task(5)
    def getAlojamientosPostEditar(self):
        headers = { 'Authorization' : self.authToken }
        self.client.get("/alojamiento", headers = headers)
        
    @seq_task(6)
    def rechazarAlojamiento(self):
        headers = { 'Authorization' : self.authToken }
        self.client.post("/alojamiento/uncheck/10?justificacion=Direccion mal cargada", headers = headers)

class WebsiteUser(HttpLocust):
    task_set = AdminTest
    min_wait = 5000
    max_wait = 9000
