# MarketPlace Project

## Overview

This project is a pet project for a marketplace platform created by a Java developers. The application leverages several technologies such as Spring Boot, PostgreSQL, GraphQL, and Git.

## Technologies Used

- **Java**: The primary programming language used for development.
- **Spring Boot**: A framework for building standalone, production-ready Spring-based applications.
- **PostgreSQL**: A powerful, open-source object-relational database management system.
- **GraphQL**: A query language for your API, offering a more efficient and flexible alternative to REST.
- **Git**: A version control system to manage and track changes in the source code.

## Features

1. **Registration and Authentication**:
   - Implemented using JWT (JSON Web Tokens) to enhance security and manage access control.
   - Allows users to securely sign up and log in.

2. **Purchases and Orders**:
   - Users can add products to their cart, place orders, and specify delivery addresses.
   - A streamlined order processing and management system.

3. **Reviews and Ratings**:
   - Users can leave ratings and comments on products.
   - Assists other users in making informed decisions based on feedback.

4. **GraphQL API**:
   - Provides flexibility and efficiency in client-server interactions.
   - Enables clients to request only the data they need, reducing payload size and improving performance.

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven
- PostgreSQL

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/bolatdias/market-place.git
   cd market-place
   ```

2. **Configure PostgreSQL**:
   - Create a new database for the project.
   - Update the `application.properties` file with your PostgreSQL credentials.

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

### Usage

- Access the application at `http://localhost:8080`.
- Use the GraphQL playground at `http://localhost:8080/graphql` to interact with the GraphQL API.

