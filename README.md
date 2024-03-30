# Strava Experiments
A personal development project to familiarise myself with Spring Boot, with a TDD approach focused the outputs of the REST API.

### Getting Started
* Docker desktop will need to be running in order to run this project.
* The Stava activity data is loaded on start up from the `/src/main/resources/data/activities.json` file. The file provided contains dummy activity data, replace it with your own before running the application.

### REST API Usage
#### Activities
`GET /api/activities` Retrieve all activities

`GET /api/activities/{id}` Retrieve activity by ID

`GET /api/activities?sport=run` Retrieve all running activities

`GET /api/activities?distanceGte=5000` Retrieve all activities with a distance greater or equal to 5km

#### Best effort
Best effort is the shortest elapsed time for the given sport and distance

`GET /api/activities/best-effort?sport=run&distanceGte=10000` Retrieve the fastest run with a distance greater or equal to 10km

### Planned Features
* Integration with Strava APIs to retrieve user Activity data
* Front end visualisations
