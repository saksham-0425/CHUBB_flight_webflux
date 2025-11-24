# Flight Booking System – Spring WebFlux + MongoDB
(Jacoco Code Coverage, and sonarqube reports have been attached at the last)

It supports:

• Managing Airlines

• Managing Flights under Airlines

• Managing Inventory for each Flight

• Searching Flights

• Booking Tickets (with PNR generation)

• Viewing Bookings & Booking History

• Canceling Bookings

• Code Coverage via JaCoCo

• Static Code Analysis using SonarCloud

• Load Testing using JMeter

## 1. Architecture Overview

Controller Layer: Handles incoming HTTP requests

• Service Layer: Business logic

• Repository Layer: Reactive MongoDB operations

• Model Layer: Domain entities

• DTOs: Request/response mapping

• Global Exception Handler: Unified error handling

<img width="350" height="375" alt="image" src="https://github.com/user-attachments/assets/473cc304-3c74-4394-b52f-ff6e26b8ae86" />

## 2. Features

### Admin

• Add Airline

• Add Flights under an Airline

• Add Inventory for Flights

### User

• Search Flights

• Book Tickets

• View Ticket By PNR

• View Booking History

• Cancel Ticket

### Technical

• Reactive Programming (non-blocking)

• Fully reactive Repositories

• JaCoCo Code Coverage

• SonarCloud Code Quality

• JMeter Load testing

## 3. Tech Stack

```
| Layer                | Technology                      |
| -------------------- | ------------------------------- |
| Programming Language | Java 17                         |
| Framework            | Spring Boot 4, Spring WebFlux   |
| Database             | MongoDB Reactive                |
| Build Tool           | Maven                           |
| Testing              | JUnit 5, Mockito, WebTestClient |
| Code Coverage        | JaCoCo                          |
| Quality Analysis     | SonarCloud                      |
| Load Testing         | Apache JMeter                   |
```
## 4. Folder Structure

<img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/09617bc5-39c2-4564-bc99-0ea49e0ded05" />

## 5. API Endpoints Summary

Airline APIs
```
POST     /airline    Create airline
```

Flight APIs
```
POST     /airline/{airlineId}/flight   Add flight under airline
```

Inventory APIs
```
POST     /flight/{flightId}/inventory   Add inventory for a flight
```

Search API
```
POST     /flight/search    Search flights based on route & date
```

Booking APIs
```
POST	   /booking/{inventoryId}	    Book a flight
GET	     /booking/ticket/{pnr}	    Get ticket by PNR
GET	     /booking/history/{userId}	Get booking history
DELETE	 /booking/cancel/{pnr}	    Cancel a booking
```

## 6. Jacoco Code Coverage Report

<img width="700" height="337" alt="image" src="https://github.com/user-attachments/assets/390d87bf-1fe9-4f6a-bd57-b1dab2036bcd" />


## 7. Sonar Cloud Report 

### Before fixing the open issues:-
![WhatsApp Image 2025-11-24 at 14 23 19_07605110](https://github.com/user-attachments/assets/9741c969-62af-4fbd-8ec9-4904ad72c8e0)
### After fixing the open issues:-
![WhatsApp Image 2025-11-24 at 22 34 15_da1515bc](https://github.com/user-attachments/assets/575573d8-bd47-4c8b-963d-01f92399d43f)

## 8. JMeter Load Testing

Thread Group

• 50 threads

• Ramp-up: 5 seconds

• Loop count: 1

## 9. Conclusion
This project demonstrates a fully reactive flight booking system using modern Spring WebFlux and MongoDB, with unit tests, coverage, static code quality analysis, and load testing.
