from locust import HttpLocust, seq_task, TaskSequence

class AdminTest(TaskSequence):

    username = "user@gmail.com"
    password = "1234678"
    authToken = "Basic dXNlckBnbWFpbC5jb206MTIzNDY3OA=="

    @seq_task(1)
    def login(self):
        self.client.post("/usuario/login", json={"username":self.username, "password":self.password})

    @seq_task(2)
    def getAlojamientos(self):
        headers = { 'Authorization' : self.authToken }
        self.client.get("/alojamiento/estado/VALIDADO", headers = headers)

    @seq_task(3)
    def agregarAlojamiento(self):
        headers = { 'Authorization' : self.authToken }
        self.client.post("/alojamiento", headers = headers, json = {
            "nombre" : "Posada las moras",
            "descripcion" : "Un buen lugar para descansar",
            "ubicacion" : {
                "provincia" : "Buenos Aires",
                "localidad" : "Florencio Varela",
                "direccion" : "Avenida San Martin 123"
            },
            "tipoAlojamiento" : "POSADA",
            "tipoPension" : "COMPLETA",
            "valorPension" : "250",
            "categoria" : "2"
        })

class WebsiteUser(HttpLocust):
    task_set = AdminTest
    min_wait = 5000
    max_wait = 9000
