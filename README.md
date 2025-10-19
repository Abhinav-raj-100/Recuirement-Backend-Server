🎯 Recruitment Backend Server
Welcome to the Recruitment Backend Server – a robust, secure, and scalable Java Spring Boot application designed to streamline recruitment workflows. Built with modern best practices, it supports user management, job postings, resume handling, and role-based access control.

📌 Overview
✅ RESTful APIs for user registration, authentication, job management, and applications
🔐 JWT-based Authentication with role-based authorization (APPLICANT / ADMIN)
📄 Swagger (OpenAPI 3.0) documentation for seamless API testing
🗃️ Spring Data JPA with MySQL (or H2 for dev)
🧪 Clean, modular architecture following MVC pattern
🛠️ Tech Stack
Language
Java 17+
Framework
Spring Boot 3.x
Security
Spring Security + JWT
Database
MySQL (Production), H2 (Dev)
Build Tool
Maven
API Docs
Swagger UI (OpenAPI 3.0)
IDE Support
IntelliJ / Eclipse / VS Code

🚀 Getting Started
Prerequisites
Java 17 or higher
Maven 3.6+
Git
MySQL (optional; H2 used by default in dev)
Clone & Run
bash
# Clone the repository
git clone https://github.com/your-username/recruitment-backend.git
cd recruitment-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
The server starts on http://localhost:9090 

📚 API Documentation
Explore and test all endpoints interactively via Swagger UI:

🔗 http://localhost:9090/swagger-ui.html

🔑 Authentication Flow
Sign Up → Create an account (APPLICANT by default)
Log In → Receive a JWT token
Use Token → Include in Authorization: Bearer <token> header for protected routes
Admin accounts must be created manually or via seed data (not via public signup). 

🗂️ Project Structure
src/
└── main/
    ├── java/
    │   └── com/backend/recruitment/
    │       ├── config/          # Security, JWT, Swagger config
    │       ├── controller/      # REST controllers
    │       ├── entities/        # JPA entities (User, Job, Application, Resume)
    │       ├── repository/      # Spring Data JPA repositories
    │       ├── service/         # Business logic & services
    │       └── RecruitmentApplication.java
    └── resources/
        ├── application.properties  # DB, JWT, server config
        └── data.sql (optional)    # Initial data (e.g., admin user)
🌐 API Endpoints
🔹 Auth Controller
POST
/api/auth/signup
Register a new applicant
❌
POST
/api/auth/login
Login & get JWT token
❌

🔹 User Controller
GET
/api/users/profile
Get current user profile
✅ (Any)
PUT
/api/users/profile
Update user profile
✅ (Any)

🔹 Job Controller
GET
/api/jobs
Get all active jobs
❌
GET
/api/jobs/{id}
Get job by ID
❌
POST
/api/jobs
Create a new job
✅ ADMIN
PUT
/api/jobs/{id}
Update job
✅ ADMIN
DELETE
/api/jobs/{id}
Delete job
✅ ADMIN

🔹 Application Controller
POST
/api/applications
Apply to a job (with resume)
✅ APPLICANT
GET
/api/applications
Get all applications (admin view)
✅ ADMIN
GET
/api/applications/my
Get my applications
✅ APPLICANT
PUT
/api/applications/{id}/status
Update application status (e.g., APPROVED/REJECTED)
✅ ADMIN

🔹 Resume Controller
POST
/api/resumes/upload
Upload resume (PDF/DOCX)
✅ APPLICANT
GET
/api/resumes/{id}
Download resume
✅ (Owner or ADMIN)

🔒 Security & Roles
APPLICANT
- Signup/login
- View jobs
- Apply to jobs
- Upload/download own resume
ADMIN
- Create/update/delete jobs
- View all applications
- Approve/reject applications

All sensitive endpoints are protected by JWT and role validation. 

🧪 Testing
Unit tests: src/test/java/...
Integration tests included for critical flows (auth, job creation, etc.)
Use Postman or Swagger to manually test APIs during development
📂 Configuration
application.properties (Sample)

properties

# Server
server.port=9090

# Database (H2 for dev)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# JWT
app.jwt-secret=yourStrongSecretKeyHereAtLeast32CharactersLong!
app.jwt-expiration-ms=86400000

# File Upload (for resumes)
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository
Create your feature branch (git checkout -b feature/AmazingFeature)
Commit your changes (git commit -m 'Add some AmazingFeature')
Push to the branch (git push origin feature/AmazingFeature)
Open a Pull Request
📄 License
Distributed under the MIT License. See LICENSE for more information.

💡 Tip: Always check Swagger UI (/swagger-ui.html) for the latest, interactive API reference!
🚀 Happy coding and happy hiring! 
