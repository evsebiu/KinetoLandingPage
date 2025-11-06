# KinetoLandingPage

Responsive landing page built with Spring Boot and Thymeleaf for a local business. Displays services with pricing and duration, allowing customers to call directly for appointments. Focused on clean architecture, maintainability, and real-world usability.

## Key Features & Benefits

*   **Responsive Design:**  Provides a seamless experience across various devices (desktops, tablets, and smartphones).
*   **Service Showcase:**  Clearly presents services offered, including pricing and duration details.
*   **Direct Call Integration:**  Facilitates immediate appointment booking by allowing customers to call directly from the landing page.
*   **Spring Boot Powered:**  Leverages the power and efficiency of Spring Boot for backend functionality.
*   **Thymeleaf Templating:**  Utilizes Thymeleaf for dynamic content rendering and a clean, maintainable frontend.
*   **Clean Architecture:**  Adheres to a clean and well-structured architecture, promoting maintainability and scalability.

## Prerequisites & Dependencies

Before you begin, ensure you have the following installed:

*   **Java Development Kit (JDK):** Version 11 or higher.
*   **Maven:**  Version 3.6.0 or higher.
*   **An IDE (Integrated Development Environment):**  Such as IntelliJ IDEA or Eclipse.
*   **A phone app capable of making calls (for testing the direct call feature).**

## Installation & Setup Instructions

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/evsebiu/KinetoLandingPage.git
    cd KinetoLandingPage
    ```

2.  **Build the project using Maven:**

    ```bash
    ./mvnw clean install
    ```

3.  **Run the application:**

    ```bash
    ./mvnw spring-boot:run
    ```

    Alternatively, you can package the application into a JAR file and run it:

    ```bash
    ./mvnw package
    java -jar target/KinetoWebsite-0.0.1-SNAPSHOT.jar
    ```

4.  **Access the landing page:**

    Open your web browser and navigate to `http://localhost:8080`.

## Usage Examples & API Documentation

This project is primarily a landing page. There is no exposed API for external consumption.  The `PublicController` handles requests for the main landing page. The `AdminController` is likely for internal management (although details are not provided, it probably requires authentication).

## Configuration Options

The application's properties can be configured through the `application.properties` file.

*   **Server Port:**  `server.port` (defaults to 8080).
*   **Database Connection:** (If a database were added, properties would configure the connection).
*   **Security Settings:** (Configuration for authentication and authorization).

## Contributing Guidelines

We welcome contributions to this project! If you'd like to contribute, please follow these guidelines:

1.  **Fork the repository.**
2.  **Create a new branch for your feature or bug fix.**
3.  **Make your changes and test thoroughly.**
4.  **Submit a pull request with a clear description of your changes.**

Please adhere to the existing code style and conventions.

## License Information

This project is licensed under [MIT License].

## Acknowledgments

*   This project utilizes the Spring Boot framework.
*   This project utilizes the Thymeleaf templating engine.
