
# Library Management System

This Application provides a comprehensive set of endpoints for efficient management of books, Members, and transactions in a library. It enables librarians to effortlessly organize and track the library's collection, maintain accurate Member records, and facilitate smooth borrowing and returning experiences for library users. With this API, librarians can easily add, update, and remove books from the library's collection, manage Member information, handle book borrowing and returning processes, calculate fines for overdue books, enable book reservations, process book renewals, collect book reviews and ratings, and generate reports and analytics for data-driven decision making. Streamline your library operations and enhance user satisfaction with the Library Management System Application.




## Architecture

This Application was developed using a Microservice Architecture, Using Docker to containerize services.

### Security 
For Security we Use keycloak to secure our api with JWT token.

#### keycloak
- keycloak will be in the Docker compose file. when keycloak is started go to http://localhost:8080 and Create a new realm. 

a JSON file will be provided in the main repository called REALM.json, that you should upload to keycloak as it will have all the realm configurations. for further info on using keycloak go to https://www.keycloak.org/getting-started/getting-started-docker

### Database
The the database was develop using postgres and flyway as the database migration tool.

#### Database Schema 
the schema will be located on the main repository classpath under Resources --> db 
please remmeber to run the schema after starting the database 

### LIST OF SERVICES
- Config-Server
- Registry/Discovery services
- Gateway
- Member-Service
- Book-Service
- Bag-Service
- Auth-Service

#### Config-Server
- Contains all the configuration for the other microservices.

#### Registry
- Spring Cloud Discovery Service: Register all microservices to the eureka server.

#### Gateway
- API gateway for all the other services, this is the entry point of the application: running on port 8222

#### Member-Service
- Contains all the endpoints relevant to the member service

#### Book-Service
- Contains all the endpoints relevant to the book service

#### Bag-Service
- Contains all the endpoints relevant to the Bag-service





## How To Run Application 

#### Download the following Applications from the following repositories
- Config-Server : https://github.com/Odane-Roberts/Capstone-Config-Server

    ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Config-Server
    ```

- Registry : https://github.com/Odane-Roberts/Capstone-Registry

    ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Registry
    ```

- Gateway : https://github.com/Odane-Roberts/Capstone-Gateway

     ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Gateway
    ```

- Book-Service : https://github.com/Odane-Roberts/Capstone-Book-Service

     ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Book-Service
    ```

- Member-Service : https://github.com/Odane-Roberts/Capstone-Member-Service

     ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Member-Service
    ```

- Bag-Service : https://github.com/Odane-Roberts/Capstone-Bag-Service

     ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Bag-Service
    ```

- Auth-Service : https://github.com/Odane-Roberts/Capstone-Auth-Service

     ```bash
  git clone https://github.com/Odane-Roberts/Capstone-Auth-Service
    ```

## Order of Execution 
Package each application with Maven, The Dockerfile will be present in all Application.

After Packaging all Application, Go to the Docker-Compose file and run the following Apllications in the following order.

- Config-Server
- Registry
- Gateway
- Postgres  -->  Remember to run the script for the schema 
- Redis
- Keycloak 

The other Applications can run in any order.