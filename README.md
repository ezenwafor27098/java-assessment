# java-assessment

# INTRODUCTION

A Java Spring-Boot Application that caters for authentication of requests via the Authentication Service when 
made to Transaction Service. The transaction service exposes a REST API that accepts a transaction authorize request. The Transaction service depends on the Authentication service via Kafka to authenticate the request and afterwards processes the transaction if successful.


# REQUIREMENTS

To run this application you will Require Docker installed on your system.


# HOW TO RUN

CD into the project root from your Docker Terminal and run the following commmands:
- docker-compose build
- docker-compose up

To drop docker instance:
- docker-compose down
- docker system prune -f


# HOW TO TEST API via POSTMAN or INSOMIA

- POST Request to http://localhost:8082/api/v1/transaction/authorize
- Request body should look like: 
    {
        "stationUuid": "25aac66b-6051-478a-95e2-6d3aa343b025",
        "driverIdentifier": {"id": "1000"}
    } 
- Response body would look like this: 
    {
        "authenticationStatus": "Accepted"
    }
- Valid Driver IDs are 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000.
- Driver ID must be a number string, else results to INVALID authentication status.
