🌿 KinetoLandingPage

Responsive landing page built with Spring Boot and Thymeleaf for a local business. Displays services with pricing and duration, allowing customers to call directly for appointments. Focused on clean architecture, maintainability, and real-world usability.

✨ Key Features & Benefits

📱 Responsive Design: Seamless experience across desktops, tablets, and smartphones.

💆 Service Showcase: Clear presentation of services, pricing, and duration.

☎️ Direct Call Integration: Customers can call directly to book appointments.

⚙️ Spring Boot Powered: Reliable, efficient backend with Spring Boot.

🧩 Thymeleaf Templating: Clean, maintainable, and dynamic frontend rendering.

🧱 Clean Architecture: Organized structure that promotes scalability and easy maintenance.

🧰 Prerequisites & Dependencies

Make sure you have these installed before starting:

☕ Java Development Kit (JDK): Version 11 or higher

📦 Maven: Version 3.6.0 or higher

🧠 IDE: IntelliJ IDEA, Eclipse, or any preferred Java IDE

📞 Phone App: To test the direct call feature

⚡ Installation & Setup Instructions

Clone the repository:

git clone https://github.com/evsebiu/KinetoLandingPage.git
cd KinetoLandingPage


Build the project using Maven:

./mvnw clean install


Run the application:

./mvnw spring-boot:run


Or package and run it manually:

./mvnw package
java -jar target/KinetoWebsite-0.0.1-SNAPSHOT.jar


Access the landing page:
Open your browser and visit 👉 http://localhost:8080

💡 Usage Examples & API Info

This project is mainly a landing page, not an API service.

PublicController → handles requests for the main page.

AdminController → for internal management (likely requires authentication).

⚙️ Configuration Options

All app configurations live in the application.properties file:

🔌 Server Port: server.port (default: 8080)

🗄️ Database: (Future-ready if you add one)

🔐 Security Settings: Authentication & authorization configs (if needed)

🤝 Contributing Guidelines

We’d love your help improving this project!

🍴 Fork the repo

🌿 Create a branch for your feature or fix

🧪 Test thoroughly

📬 Submit a pull request with a clear description

Please stick to the existing code style and conventions.

📄 License

This project is licensed under the MIT License.

🙏 Acknowledgments

💚 Built with Spring Boot

🧠 Powered by Thymeleaf
