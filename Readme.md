# VoxPopuli
VoxPopuli is a backend system designed to provide a comment section to any website. It leverages a microservice architecture for scalability, maintainability and clear separation of concerns.

## Overview
VoxPopuli is a Chrome extension that adds a comment section to any website. I'm building it as a learning project to explore microservice architectures, backend authentication with Spring security JWT, and frontend Shadow DOM manipulation. The backend is built with Spring Boot and PostgreSQL, with separate services for users, comments, authentication, filtering, and a gateway. The Chrome extension communicates with the backend to show comments in real time.

## Stack used
**Backend:** Spring boot, Java, JPA
**Security:** Spring Security, JWT
**Database:** PostgreSQL

## Architecture
Service level flow charts and sequence diagrams can be found in the [Architecture.md](Architecture.md) file. Implementation level diagrams are found in the Architecture.md files within each service.

## Roadmap:
**Backend:**
- [ ] User Service.
- [ ] Comment Service.
- [ ] Authentication Service.
- [ ] Filter Service.
- [ ] Gateway Service.
**Frontend:**
- [ ] Popup UI.
- [ ] BackgroundScript.
- [ ] Authentication logic.
- [ ] Messaging logic (extension ↔ backend).
- [ ] Shadow DOM logic.
**Integration:**
- [ ] Deploy Microservices to a Host
- [ ] Publish Extension.
