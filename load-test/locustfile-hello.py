from locust import task, FastHttpUser

class HelloWorld(FastHttpUser):
    connection_timeout = 10.10
    network_timeout = 10.0

    @task
    def hello(self):
        self.client.get("/hello")