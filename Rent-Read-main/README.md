# Rent-Read
# RentRead API

## Overview
RentRead is a RESTful API service built with Spring Boot for managing an online book rental system.

## Features
- User Registration and Login
- Book Management (Create, Update, Delete by Admin)
- Rental Management (Rent and Return Books)
- Authentication and Authorization with Basic Auth
- Role-based access control (USER, ADMIN)

## Endpoints
- POST /public/register - Register a new rentalUser
- GET /books - Get all books
- POST /books - Create a new book (Admin only)
- PUT /books/{bookId} - Update a book (Admin only)
- DELETE /books/{bookId} - Delete a book (Admin only)
- POST /rentals/rent/{bookId} - Rent a book
- POST /rentals/return/{bookId} - Return a book

## Setup
1. Clone the repository
2. Configure MySQL database in `src/main/resources/application.properties`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## Running Tests
Run the tests: `mvn test`

## Logging
Logging is enabled using Lombok’s @Slf4j annotation.

## Contributing
Feel free to submit issues or pull requests.

## License
This project is licensed under the MIT License.

